/*
 * Copyright 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.lucence;


import java.io.Closeable;
import java.io.IOException;
import java.text.ParseException;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.expressions.Expression;
import org.apache.lucene.expressions.SimpleBindings;
import org.apache.lucene.expressions.js.JavascriptCompiler;
import org.apache.lucene.facet.DrillDownQuery;
import org.apache.lucene.facet.DrillSideways;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.range.DoubleRange;
import org.apache.lucene.facet.range.DoubleRangeFacetCounts;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.SloppyMath;

public class DistanceFacetsExample implements Closeable {

    final DoubleRange          ONE_KM           = new DoubleRange("< 1 km", 0.0D, true, 1.0D, false);
    final DoubleRange          TWO_KM           = new DoubleRange("< 2 km", 0.0D, true, 2.0D, false);
    final DoubleRange          FIVE_KM          = new DoubleRange("< 5 km", 0.0D, true, 5.0D, false);
    final DoubleRange          TEN_KM           = new DoubleRange("< 10 km", 0.0D, true, 10.0D, false);

    private final Directory    indexDir         = new RAMDirectory();
    private IndexSearcher      searcher;
    private final FacetsConfig config           = new FacetsConfig();
    public static final double ORIGIN_LATITUDE  = 40.7143528D;
    public static final double ORIGIN_LONGITUDE = -74.005973100000006D;
    public static final double EARTH_RADIUS_KM  = 6371.0100000000002D;

    public void index() throws IOException {
        IndexWriter writer = new IndexWriter(indexDir, new IndexWriterConfig(new WhitespaceAnalyzer()).setOpenMode(IndexWriterConfig.OpenMode.CREATE));

        Document doc = new Document();
        doc.add(new DoubleField("latitude", 40.759011000000001D, Field.Store.NO));
        doc.add(new NumericDocValuesField("latitude", Double.doubleToRawLongBits(40.759011000000001D)));
        doc.add(new DoubleField("longitude", -73.984472199999999D, Field.Store.NO));
        doc.add(new NumericDocValuesField("longitude", Double.doubleToRawLongBits(-73.984472199999999D)));
        writer.addDocument(doc);

        doc = new Document();
        doc.add(new DoubleField("latitude", 40.718266D, Field.Store.NO));
        doc.add(new NumericDocValuesField("latitude", Double.doubleToRawLongBits(40.718266D)));
        doc.add(new DoubleField("longitude", -74.007818999999998D, Field.Store.NO));
        doc.add(new NumericDocValuesField("longitude", Double.doubleToRawLongBits(-74.007818999999998D)));
        writer.addDocument(doc);

        doc = new Document();
        doc.add(new DoubleField("latitude", 40.7051157D, Field.Store.NO));
        doc.add(new NumericDocValuesField("latitude", Double.doubleToRawLongBits(40.7051157D)));
        doc.add(new DoubleField("longitude", -74.008830500000002D, Field.Store.NO));
        doc.add(new NumericDocValuesField("longitude", Double.doubleToRawLongBits(-74.008830500000002D)));
        writer.addDocument(doc);

        searcher = new IndexSearcher(DirectoryReader.open(writer));
        writer.close();
    }

    private ValueSource getDistanceValueSource() {
        Expression distance;
        try {
            distance = JavascriptCompiler.compile("haversin(40.7143528,-74.0059731,latitude,longitude)");
        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }
        SimpleBindings bindings = new SimpleBindings();
        bindings.add(new SortField("latitude", SortField.Type.DOUBLE));
        bindings.add(new SortField("longitude", SortField.Type.DOUBLE));

        return distance.getValueSource(bindings);
    }

    public static Query getBoundingBoxQuery(double originLat, double originLng, double maxDistanceKM) {
        double originLatRadians = Math.toRadians(originLat);
        double originLngRadians = Math.toRadians(originLng);

        double angle = maxDistanceKM / (SloppyMath.earthDiameter(originLat) / 2.0D);

        double minLat = originLatRadians - angle;
        double maxLat = originLatRadians + angle;
        double minLng;
        double maxLng;
        if ((minLat > Math.toRadians(-90.0D)) && (maxLat < Math.toRadians(90.0D))) {
            double delta = Math.asin(Math.sin(angle) / Math.cos(originLatRadians));
            minLng = originLngRadians - delta;
            if (minLng < Math.toRadians(-180.0D)) {
                minLng += 6.283185307179586D;
            }
            maxLng = originLngRadians + delta;
            if (maxLng > Math.toRadians(180.0D)) maxLng -= 6.283185307179586D;
        } else {
            minLat = Math.max(minLat, Math.toRadians(-90.0D));
            maxLat = Math.min(maxLat, Math.toRadians(90.0D));
            minLng = Math.toRadians(-180.0D);
            maxLng = Math.toRadians(180.0D);
        }

        BooleanQuery.Builder f = new BooleanQuery.Builder();

        f.add(NumericRangeQuery.newDoubleRange("latitude", Double.valueOf(Math.toDegrees(minLat)), Double.valueOf(Math.toDegrees(maxLat)), true, true), BooleanClause.Occur.FILTER);

        if (minLng > maxLng) {
            BooleanQuery.Builder lonF = new BooleanQuery.Builder();
            lonF.add(NumericRangeQuery.newDoubleRange("longitude", Double.valueOf(Math.toDegrees(minLng)), null, true, true), BooleanClause.Occur.SHOULD);

            lonF.add(NumericRangeQuery.newDoubleRange("longitude", null, Double.valueOf(Math.toDegrees(maxLng)), true, true), BooleanClause.Occur.SHOULD);

            f.add(lonF.build(), BooleanClause.Occur.MUST);
        } else {
            f.add(NumericRangeQuery.newDoubleRange("longitude", Double.valueOf(Math.toDegrees(minLng)), Double.valueOf(Math.toDegrees(maxLng)), true, true), BooleanClause.Occur.FILTER);
        }

        return f.build();
    }

    public FacetResult search() throws IOException {
        FacetsCollector fc = new FacetsCollector();

        searcher.search(new MatchAllDocsQuery(), fc);

        Facets facets = new DoubleRangeFacetCounts("field", getDistanceValueSource(), fc, getBoundingBoxQuery(40.7143528D, -74.005973100000006D, 10.0D), new DoubleRange[] { ONE_KM, TWO_KM, FIVE_KM,
                TEN_KM });

        return facets.getTopChildren(10, "field", new String[0]);
    }

    public TopDocs drillDown(DoubleRange range) throws IOException {
        DrillDownQuery q = new DrillDownQuery(null);
        final ValueSource vs = getDistanceValueSource();
        q.add("field", range.getQuery(getBoundingBoxQuery(40.7143528D, -74.005973100000006D, range.max), vs));
        DrillSideways ds = new DrillSideways(searcher, config, (TaxonomyReader) null) {

            protected Facets buildFacetsResult(FacetsCollector drillDowns, FacetsCollector[] drillSideways, String[] drillSidewaysDims) throws IOException {
                assert (drillSideways.length == 1);
                return new DoubleRangeFacetCounts("field", vs, drillSideways[0], new DoubleRange[] { ONE_KM, TWO_KM, FIVE_KM, TEN_KM });
            }
        };
        return ds.search(q, 10).hits;
    }

    public void close() throws IOException {
        searcher.getIndexReader().close();
        indexDir.close();
    }

    public static void main(String[] args) throws Exception {
        DistanceFacetsExample example = new DistanceFacetsExample();
        example.index();

        System.out.println("Distance facet counting example:");
        System.out.println("-----------------------");
        System.out.println(example.search());

        System.out.println("Distance facet drill-down example (field/< 2 km):");
        System.out.println("---------------------------------------------");
        TopDocs hits = example.drillDown(example.TWO_KM);
        System.out.println(hits.totalHits + " totalHits");

        example.close();
    }
}

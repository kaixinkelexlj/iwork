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

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.facet.DrillDownQuery;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.range.LongRange;
import org.apache.lucene.facet.range.LongRangeFacetCounts;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

/**
 * 
 * @author lujun.xlj
 * @version $Id: RangeFacetsExample.java, v 0.1 Jun 17, 2016 11:41:51 AM lujun.xlj Exp $
 */
public class RangeFacetsExample implements Closeable {

    private final Directory indexDir       = new RAMDirectory();
    private IndexSearcher   searcher;
    private final long      nowSec         = System.currentTimeMillis();

    final LongRange         PAST_HOUR      = new LongRange("Past hour", nowSec - 3600L, true, nowSec, true);
    final LongRange         PAST_SIX_HOURS = new LongRange("Past six hours", nowSec - 21600L, true, nowSec, true);
    final LongRange         PAST_DAY       = new LongRange("Past day", nowSec - 86400L, true, nowSec, true);

    public void index() throws IOException {
        IndexWriter indexWriter = new IndexWriter(indexDir, new IndexWriterConfig(new WhitespaceAnalyzer()).setOpenMode(IndexWriterConfig.OpenMode.CREATE));

        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            long then = nowSec - i * 1000;

            doc.add(new NumericDocValuesField("timestamp", then));

            doc.add(new LongField("timestamp", then, Field.Store.NO));
            indexWriter.addDocument(doc);
        }

        searcher = new IndexSearcher(DirectoryReader.open(indexWriter));
        indexWriter.close();
    }

    private FacetsConfig getConfig() {
        return new FacetsConfig();
    }

    public FacetResult search() throws IOException {
        FacetsCollector fc = new FacetsCollector();

        FacetsCollector.search(searcher, new MatchAllDocsQuery(), 10, fc);

        Facets facets = new LongRangeFacetCounts("timestamp", fc, new LongRange[] { PAST_HOUR, PAST_SIX_HOURS, PAST_DAY });

        return facets.getTopChildren(10, "timestamp", new String[0]);
    }

    public TopDocs drillDown(LongRange range) throws IOException {
        DrillDownQuery q = new DrillDownQuery(getConfig());

        q.add("timestamp", NumericRangeQuery.newLongRange("timestamp", Long.valueOf(range.min), Long.valueOf(range.max), range.minInclusive, range.maxInclusive));

        return searcher.search(q, 10);
    }

    public void close() throws IOException {
        searcher.getIndexReader().close();
        indexDir.close();
    }

    public static void main(String[] args) throws Exception {
        RangeFacetsExample example = new RangeFacetsExample();
        example.index();

        System.out.println("Facet counting example:");
        System.out.println("-----------------------");
        System.out.println(example.search());

        System.out.println("\n");
        System.out.println("Facet drill-down example (timestamp/Past six hours):");
        System.out.println("---------------------------------------------");
        TopDocs hits = example.drillDown(example.PAST_SIX_HOURS);
        System.out.println(hits.totalHits + " totalHits");

        example.close();
    }
}

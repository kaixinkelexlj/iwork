/*
 * Copyright 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.lucence;


import java.io.IOException;
import java.text.ParseException;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.expressions.Expression;
import org.apache.lucene.expressions.js.JavascriptCompiler;
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.taxonomy.TaxonomyFacetSumValueSource;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.SortField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class ExpressionAggregationFacetsExample {

    private final Directory    indexDir = new RAMDirectory();
    private final Directory    taxoDir  = new RAMDirectory();
    private final FacetsConfig config   = new FacetsConfig();

    private void index() throws IOException {
        IndexWriter indexWriter = new IndexWriter(indexDir, new IndexWriterConfig(new WhitespaceAnalyzer()).setOpenMode(IndexWriterConfig.OpenMode.CREATE));

        DirectoryTaxonomyWriter taxoWriter = new DirectoryTaxonomyWriter(taxoDir);

        Document doc = new Document();
        doc.add(new TextField("c", "foo bar", Field.Store.NO));
        doc.add(new NumericDocValuesField("popularity", 5L));
        doc.add(new FacetField("A", new String[] { "B" }));
        indexWriter.addDocument(config.build(taxoWriter, doc));

        doc = new Document();
        doc.add(new TextField("c", "foo foo bar", Field.Store.NO));
        doc.add(new NumericDocValuesField("popularity", 3L));
        doc.add(new FacetField("A", new String[] { "C" }));
        indexWriter.addDocument(config.build(taxoWriter, doc));

        indexWriter.close();
        taxoWriter.close();
    }

    private FacetResult search() throws IOException, ParseException {
        DirectoryReader indexReader = DirectoryReader.open(indexDir);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        TaxonomyReader taxoReader = new DirectoryTaxonomyReader(taxoDir);

        Expression expr = JavascriptCompiler.compile("_score * sqrt(popularity)");
        org.apache.lucene.expressions.SimpleBindings bindings = new org.apache.lucene.expressions.SimpleBindings();
        bindings.add(new SortField("_score", SortField.Type.SCORE));
        bindings.add(new SortField("popularity", SortField.Type.LONG));

        FacetsCollector fc = new FacetsCollector(true);

        FacetsCollector.search(searcher, new MatchAllDocsQuery(), 10, fc);

        Facets facets = new TaxonomyFacetSumValueSource(taxoReader, config, fc, expr.getValueSource(bindings));
        FacetResult result = facets.getTopChildren(10, "A", new String[0]);

        indexReader.close();
        taxoReader.close();

        return result;
    }

    public FacetResult runSearch() throws IOException, ParseException {
        index();
        return search();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Facet counting example:");
        System.out.println("-----------------------");
        FacetResult result = new ExpressionAggregationFacetsExample().runSearch();
        System.out.println(result);
    }
}

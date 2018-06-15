/*
 * Copyright 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.lucence;

import java.io.StringReader;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PhraseQuery.Builder;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.lionsoul.jcseg.analyzer.JcsegAnalyzer;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;

/**
 * @author lujun.xlj
 * @version $Id: PhraseQueryTest.java, v 0.1 Jun 27, 2016 5:23:57 PM lujun.xlj Exp $
 */
public class PhraseQueryTest {

    public static void main(String[] args) throws Exception {
        //enTest();
        //cnTest();
        fenciTest();
        cnTestByJcseg();
    }

    public static void fenciTest() throws Exception {
        String text = "北京金逸影院";
        Analyzer analyzer = new StandardAnalyzer();
        StringReader reader = new StringReader(text);
        TokenStream ts = analyzer.tokenStream("", reader);
        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
        ts.reset();
        while (ts.incrementToken()) {
            System.out.print(term.toString() + "|");
        }
        analyzer.close();
        reader.close();

        System.out.println("==================");
        analyzer = new SimpleAnalyzer();
        reader = new StringReader(text);
        ts = analyzer.tokenStream("", reader);
        term = ts.getAttribute(CharTermAttribute.class);
        ts.reset();
        while (ts.incrementToken()) {
            System.out.print(term.toString() + "|");
        }
        analyzer.close();
        reader.close();

        //CJKAnalyzer
        System.out.println("==================");
        analyzer = new CJKAnalyzer();
        reader = new StringReader(text);
        ts = analyzer.tokenStream("", reader);
        term = ts.getAttribute(CharTermAttribute.class);
        ts.reset();
        while (ts.incrementToken()) {
            System.out.print(term.toString() + "|");
        }
        analyzer.close();
        reader.close();

        //jcseg中文分词词库
        System.out.println("==================");
        analyzer = new JcsegAnalyzer(JcsegTaskConfig.COMPLEX_MODE);
        reader = new StringReader(text);
        ts = analyzer.tokenStream("", reader);
        term = ts.getAttribute(CharTermAttribute.class);
        ts.reset();
        while (ts.incrementToken()) {
            System.out.print(term.toString() + "|");
        }
        analyzer.close();
        reader.close();

        //jcseg中文分词词库+自定义词库
        initJcsegCustomDic();
        System.out.println("==================");
        analyzer = new JcsegAnalyzer(JcsegTaskConfig.COMPLEX_MODE);
        reader = new StringReader(text);
        ts = analyzer.tokenStream("", reader);
        term = ts.getAttribute(CharTermAttribute.class);
        ts.reset();
        System.out.println("jcseg==>");
        while (ts.incrementToken()) {
            System.out.print(term.toString() + "|");
        }
        analyzer.close();
        reader.close();
        System.out.println("=====end=======");

    }

    private static void initJcsegCustomDic() throws Exception {

       /* JcsegTaskConfig config = new JcsegTaskConfig();
        ADictionary dic = DictionaryFactory.createSingletonDictionary(config, false);
        InputStream is = IOUtils.toInputStream("CJK_WORD\r\n金逸/null/jin yi/null", "UTF-8");
        dic.load(is);
        is.close();*/

    }

    public static void cnTestByJcseg() throws Exception {
        initJcsegCustomDic();
        Directory dir = new RAMDirectory();
        Analyzer analyzer = new JcsegAnalyzer(JcsegTaskConfig.COMPLEX_MODE);
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(dir, iwc);

        Document doc = new Document();
        doc.add(new TextField("text", "北京金逸影院", Field.Store.YES));
        writer.addDocument(doc);

        doc = new Document();
        doc.add(new TextField("text", "北京市金逸影院", Field.Store.YES));
        writer.addDocument(doc);

        doc = new Document();
        doc.add(new TextField("text", "金逸北京影院", Field.Store.YES));
        writer.addDocument(doc);

        writer.close();

        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);

        Builder builder = new Builder();
        String queryString = "影院 北京  ";
        for (String q : queryString.split("\\s+")) {
            q = q.trim();
            if (StringUtils.isBlank(q)) {
                continue;
            }
            System.out.println("q==>" + q);
            builder.add(new Term("text", q));
        }
        PhraseQuery query = builder.setSlop(100).build();
        TopDocs results = searcher.search(query, 100);

        /*String term1 = "北京金逸影院";
        FuzzyQuery q = new FuzzyQuery(new Term("text", term1));
        TopDocs results = searcher.search(q, 100);*/

        ScoreDoc[] scoreDocs = results.scoreDocs;
        for (int i = 0; i < scoreDocs.length; ++i) {
            // System.out.println(searcher.explain(query, scoreDocs[i].doc));
            int docID = scoreDocs[i].doc;
            Document document = searcher.doc(docID);
            String path = document.get("text");
            System.out.println("text:" + path);
        }
    }

    public static void cnTest() throws Exception {
        Directory dir = new RAMDirectory();
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(dir, iwc);

        Document doc = new Document();
        doc.add(new TextField("text", "北京金逸影院", Field.Store.YES));
        writer.addDocument(doc);

        doc = new Document();
        doc.add(new TextField("text", "北京市金逸影院", Field.Store.YES));
        writer.addDocument(doc);

        doc = new Document();
        doc.add(new TextField("text", "金逸北京影院", Field.Store.YES));
        writer.addDocument(doc);

        writer.close();

        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);

        String term1 = "北";
        Builder builder = new Builder();
        builder.add(new Term("text", term1));
        TopDocs results = searcher.search(builder.build(), 100);

        /*FuzzyQuery q = new FuzzyQuery(new Term("text", term1));
        TopDocs results = searcher.search(q, 100);*/

        ScoreDoc[] scoreDocs = results.scoreDocs;
        for (int i = 0; i < scoreDocs.length; ++i) {
            // System.out.println(searcher.explain(query, scoreDocs[i].doc));
            int docID = scoreDocs[i].doc;
            Document document = searcher.doc(docID);
            String path = document.get("text");
            System.out.println("text:" + path);
        }
    }

    public static void enTest() throws Exception {
        Directory dir = new RAMDirectory();
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(dir, iwc);

        Document doc = new Document();
        doc.add(new TextField("text", "quick brown fox", Field.Store.YES));
        writer.addDocument(doc);

        doc = new Document();
        doc.add(new TextField("text", "jumps over lazy broun dog", Field.Store.YES));
        writer.addDocument(doc);

        doc = new Document();
        doc.add(new TextField("text", "jumps over extremely very lazy broxn dog", Field.Store.YES));
        writer.addDocument(doc);

        writer.close();

        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);

        String term1 = "dog";
        String term2 = "jumps";
        /*PhraseQuery phraseQuery = new PhraseQuery();
        phraseQuery.add(new Term("text", term1));
        phraseQuery.add(new Term("text", term2));
        phraseQuery.setSlop(15);*/

        Builder builder = new Builder();

        builder.add(new Term("text", term1));
        builder.add(new Term("text", term2));
        builder.setSlop(5);
        // builder.setSlop(7);

        TopDocs results = searcher.search(builder.build(), 100);
        ScoreDoc[] scoreDocs = results.scoreDocs;

        for (int i = 0; i < scoreDocs.length; ++i) {
            // System.out.println(searcher.explain(query, scoreDocs[i].doc));
            int docID = scoreDocs[i].doc;
            Document document = searcher.doc(docID);
            String path = document.get("text");
            System.out.println("text:" + path);
        }
    }
}

/*
 * Copyright 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.lucence;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;


/**
 * 
 * @author lujun.xlj
 * @version $Id: SortTest.java, v 0.1 Jun 28, 2016 9:53:28 AM lujun.xlj Exp $
 */
public class SortTest {
    
    /**
     * RELEVANCE 表示按评分排序，
     * INDEXORDER 表示按文档索引排序，什么叫按文档索引排序？意思是按照索引文档的docId排序，我们在创建索引文档的时候，Lucene默认会帮我们自动加一个Field(docId),如果你没有修改默认的排序行为，默认是先按照索引文档相关性评分降序排序(如果你开启了对索引文档打分功能的话)，然后如果两个文档评分相同，再按照索引文档id升序排列。
     * SCORE:表示按评分排序,默认是降序
     * DOC:按文档ID排序，除了评分默认是降序以外，其他默认都是升序
     * STRING:表示把域的值转成字符串进行排序，
     * STRING_VAL也是把域的值转成字符串进行排序，不过比较的时候是调用String.compareTo来比较的
     * STRING_VAL性能比STRING要差，STRING是怎么比较的，源码里没有说明。
     * 相应的还有INT,FLOAT,DOUBLE,LONG就不多说了，
     * CUSTOM:表示自定义排序，这个是要结合下面的成员变量
     * private FieldComparatorSource comparatorSource;一起使用，即指定一个自己的自定义的比较器，通过自己的比较器来决定排序顺序。
     * 
     * 
     * @param args
     * @throws Exception
     */
    
    public static void main(String[] args) throws Exception {
        String dataDir = "f:/workspace/iWork4Maven/source/demodata/";
        String indexDir = "f:/luceneindex/demoindex/";

        Directory dir = FSDirectory.open(Paths.get(indexDir));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = new IndexWriter(dir, indexWriterConfig);

        List<File> results = new ArrayList<File>();
        findFiles(results, new File(dataDir));
        System.out.println(results.size() + " books to index");

        for (File file : results) {
            Document doc = getDocument(dataDir, file);
            writer.addDocument(doc);
        }
        writer.close();
        dir.close();

        Query allBooks = new MatchAllDocsQuery();

        QueryParser parser = new QueryParser("contents", new StandardAnalyzer());
        BooleanQuery query = new BooleanQuery();
        query.add(allBooks, BooleanClause.Occur.SHOULD);
        query.add(parser.parse("java OR action"), BooleanClause.Occur.SHOULD);

        Directory directory = FSDirectory.open(Paths.get(indexDir));

        displayResults(directory, query, Sort.RELEVANCE);

        displayResults(directory, query, Sort.INDEXORDER);

        displayResults(directory, query, new Sort(new SortField("category", Type.STRING)));

        displayResults(directory, query, new Sort(new SortField("pubmonth", Type.INT, true)));

        displayResults(directory, query, new Sort(new SortField("category", Type.STRING), SortField.FIELD_SCORE, new SortField("pubmonth", Type.INT, true)));

        displayResults(directory, query, new Sort(new SortField[] { SortField.FIELD_SCORE, new SortField("category", Type.STRING) }));
        directory.close();


    }

    /**
     * 查找指定目录下的所有properties文件
     * 
     * @param result
     * @param dir
     */
    private static void findFiles(List<File> result, File dir) {
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(".properties")) {
                result.add(file);
            } else if (file.isDirectory()) {
                findFiles(result, file);
            }
        }
    }

    /**
     * 读取properties文件生成Document
     * 
     * @param rootDir
     * @param file
     * @return
     * @throws IOException
     */
    public static Document getDocument(String rootDir, File file) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(file));

        Document doc = new Document();

        String category = file.getParent().substring(rootDir.length());
        category = category.replace(File.separatorChar, '/');

        String isbn = props.getProperty("isbn");
        String title = props.getProperty("title");
        String author = props.getProperty("author");
        String url = props.getProperty("url");
        String subject = props.getProperty("subject");

        String pubmonth = props.getProperty("pubmonth");

        System.out.println("title:" + title + "\n" + "author:" + author + "\n" + "subject:" + subject + "\n" + "pubmonth:" + pubmonth + "\n" + "category:" + category + "\n---------");

        doc.add(new StringField("isbn", isbn, Field.Store.YES));
        doc.add(new StringField("category", category, Field.Store.YES));
        doc.add(new SortedDocValuesField("category", new BytesRef(category)));
        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new Field("title2", title.toLowerCase(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS, Field.TermVector.WITH_POSITIONS_OFFSETS));

        String[] authors = author.split(",");
        for (String a : authors) {
            doc.add(new Field("author", a, Field.Store.YES, Field.Index.NOT_ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
        }

        doc.add(new Field("url", url, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
        doc.add(new Field("subject", subject, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));

        doc.add(new IntField("pubmonth", Integer.parseInt(pubmonth), Field.Store.YES));
        doc.add(new NumericDocValuesField("pubmonth", Integer.parseInt(pubmonth)));
        Date d = null;
        try {
            d = DateTools.stringToDate(pubmonth);
        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }
        doc.add(new IntField("pubmonthAsDay", (int) (d.getTime() / (1000 * 3600 * 24)), Field.Store.YES));

        for (String text : new String[] { title, subject, author, category }) {
            doc.add(new Field("contents", text, Field.Store.NO, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
        }
        return doc;
    }

    public static void displayResults(Directory directory, Query query, Sort sort) throws IOException {
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        // searcher.setDefaultFieldSortScoring(true, false);

        // Lucene5.x把是否评分的两个参数放到方法入参里来进行设置
        // searcher.search(query, filter, n, sort, doDocScores, doMaxScore);
        TopDocs results = searcher.search(query, 20, sort, true, false);

        System.out.println("\nResults for: " + query.toString() + " sorted by " + sort);

        System.out.println(StringUtils.rightPad("Title", 30) + StringUtils.rightPad("pubmonth", 10) + StringUtils.center("id", 4) + StringUtils.center("score", 15));
        PrintStream out = new PrintStream(System.out, true, "UTF-8");

        DecimalFormat scoreFormatter = new DecimalFormat("0.######");
        for (ScoreDoc sd : results.scoreDocs) {
            int docID = sd.doc;
            float score = sd.score;
            Document doc = searcher.doc(docID);
            out.println(StringUtils.rightPad(StringUtils.abbreviate(doc.get("title"), 29), 30) + StringUtils.rightPad(doc.get("pubmonth"), 10) + StringUtils.center("" + docID, 4)
                        + StringUtils.leftPad(scoreFormatter.format(score), 12));
            out.println("   " + doc.get("category"));
            // out.println(searcher.explain(query, docID));
        }
        System.out.println("\n**************************************\n");
        reader.close();
    }

}

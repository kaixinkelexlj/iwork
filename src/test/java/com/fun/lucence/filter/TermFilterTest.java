/*
 * Copyright 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.fun.lucence.filter;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.TermFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;

import com.fun.lucence.util.LuceneUtils;


/**
 * 
 * @author lujun.xlj
 * @version $Id: TermFilterTest.java, v 0.1 Jun 28, 2016 11:50:43 AM lujun.xlj Exp $
 */
public class TermFilterTest extends FilterTestBase{
    
    public static void main(String[] args) throws IOException {  
        Directory directory = LuceneUtils.openFSDirectory(IndexDir);  
        IndexReader reader = DirectoryReader.open(directory);  
        IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher(reader);  
        //先过滤掉subject域中不包含junit的索引文档，然后再在剩下的索引文档中查询title域中包含ant关键字的索引文档  
        Query query = new TermQuery(new Term("title","ant"));  
        Filter filter = new TermFilter(new Term("subject","junit"));  
        List<Document> list = LuceneUtils.query(indexSearcher, query,filter);  
        if(null == list || list.size() <= 0) {  
            System.out.println("No results.");  
            return;  
        }  
        for(Document doc : list) {  
            String isbn = doc.get("isbn");  
            String category = doc.get("category");  
            String title = doc.get("title");  
            String author = doc.get("author");  
            System.out.println("isbn:" + isbn);  
            String pubmonth = doc.get("pubmonth");  
            System.out.println("category:" + category);  
            System.out.println("title:" + title);  
            System.out.println("author:" + author);  
            System.out.println("pubmonth:" + pubmonth);  
            System.out.println("*****************************************************\n\n");  
        }  
    }  
    
}

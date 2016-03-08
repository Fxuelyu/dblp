package org.me.SearchEngine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.me.DBConnection.DBConnection;

public class keyWordSearch {
   //add index to the journals
	private static IndexWriter indexWriter=null;
	private static Directory indexDir=null;
	public keyWordSearch(IndexWriter indexWriter, Directory indexDir){
		this.indexWriter=indexWriter;
		this.indexDir=indexDir;
	}
	
	public static IndexWriter getIndexWriter(boolean create) throws IOException{
		if(indexWriter==null){
			File files=new File("./indexFile");
			 for (File file: files.listFiles()) {
			        file.delete();
			    }
		    indexDir=FSDirectory.open(files.toPath());
			Analyzer analyzer=new StandardAnalyzer();
			IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
			indexWriter=new IndexWriter(indexDir,iwc);
		   // System.err.println(indexWriter);
		}
		return indexWriter;
	}
	public static void closeIndexWriter() throws IOException{
		if(indexWriter!=null){
			indexWriter.close();
		}
	}
	
	public static void indexTitle(String title,IndexWriter writer) throws IOException{
		System.out.println("Indexing title: "+title);
		String[] buff=title.split(" ");
		Document document=new Document();
		for(String str:buff){
			document.add(new StringField("word", str, Field.Store.YES));
		}
		document.add(new TextField("Title",title,Field.Store.YES));
		
		writer.addDocument(document);
	}
	
	public static void rebuildIndexes(String url,IndexWriter indexWriter) throws IOException, SQLException{
		// erase existing index
		//getIndexWriter(true);
		//index all titles
		System.out.println("dddddddddddddddddddddddddddddddddddddd");
		PreparedStatement stmt_title,stmt_author;
		Connection connection=DBConnection.getConn();
		String sql1="select title from journals";
		File file1=new File(url);
		Writer writer1=new FileWriter(file1);
		BufferedWriter bWriter1=new BufferedWriter(writer1);
		stmt_title=connection.prepareStatement(sql1);
		ResultSet rSet=stmt_title.executeQuery();
		while(rSet.next()){
			String title=rSet.getString(1);
			bWriter1.write(title+"\n");
		}
		//readline from the file
		BufferedReader brReader=new BufferedReader(new FileReader(new File(url)));
	    ArrayList<String> titles=new ArrayList<String>();
	    String str=null;
	    while((str=brReader.readLine())!=null){
	    	titles.add(str);
	    }
	    for(String title:titles){
	    	indexTitle(title,indexWriter);
	    }
	    bWriter1.close();
	    closeIndexWriter();
	}
	/************Search Part
	 * @throws IOException 
	 * @throws ParseException **************/
	
	public static void search(String queryStr,int num) throws IOException, ParseException{
		QueryParser parser=new QueryParser("Title", new StandardAnalyzer());

		Directory directory=FSDirectory.open(Paths.get("./indexFile"));
	    IndexReader reader=DirectoryReader.open(directory);
	    IndexSearcher searcher=new IndexSearcher(reader);
	    Query query=parser.parse(queryStr);
	    TopDocs docs=searcher.search(query, num);
	    System.out.println("the number of results: "+docs.totalHits);
	    File file2=new File("/Users/caoyi/Downloads/eclipse/Eclipse.app/Contents/MacOS/keyWordSearch.txt");
	    Writer writer2=new FileWriter(file2,false);
	    BufferedWriter bWriter2=new BufferedWriter(writer2);
	    for (ScoreDoc scoreDoc : docs.scoreDocs)  
        {   
	    	
	    	bWriter2.write("<p style='font-family:courier;'>order: " + scoreDoc.doc+" socre: " + scoreDoc.score+" title: " +searcher.doc(scoreDoc.doc).get("Title")+"</p></br>");
            System.out.print("order: " + scoreDoc.doc);  
            System.out.print(" socre: " + scoreDoc.score);  
            Document document = searcher.doc(scoreDoc.doc);  
            System.out.print(" title: " + document.get("Title"));  
            System.out.println();  
        }
	    bWriter2.close();
	}
	public static void main(String[]args) throws IOException, SQLException, ParseException{
		rebuildIndexes("./sourceFiles/journals.dbc",getIndexWriter(true));
		int num=100;
		String queryStr="Pattern Matching";
		search(queryStr, num);
	}
	
}



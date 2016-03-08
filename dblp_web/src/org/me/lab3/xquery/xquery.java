package org.me.lab3.xquery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import com.saxonica.xqj.SaxonXQDataSource;


public class xquery {
	private xquery() {

	}

	public static void main(String[] argv) {

		List<Test> tests = new ArrayList<Test>();
		tests.add(new selectTitle());
        tests.add(new selectFirstArticleTitle());
		tests.add(new selectAllAuthors());
		tests.add(new selectArcNodesByYear());
		tests.add(new selectTileNodesByYear());
		String test = "all";
		if (argv.length > 0) {
			test = argv[0];
		}

		boolean found = false;
		Iterator<Test> allTests = tests.iterator();
		while (allTests.hasNext()) {
			Test next = (Test) allTests.next();
			if (test.equals("all") || next.name().equals(test)) {
				found = true;
				try {
					
					System.out.println("\n==== " + next.name() + "====\n");
	
					next.run();
				} catch (XQException ex) {
					handleException(ex);
				}
			}
		}

		if (!found) {
			System.err.println("Please supply a valid test name, or 'all' ("
					+ test + "' is invalid)");
		}

	}

	public static void handleException(Exception ex) {
		System.out.print("Exception:" + ex);
		ex.printStackTrace();
	}

	public static class selectTitle implements Test {

		@Override
		public String name() {
			// TODO Auto-generated method stub
			return "selectTitle";
		}

		@Override
		public void run() throws XQException {
			InputStream inputStream;
			StringBuilder sBuilder=new StringBuilder();
		//	sBuilder.append("<article>"+"\n");
			try {
				inputStream = new FileInputStream(new File("selectTitle.xqy"));
				XQDataSource ds = new SaxonXQDataSource();
				XQConnection conn = ds.getConnection();
				XQPreparedExpression exp = conn.prepareExpression(inputStream);
				XQResultSequence result = exp.executeQuery();
				int count = 0;
				while (result.next() && count < 1000) {
					sBuilder.append(result.getItemAsString(null));

					count++;
				}
			   	xmlReader.writeFile(sBuilder.toString(), "selectTitle.xml");
			    
				
             xmlTohtml.transform("selectTitle.xml", "selectTitle.html", "selectTitles.xsl");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
    
	public static class selectFirstArticleTitle implements Test{

		@Override
		public String name() {
		
			return "select the title of the first article";
		}

		@Override
		public void run() throws XQException {
			 XQDataSource ds = new SaxonXQDataSource();
	         XQConnection conn = ds.getConnection();
	         StringBuilder sBuilder=new StringBuilder();
	         sBuilder.append("<dblp><article>");
	     
	          XQPreparedExpression exp=conn.prepareExpression("doc('dblp.sample.xml')/dblp/article[1]/title");
             XQResultSequence result=exp.executeQuery();
             while(result.next()){
            	sBuilder.append(result.getItemAsString(null));
            	
            	
             }
			sBuilder.append("</article></dblp>");
		
			xmlReader.writeFile(sBuilder.toString(), "firstTitle.xml");
			xmlTohtml.transform("firstTitle.xml", "firstTitle.html", "selectTitles.xsl");
		}
		
	}
    
	public static class selectAllAuthors implements Test{

		@Override
		public String name() {
			// TODO Auto-generated method stub
			return "selelct all authors";
		}

		@Override
		public void run() throws XQException {
			 XQDataSource ds = new SaxonXQDataSource();
	          XQConnection conn = ds.getConnection();
	          String queryString="doc('dblp.sample.xml')/dblp/article/author";
	          XQPreparedExpression exp = conn.prepareExpression(queryString);
	         XQResultSequence result=exp.executeQuery();
	         StringBuilder sBuilder=new StringBuilder();
	         sBuilder.append("<dblp>");
	        int count=0;
	             while(result.next()&&count<1000){
	            	 sBuilder.append("<article>");
	            	 sBuilder.append(result.getItemAsString(null));
	            	 sBuilder.append("</article>");
//	            	 System.out.println(result.getItemAsString(null));
	            	 count++;
	             }
	             sBuilder.append("</dblp>");
	             xmlReader.writeFile(sBuilder.toString(), "authors.xml");
	 			xmlTohtml.transform("authors.xml", "authors.html", "authors.xsl");

		}
		
		
		
		
		
	}
    
	public static class selectArcNodesByYear implements Test{

		@Override
		public String name() {
			
			return "Select article nodes with publication data later than 2010";
		}

		@Override
		public void run() throws XQException {
			InputStream inputStream;
			try {
				inputStream = new FileInputStream(new File("selectArcNodeByYear.xqy"));
				XQDataSource ds = new SaxonXQDataSource();
				XQConnection conn = ds.getConnection();
				XQPreparedExpression exp = conn.prepareExpression(inputStream);
				XQResultSequence result = exp.executeQuery();
				 StringBuilder sBuilder=new StringBuilder();
				int count = 0;

				while (result.next() && count < 1000) {
					sBuilder.append(result.getItemAsString(null));
					count++;
				}
				 xmlReader.writeFile(sBuilder.toString(), "articles.xml");
		 	     xmlTohtml.transform("articles.xml", "articles.html", "selectArcNodeByYear.xsl");
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        
		}
		
	}
     
	public static class selectTileNodesByYear implements Test{

		@Override
		public String name() {
			
			return "Select title nodes with publication data later than 2010";
		}

		@Override
		public void run() throws XQException {
			InputStream inputStream;
			try {
				 StringBuilder sBuilder=new StringBuilder();
				inputStream = new FileInputStream(new File("selectTitleNodeByYear.xqy"));
				XQDataSource ds = new SaxonXQDataSource();
				XQConnection conn = ds.getConnection();
				XQPreparedExpression exp = conn.prepareExpression(inputStream);
				XQResultSequence result = exp.executeQuery();
				int count = 0;
				while (result.next() && count < 1000) {
					sBuilder.append(result.getItemAsString(null));
					count++;
				}
				 xmlReader.writeFile(sBuilder.toString(), "titles.xml");
		 	     xmlTohtml.transform("titles.xml", "titles.html", "selectTitleNodeByYear.xsl");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        
		}
		
	}
	
	
}

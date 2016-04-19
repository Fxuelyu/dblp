package dblpSparql;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

/***
 * This class is used to implement the queries from RDF with sparql
 * @author caoyi
 *
 */
/***
-Find me all publications from a given user name;

-Find all publications of a user on a specific time range;

-Find co-authors of an author;

-Find the detailed information of a paper given a paper title;

-Find all papers given some keywords.
 * @author caoyi
 *
 */
public class dblpQueries {
	public static void main(String[]args){
		String string=null;
		//string=queryByTimeRange("Sheng Yu", "1998", "2001");
      //  string=queryByName("Sheng Yu");
        //string= queryByKeyword("matching");
    	//string=queryCoAuthor("Sheng Yu");
     //	string=queryDetails("3D CAD model matching from 2D local invariant features.");
		//System.out.println(string);
	}
	
     public static String queryByName(String name){
        //this method is used to find all publications with a given name
  	   //query those co-authors and the article detail
		 File file =new File("/Users/caoyi/Documents/SOC/Yi Cao_Lab12/dblp.rdf");
  	    Model queyModel=ModelFactory.createDefaultModel();
  	    InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		queyModel.read( inputStream ,"");	
  		//queyModel.write(System.out);
  		 String s=null;
    	 String queryString = "select ?title ?author"
     	 		+ " where {?arc <http://www.me.org/dblp/title> ?title."
     	 		+ " ?arc <http://www.me.org/dblp/author> ?author."
     	 		+ " filter( regex(?author,"
     	 		+ " '"+name+"' )) }" ;
    	  Query query = QueryFactory.create(queryString) ;
    	  try (QueryExecution qexec = QueryExecutionFactory.create(query,queyModel)) {
    	    ResultSet results = qexec.execSelect() ;
    	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	    PrintStream ps = new PrintStream(baos);
    	    ResultSetFormatter.out(ps, results, query) ;
    	   
    	    try {
				s = new String(baos.toByteArray(), "UTF-8");
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   
    	  }
    	   return s;
     }
     
     //Find all publications of a user on a specific time range;
     public static String queryByTimeRange(String name,String low, String high){
    	 String s=null;
    	 File file =new File("/Users/caoyi/Documents/SOC/Yi Cao_Lab12/dblp.rdf");
    	 Model queyModel=ModelFactory.createDefaultModel();
   	    InputStream inputStream = null;
 		try {
 			inputStream = new FileInputStream(file);
 		} catch (FileNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
   		queyModel.read( inputStream ,"");	
   		//queyModel.write(System.out);
     	 String queryString = "select ?title ?author ?year"
     	 		+ " where {?arc <http://www.me.org/dblp/title> ?title."
     	 		+ " ?arc <http://www.me.org/dblp/author> ?author."
     	 		+ " ?arc <http://www.me.org/dblp/year> ?year."
     	 		+ " filter( regex(?author,"
     	 		+ " '"+name+"') &&"
     	 		+ " ?year >='"+low+"' && ?year <='"+high+"' )}" ;
     	  Query query = QueryFactory.create(queryString) ;
     	  try (QueryExecution qexec = QueryExecutionFactory.create(query,queyModel)) {
     	ResultSet results = qexec.execSelect() ;
     	ByteArrayOutputStream baos = new ByteArrayOutputStream();
   	    PrintStream ps = new PrintStream(baos);
   	    ResultSetFormatter.out(ps, results, query) ;
   	   
   	    try {
				s = new String(baos.toByteArray(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  
   	  }
   	   
   	   return s;
    }
  
     
     //Find co-authors of an author;
     public static String queryCoAuthor(String name){
    	 File file =new File("/Users/caoyi/Documents/SOC/Yi Cao_Lab12/dblp.rdf");
         String s=null;
    	 Model queyModel=ModelFactory.createDefaultModel();
    	    InputStream inputStream = null;
  		try {
  			inputStream = new FileInputStream(file);
  		} catch (FileNotFoundException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
    		queyModel.read( inputStream ,"");	
    		//queyModel.write(System.out);
    		String queryString = "select  ?author"
        	 		+ " where {"
        	 		+ " ?arc <http://www.me.org/dblp/author> ?author."
        	 		+ " filter( regex(?author,"
        	 		+ " '"+name+"' )) }" ;
      	  Query query = QueryFactory.create(queryString) ;
      	  try (QueryExecution qexec = QueryExecutionFactory.create(query,queyModel)) {
      	    ResultSet results = qexec.execSelect() ;
      	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
     	    PrintStream ps = new PrintStream(baos);
     	    ResultSetFormatter.out(ps, results, query) ;
     	   
     	    try {
  				s = new String(baos.toByteArray(), "UTF-8");
  				
  			} catch (UnsupportedEncodingException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
    
     	  }
     	 
     	   return s;
     }
     //Find the detailed information of a paper given a paper title;
     public static String queryDetails(String title){
    	 File file =new File("/Users/caoyi/Documents/SOC/Yi Cao_Lab12/dblp.rdf");
    	 String s=null;
    	 Model queyModel=ModelFactory.createDefaultModel();
 	    InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		queyModel.read( inputStream ,"");	
 		//queyModel.write(System.out);
 		String queryString = "select  ?title ?author ?year ?url ?pages ?ee ?journal ?volume "
     	 		+ " where {"
     	 		+ " ?arc <http://www.me.org/dblp/title> ?title."
     	 		+ " ?arc <http://www.me.org/dblp/author> ?author."
     	 		+ " ?arc <http://www.me.org/dblp/year> ?year."
     	 		+ " ?arc <http://www.me.org/dblp/url> ?url."
     	 		+ " ?arc <http://www.me.org/dblp/pages> ?pages."
     	 		+ " ?arc <http://www.me.org/dblp/ee> ?ee."
     	 		+ " ?arc <http://www.me.org/dblp/journal> ?journal."
     	 		+ " ?arc <http://www.me.org/dblp/volume> ?volume."
     	 		+ " filter(?title='"+title+"') }" ;
   	  Query query = QueryFactory.create(queryString) ;
   	  try (QueryExecution qexec = QueryExecutionFactory.create(query,queyModel)) {
   	    ResultSet results = qexec.execSelect() ;
   	 ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(baos);
	    ResultSetFormatter.out(ps, results, query) ;
	   
	    try {
				s = new String(baos.toByteArray(), "UTF-8");
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	  }
	 
	   return s;
     }
     
     //Find all papers given some keywords.
     public static String queryByKeyword(String keyword){
    	 File file =new File("/Users/caoyi/Documents/SOC/Yi Cao_Lab12/dblp.rdf");
         String s=null;
    	 Model queyModel=ModelFactory.createDefaultModel();
   	    InputStream inputStream = null;
 		try {
 			inputStream = new FileInputStream(file);
 		} catch (FileNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
   		queyModel.read( inputStream ,"");	
   		//queyModel.write(System.out);
   		String queryString = "select  ?title ?author ?year ?url ?pages ?ee ?journal ?volume "
     	 		+ " where {"
     	 		+ " ?arc <http://www.me.org/dblp/title> ?title."
     	 		+ " ?arc <http://www.me.org/dblp/author> ?author."
     	 		+ " ?arc <http://www.me.org/dblp/year> ?year."
     	 		+ " ?arc <http://www.me.org/dblp/url> ?url."
     	 		+ " ?arc <http://www.me.org/dblp/pages> ?pages."
     	 		+ " ?arc <http://www.me.org/dblp/ee> ?ee."
     	 		+ " ?arc <http://www.me.org/dblp/journal> ?journal."
     	 		+ " ?arc <http://www.me.org/dblp/volume> ?volume."
     	 		+ " filter( regex(?title, '"+keyword+"')) }" ;
     	
     	  Query query = QueryFactory.create(queryString) ;
     	  try (QueryExecution qexec = QueryExecutionFactory.create(query,queyModel)) {
     	    ResultSet results = qexec.execSelect() ;
     	   ByteArrayOutputStream baos = new ByteArrayOutputStream();
      	    PrintStream ps = new PrintStream(baos);
      	    ResultSetFormatter.out(ps, results, query) ;
      	   
      	    try {
   				s = new String(baos.toByteArray(), "UTF-8");
   				
   			} catch (UnsupportedEncodingException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
     
      	  }
      	 
      	   return s;
     	   
     }
	
}

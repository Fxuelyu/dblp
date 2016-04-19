package dblpTransformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

/****
 * This method is used to query model
 * @author caoyi
 *
 */
public class queryModel {
	static File file =new File("./dblp.rdf");
   public static void main(String[]args) throws FileNotFoundException{
	   xmlToRdf.transform("./dblp.sample.xml","./dblp.rdf" , "./template.xsl");
	  
	   getArticles("Sheng Yu");
      
	   
   }
   
 
   private static void getArticles(String authorname) throws FileNotFoundException{
	   //query those co-authors and the article detail
	   Model queyModel=ModelFactory.createDefaultModel();
	    InputStream inputStream=new FileInputStream(file);
		queyModel.read( inputStream ,"");	
		//queyModel.write(System.out);
    	Property title = queyModel.getProperty("http://www.me.org/dblp/title");
    	Property author=queyModel.getProperty("http://www.me.org/dblp/author");
    	Property year=queyModel.getProperty("http://www.me.org/dblp/year");
    	Property journal=queyModel.getProperty("http://www.me.org/dblp/journal");
    	Property pages=queyModel.getProperty("http://www.me.org/dblp/pages");
    	Property volume=queyModel.getProperty("http://www.me.org/dblp/volume");
    	Property url=queyModel.getProperty("http://www.me.org/dblp/url");
    	Property ee=queyModel.getProperty("http://www.me.org/dblp/ee");
    	ArrayList<String> titles=new ArrayList<String>();
    	ResIterator resIterator=queyModel.listResourcesWithProperty(author);
    	while(resIterator.hasNext()){
    		Resource resource=resIterator.nextResource();
    		if(resource.getProperty(author)!=null){
               if(resource.getProperty(author).getLiteral().toString().contains(authorname)){
           		 System.out.println("title:"+resource.getProperty(title).getLiteral());
           		 System.out.println("author:"+resource.getProperty(author).getLiteral().toString().replaceAll(";", " "));
           		 System.out.println("journal:"+resource.getProperty(journal).getLiteral());
           		 System.out.println("year:"+resource.getProperty(year).getLiteral());
           		 System.out.println("pages:"+resource.getProperty(pages).getLiteral());
           		 System.out.println("url:"+resource.getProperty(url).getLiteral());
           		 System.out.println("volume:"+resource.getProperty(volume).getLiteral());
           		 System.out.println("ee:"+resource.getProperty(ee).getLiteral());
           		 System.out.println("==========================================================");
               }
    		}
    	}
    	
    	
   }
 }

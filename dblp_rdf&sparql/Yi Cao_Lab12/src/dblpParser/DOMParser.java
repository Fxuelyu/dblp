package dblpParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.mem.ObjectIterator;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.VCARD;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.corba.se.pept.transport.Selector;
import com.sun.org.apache.bcel.internal.generic.Select;

public class DOMParser {
	static File file =new File("./result.rdf");
	static Model model=ModelFactory.createDefaultModel();
	public static void main(String[] args) {
		
		// 截取xml文档的前3000条数据
		File xmlfile = new File(
				"./rescources/dblp.sample.xml");

		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		ArrayList<Article> act = new ArrayList<Article>();
		NodeList newnodeList;
		int count = 0;
		int i = 0;
		int numbers = 0;
		try {
			dBuilder = dbfactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlfile);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getDocumentElement().getChildNodes();
			while (count != 3000) {
				Node node = nodeList.item(i);
				if (node.getNodeName().equals("article")) {
					count++;
					Article ac = new Article();
					ac.mdate = node.getAttributes().getNamedItem("mdate")
							.getNodeValue();
					ac.key = node.getAttributes().getNamedItem("key")
							.getNodeValue();

					NodeList childNodes = node.getChildNodes();

					for (int k = 0; k < childNodes.getLength(); k++) {
						Node cNode = childNodes.item(k);
						if (cNode instanceof Element) {
							String content = cNode.getLastChild()
									.getTextContent().trim();
							switch (cNode.getNodeName()) {
							case "title":
								ac.title = content;
								break;
							case "pages":
								ac.pages = content;
								break;
							case "year":
								ac.year = Integer.parseInt(content);
								break;
							case "volume":
								ac.volume = Integer.parseInt(content);
								break;
							case "journal":
								ac.journal = content;
								break;
							case "number":
								ac.number = content;
								break;
							case "ee":
								ac.ee = content;
								break;
							case "url":
								ac.url = content;
								break;
							case "author":
								ac.authors.add(content);
								break;

							}
						}
					}
					act.add(ac);
					
				}
				i++;
			}
 			
			RdfGen(act);
			ArrayList<String> titles=new ArrayList<String>();
            titles= getArcticleTitle("Pattern");
            for(String title: titles){
            	System.out.println("t:"+title);
            
            }
            System.out.println("+++++++++++++++++++++++++++++++++++++");
            getArcticleInfo(titles);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/***
	 * This method is used to generate RDF_XML file
	 * @throws FileNotFoundException 
	 */
	private static void RdfGen(ArrayList<Article> act) throws FileNotFoundException{
		String nString="http://www.me.org/dblp/";
		
		//specify the Prefixes
		//the subject is article 
		// the predicts include  author, title, pages, year, volume, journal, number, url, ee
		
       for(int i=0;i<act.size();i++){
    	    Resource resource=model.createResource(nString+"article"+i);
		    Property author=model.createProperty(nString+"author");
		    Property title=model.createProperty(nString+"title");
		    Property pages=model.createProperty(nString+"pages");
		    Property year=model.createProperty(nString+"year");
		    Property volume=model.createProperty(nString+"volume");
		    Property journal=model.createProperty(nString+"journal");
		    Property number=model.createProperty(nString+"number");
		    Property url=model.createProperty(nString+"url");
		    Property ee=model.createProperty(nString+"ee");
		    resource.addProperty(author, act.get(i).getAuthors().toString(), XSDDatatype.XSDstring)
		    .addProperty(title, act.get(i).getTitle(), XSDDatatype.XSDstring)
		    .addProperty(pages, act.get(i).getPages().toString(), XSDDatatype.XSDstring)
		    .addProperty(year, String.valueOf(act.get(i).getYear()), XSDDatatype.XSDstring)
		    .addProperty(volume, String.valueOf(act.get(i).getVolume()), XSDDatatype.XSDstring)
		    .addProperty(journal, act.get(i).getJournal(), XSDDatatype.XSDstring)
		    .addProperty(number, String.valueOf(act.get(i).getNumber()), XSDDatatype.XSDstring)
		    .addProperty(url, act.get(i).getUrl(), XSDDatatype.XSDstring)
		    .addProperty(ee, act.get(i).getEe(), XSDDatatype.XSDstring);
       }
       model.write(new FileOutputStream(file));
       
	}
	/***
	 * This method is used to query the RDF
	 * @throws FileNotFoundException 
	 */
	private static ArrayList<String> getArcticleTitle(String titlename) throws FileNotFoundException{
     	Model queyModel=ModelFactory.createDefaultModel();
     	ArrayList<String> titles=new ArrayList<String>();
     	Property title = model.getProperty("http://www.me.org/dblp/title");
		InputStream inputStream=new FileInputStream(file);
		queyModel.read( inputStream ,"");
		StmtIterator iter = queyModel.listStatements(
			    new SimpleSelector(null,title, (RDFNode)null) {
			        public boolean selects(Statement s) {	        
			        	return s.getString().contains(titlename);
			        	}
			    });
		if (iter.hasNext()) {
           while (iter.hasNext()) {
        	   titles.add(iter.nextStatement().getString());
           }
       } else {
           System.out.println("Not Found!");
       }
		return titles;   
	}
	private static void getArcticleInfo(ArrayList<String> titles) throws FileNotFoundException{
		//find all articles info with titles
		Model queyModel=ModelFactory.createDefaultModel();
     	
     	Property title = model.getProperty("http://www.me.org/dblp/title");
     	Property author=model.getProperty("http://www.me.org/dblp/author");
     	Property year=model.getProperty("http://www.me.org/dblp/year");
     	Property journal=model.getProperty("http://www.me.org/dblp/journal");
     	Property pages=model.getProperty("http://www.me.org/dblp/pages");
     	Property volume=model.getProperty("http://www.me.org/dblp/volume");
     	Property url=model.getProperty("http://www.me.org/dblp/url");
     	Property ee=model.getProperty("http://www.me.org/dblp/ee");
		InputStream inputStream=new FileInputStream(file);
		queyModel.read( inputStream ,"");		

	for(int i=0;i<titles.size();i++){
		ResIterator resIterator=queyModel.listResourcesWithProperty(null);
    	while(resIterator.hasNext()){
    		Resource resource=resIterator.nextResource();
			 if(resource.getProperty(title).getLiteral().toString().contains(titles.get(i))){
		
         		    System.out.println("title:"+resource.getProperty(title).getLiteral());
				
         		    System.out.println("author:"+resource.getProperty(author).getLiteral());
         		    System.out.println("journal:"+resource.getProperty(journal).getLiteral());
         		   System.out.println("year:"+resource.getProperty(year).getLiteral());
         		  System.out.println("pages:"+resource.getProperty(pages).getLiteral());
         		 System.out.println("url:"+resource.getProperty(url).getLiteral());
         		 System.out.println("volume:"+resource.getProperty(volume).getLiteral());
         		 System.out.println("ee:"+resource.getProperty(ee).getLiteral());
         		 System.out.println("=====================================");
		}
	 }
		
}
	
	
}}

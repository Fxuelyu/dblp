package org.me.dblp_parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.me.DBConnection.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.me.dblp_parser.*;


public class DOMParser extends Authorsid{
	
	public static void main(String[] args) {
		PreparedStatement stmt_journals, stmt_authors, stmt_system;
		Connection conn;
		// 截取xml文档的前3000条数据
		File xmlfile = new File(
				"/Users/caoyi/Documents/workspace-lunaee/dblp_parser/rescoures/dblp.sample.xml");

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

//			for (Article ac : act) {
//				//System.out.println(ac);
//				numbers++;
//			}
			//System.out.print(numbers);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {
			conn = DBConnection.getConn();
			//conn.setAutoCommit(false);
			String sql1="insert into authors(name) values (?)";
			String sql2="insert into journals(authorid, title, journalname, year, pages, volume, number, ee, url) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
		    String sql3="delete from authors;";
		    String sql4="delete from journals;";
		    String sql5="alter table authors auto_increment=1;";
		    String sql6="alter table journals auto_increment=1;";
			stmt_authors=conn.prepareStatement(sql1);
		    stmt_journals=conn.prepareStatement(sql2);
//		    conn.prepareStatement(sql3).executeUpdate();
//		    conn.prepareStatement(sql4).executeUpdate();
		     Authorsid.insert(act);
		     conn.prepareStatement(sql3).executeUpdate();
		     conn.prepareStatement(sql5).executeUpdate();
		    for(Map.Entry<Integer, String> entry: Authorsid.authorsmap.entrySet()){
		    	//System.out.println(entry.getValue());
		    	stmt_authors.setString(1, entry.getValue());
		    	 
		    	stmt_authors.executeUpdate();
		    }
		   
		    conn.prepareStatement(sql4).executeUpdate();
		    conn.prepareStatement(sql6).executeUpdate();
		  for (Article ac : act) {
			  stmt_journals.setString(1,Coauthors.findcoauthors(ac, Authorsid.authorsmap));
			  stmt_journals.setString(2,ac.title);
			  stmt_journals.setString(3,ac.journal);
			  stmt_journals.setLong(4,ac.year);
			  stmt_journals.setString(5,ac.pages);
			  stmt_journals.setLong(6,ac.volume);
			  stmt_journals.setString(7,ac.number);
			  stmt_journals.setString(8,ac.ee);
			  stmt_journals.setString(9,ac.url);
			  stmt_journals.executeUpdate();
			}
		  
		   
		   
		    conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	//
}

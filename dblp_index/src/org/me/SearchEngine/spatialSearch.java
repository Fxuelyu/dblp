package org.me.SearchEngine;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.me.DBConnection.DBConnection;

public class spatialSearch extends keyWordSearch{
    public spatialSearch(IndexWriter indexWriter, Directory indexDir) {
		super(indexWriter, indexDir);
		// TODO Auto-generated constructor stub
	}

	//spatial search
	//1. create the spatial index : (paperId, year);
	public static void createIndex(){
	PreparedStatement stmt_journal,stmt_title, stmt_g1,stmt_g2;
	Connection connection=DBConnection.getConn();
	String sql="select id, title, year from journals";
	String sqlString1="insert into journals_index(title) values(?)";
//	String sqlString2="INSERT INTO journals_index(g) VALUES (GeomFromText(@g));";
//	String sqlString3="SET @g = 'POINT(? ?)';";
	try{
		stmt_journal=connection.prepareStatement(sql);
		stmt_title=connection.prepareStatement(sqlString1);
//     	stmt_g1=connection.prepareStatement(sqlString2);
//		stmt_g2=connection.prepareStatement(sqlString3);
		ResultSet rSet=stmt_journal.executeQuery();
		
		while(rSet.next()){
			//now beagin to insert values to the jounrals_index
		String id=String.valueOf(rSet.getInt(1));
		String title=rSet.getString(2);
		String year=rSet.getString(3);
		System.out.println(title);
		if(title==null) break;
		stmt_title.setString(1, title);
		stmt_title.executeUpdate();
		if(id==null||year==null){
			id="-1";
			year="-1";
		}
	   String id_year="INSERT INTO journals_index(g) VALUES (GeomFromText('POINT("+id+" "+year+")'));";
	  // System.out.println(id_year);
		stmt_g1=connection.prepareStatement(id_year);
		stmt_g1.executeUpdate();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
	
	public static void searchSpatial(String latitude, String longtitude) throws SQLException, IOException, ParseException{
		//the  rectangular like: 1-m(the papers number) as row and latitude to longtitude as col
		String string="SET @poly='polygon("
+"(1 "+latitude+","
+"2999 "+latitude+","
+"2999 "+longtitude+","
+"1 "+longtitude+","
+"1 "+latitude+""
+")"
+")';";
	  PreparedStatement stmt_search1,stmt_search2,stmt_jounral;
	  Connection connection=DBConnection.getConn();
	  stmt_search1=connection.prepareStatement(string);
	  stmt_search2=connection.prepareStatement("select astext(g) from journals_index where mbrcontains(geomfromtext(@poly),g);");
	  String sql="select title from journals where id=?;";
	  stmt_jounral=connection.prepareStatement(sql);
	  stmt_search1.execute();
	  ResultSet rSet=stmt_search2.executeQuery();
	  while(rSet.next()){
		 String str=String.valueOf(rSet.getObject(1));
		 str=str.substring(6, str.indexOf(" "));
		// System.out.println(str);
		 //select the paper title from journals table where id=str
		 stmt_jounral.setString(1, str);
		 ResultSet rSet2=stmt_jounral.executeQuery();
		 IndexWriter writer=getIndexWriter(true);
		 while(rSet2.next()){
			 String targetTitle=rSet2.getString(1);
			 indexTitle(targetTitle,writer);
		 }
	  }
	  closeIndexWriter();
	  
	  
	}
public static void main(String[]args) throws IOException, SQLException, ParseException{
		//createIndex();
		searchSpatial("1994", "1998");
		search("Pattern Matching",10);
	}
	
	
	
}

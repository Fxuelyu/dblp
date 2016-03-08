package org.me.dblp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.me.DBConnection.DBConnection;
import org.me.dblp_parser.Article;

public class findPublications {
	public static void main(String[]args) throws SQLException{
		
		System.out.print(findpublications("Sheng Yu")); 
	}
	public static String findpublications(String authorname) throws SQLException {
		   ResultSet rs=null;
		   ResultSet rs3=null;
		   
		   StringBuilder pub=new StringBuilder();
		   Article ac=new Article();
		   StringBuilder sb=new StringBuilder();
		   PreparedStatement stmt_authors = null, stmt_journals;
			Connection conn = DBConnection.getConn();
			int id = 0;
		   ArrayList<String> authors=new ArrayList<String>();
		try{
			String sql1="select id from authors where name=?;";
			String sql2="select authorid from journals;";
			stmt_authors=conn.prepareStatement(sql1);
			stmt_authors.setString(1, authorname);
			ResultSet rs1=stmt_authors.executeQuery();
			while(rs1.next()){
				 id=rs1.getInt(1);
			}
		  	
		  	stmt_journals=conn.prepareStatement(sql2);
			ResultSet rs2=stmt_journals.executeQuery();
         while(rs2.next()){
         	String str=rs2.getString(1);
         	if(str.contains(String.valueOf(id))){
         	  sql2="select * from journals where authorid=?;";
         	  stmt_journals=conn.prepareStatement(sql2);
         	  stmt_journals.setString(1, str); 
         	  rs=stmt_journals.executeQuery();
         	  while(rs.next()){
             	  pub.append(("publication detail:"+rs.getString(3)+"\n"+rs.getString(4)+"\n"+rs.getString(5)+"\n"+rs.getString(6)+"\n"+rs.getInt(7)+"\n"+rs.getString(8)+"\n"+rs.getString(9)+"\n"+rs.getString(10)));
         	  }
        
  
         	}
         }
		}catch(Exception e){
			e.printStackTrace();
		}
		return pub.toString();
		
		
	}
}

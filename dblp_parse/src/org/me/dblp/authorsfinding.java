package org.me.dblp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.me.DBConnection.DBConnection;
import org.me.dblp_parser.Article;

public class authorsfinding {
	public static void main(String[]args) throws SQLException{
		
		if(judgecoauthors("Sheng Yu","Nicolae Santean")){
			System.out.print(findpublications("Sheng Yu","Nicolae Santean"));
		}else{
			
		}
	}
	
	public static boolean judgecoauthors(String authorname1,String authorname2) {
		
	     String str=findCoauthors(authorname1);
	     if(str.contains(authorname2)){
	    	 return true;
	     }
		return false;
		
	}
	public static String findCoauthors (String authorname1) {
		PreparedStatement stmt_authors = null, stmt_journals;
		Connection conn = DBConnection.getConn();
		String sql1="select id from authors where name=?;";
		String sql2="select authorid from journals;";
		String sql3="select name from authors where id=?;";
		StringBuilder sb=new StringBuilder();
		try {
             int id=0;
			stmt_authors=conn.prepareStatement(sql1);
			stmt_authors.setString(1, authorname1);
			ResultSet rs1=stmt_authors.executeQuery();
			while(rs1.next()){
			 id=rs1.getInt(1);
			}
			
			System.out.println("id:"+id);
			stmt_journals=conn.prepareStatement(sql2);
			ResultSet rs2=stmt_journals.executeQuery();
			
			while(rs2.next()){
				String str=rs2.getString(1);
				if(str.contains(String.valueOf(id))){
					String[] buff1=str.split(",");
					for(int i=0;i<buff1.length;i++){
						if((sb.indexOf(buff1[i]))==-1){
							stmt_authors=conn.prepareStatement(sql3);
							stmt_authors.setString(1, buff1[i]);
							ResultSet rs3=stmt_authors.executeQuery();
							while(rs3.next()){
								sb.append(rs3.getString(1)+",");
							}
							
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();

	}
	public static String findpublications(String authorname1,String authorname2) throws SQLException {
		   ResultSet rs=null;
		  
		   
		   StringBuilder pub=new StringBuilder();
		  
		   PreparedStatement stmt_authors1,stmt_authors2, stmt_journals;
			Connection conn = DBConnection.getConn();
			int id1 = 0;
		    int id2=0;
		try{
			String sql1="select id from authors where name=?;";
			String sql2="select authorid from journals;";
			stmt_authors1=conn.prepareStatement(sql1);
			stmt_authors1.setString(1, authorname1);
			ResultSet rs1=stmt_authors1.executeQuery();
			stmt_authors2=conn.prepareStatement(sql1);
			stmt_authors2.setString(1, authorname2);
			ResultSet rs2=stmt_authors2.executeQuery();
			while(rs1.next()){
				 id1=rs1.getInt(1);
			}
		  	while(rs2.next()){
		  		 id2=rs2.getInt(1);
		  	}
		  	stmt_journals=conn.prepareStatement(sql2);
			ResultSet rs3=stmt_journals.executeQuery();
      while(rs3.next()){
      	String str=rs3.getString(1);
      	if(str.contains(String.valueOf(id1)) &&  str.contains(String.valueOf(id2))){
      	  sql2="select * from journals where authorid=?;";
      	  stmt_journals=conn.prepareStatement(sql2);
      	  stmt_journals.setString(1, str); 
      	  rs=stmt_journals.executeQuery();
      	  while(rs.next()){
          	  pub.append((rs.getString(3)+"\n"+rs.getString(4)+"\n"+rs.getString(5)+"\n"+rs.getString(6)+"\n"+rs.getInt(7)+"\n"+rs.getString(8)+"\n"+rs.getString(9)+"\n"+rs.getString(10)));
      	  }
     

      	}
      }
		}catch(Exception e){
			e.printStackTrace();
		}
		return pub.toString();
		
		
	}
}

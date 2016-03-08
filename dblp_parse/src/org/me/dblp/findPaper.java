package org.me.dblp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.me.DBConnection.DBConnection;
import org.me.dblp_parser.Article;

public class findPaper {
  public static void main(String[]args){
	  System.out.println(findpaper("Pattern Matching in Trees and Nets."));
  }
  public static String findpaper(String papertitle) {
		String sql2="select * from journals where title=?;";
		PreparedStatement stmt_authors , stmt_journals;
		Connection conn = DBConnection.getConn();
		ResultSet rs=null;
		ResultSet rs2=null;
		Article ac = new Article();
		ArrayList<String> authors=new ArrayList<String>();
		StringBuilder sb=new StringBuilder();
		String str = null;
		
		try {
			stmt_journals=conn.prepareStatement(sql2);
			stmt_journals.setString(1, papertitle);
			rs=stmt_journals.executeQuery();
			 while(rs.next()){
				  str=rs.getString(2);
				  System.out.print(str);
				  String[] buff=str.split(",");
				  for(int i=0;i<buff.length;i++){
					  stmt_authors=conn.prepareStatement("select name from authors where id=?;");
					  stmt_authors.setString(1, buff[i]);
					  rs2=stmt_authors.executeQuery();
					  while(rs2.next()){
						  String author=rs2.getString(1);
						  authors.add(author);
					  }
					 
				  }
				  ac.setAuthors(authors);
			      ac.setTitle(rs.getString(3));
			      ac.setJournal(rs.getString(4));
			      ac.setYear(rs.getInt(5));
			      ac.setPages(rs.getString(6));
			      ac.setVolume(rs.getInt(7));
			      ac.setNumber(rs.getString(8));
			      ac.setEe(rs.getString(9));
			      ac.setUrl(rs.getString(10));
			      sb.append(ac);
			 }
			
			  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
		
	}
}

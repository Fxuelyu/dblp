package org.me.dblp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.me.DBConnection.DBConnection;



public class key_wordsfinding {
	public static void main(String[]args){
		System.out.print(keywordsfinding("Pattern"));
	}
	public static String keywordsfinding( String keywords) {
		StringBuilder sb=new StringBuilder();
		PreparedStatement stmt_authors, stmt_journals;
		Connection conn = DBConnection.getConn();
		try {
			stmt_journals=conn.prepareStatement("select title from journals;");
		    ResultSet rs=stmt_journals.executeQuery();
			while(rs.next()){
				String title=rs.getString(1);
				if(title.contains(keywords)){

					sb.append(title+"\n");
				
				}
			}
			//
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
		
	}
}

package org.me.dblp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.WebMethod;
import javax.jws.WebParam;

import org.me.DBConnection.DBConnection;

public class findco_authors {
	public static void main(String[]args){
		String authorname="Sheng Yu";
		String str=findCoauthors(authorname);
		System.out.print(str);



	}
	public static String findCoauthors (String authorname) {
		PreparedStatement stmt_authors = null, stmt_journals;
		Connection conn = DBConnection.getConn();
		String sql1="select id from authors where name=?;";
		String sql2="select authorid from journals;";
		String sql3="select name from authors where id=?;";
		StringBuilder sb=new StringBuilder();
		try {
             int id=0;
			stmt_authors=conn.prepareStatement(sql1);
			stmt_authors.setString(1, authorname);
			ResultSet rs1=stmt_authors.executeQuery();
			while(rs1.next()){
			 id=rs1.getInt(1);
			}
			
			System.out.println("id:"+id);
			stmt_journals=conn.prepareStatement(sql2);
			ResultSet rs2=stmt_journals.executeQuery();
			
			while(rs2.next()){
				String str=rs2.getString(1);
//				String[] buff=str.split(",");
//				System.out.println(buff.length);
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
}

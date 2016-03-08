package org.me.dblp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.me.DBConnection.DBConnection;

public class test {
  public static void main(String[]args){
	  System.out.print(finddetails("Sheng Yu","Nicolae Santean"));
	  
  }
  public static String finddetails(String authorname1,String authorname2){
	  PreparedStatement stmt_authors , stmt_journals;
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
		
		
		String str=sb.toString();
		
		if(str.contains(authorname2)){
			StringBuilder pub=new StringBuilder();
			   PreparedStatement stmt_authors1,stmt_authors2, stmt_journals1;
				int id1 = 0;
			    int id2=0;
			try{
				String sql5="select id from authors where name=?;";
				String sql4="select authorid from journals;";
				stmt_authors1=conn.prepareStatement(sql5);
				stmt_authors1.setString(1, authorname1);
				ResultSet rs1=stmt_authors1.executeQuery();
				stmt_authors2=conn.prepareStatement(sql5);
				stmt_authors2.setString(1, authorname2);
				ResultSet rs2=stmt_authors2.executeQuery();
				while(rs1.next()){
					 id1=rs1.getInt(1);
				}
			  	while(rs2.next()){
			  		 id2=rs2.getInt(1);
			  	}
			  	stmt_journals1=conn.prepareStatement(sql2);
				ResultSet rs3=stmt_journals1.executeQuery();
	    while(rs3.next()){
	    	String str5=rs3.getString(1);
	    	if(str5.contains(String.valueOf(id1)) &&  str5.contains(String.valueOf(id2))){
	    	 String sql6="select * from journals where authorid=?;";
	    	  stmt_journals1=conn.prepareStatement(sql6);
	    	  stmt_journals1.setString(1, str5); 
	    	  ResultSet rsnew=stmt_journals1.executeQuery();
	    	  while(rsnew.next()){
	        	  pub.append((rsnew.getString(3)+"\n"+rsnew.getString(4)+"\n"+rsnew.getString(5)+"\n"+rsnew.getString(6)+"\n"+rsnew.getInt(7)+"\n"+rsnew.getString(8)+"\n"+rsnew.getString(9)+"\n"+rsnew.getString(10)));
	    	  }
	   

	    	}
	    }
			}catch(Exception e){
				e.printStackTrace();
			}
			return pub.toString();
		}
		return null;
		 
  }
}

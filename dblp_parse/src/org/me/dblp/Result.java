package org.me.dblp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.me.DBConnection.DBConnection;
//import org.me.DBConnection.DBConnection;
import org.me.dblp_parser.Article;
//Given author name A, list all of her co-authors.
//Note: A person B may have co-authored with A for multiple papers, but only count as one co-author.
//Given an exact paper name, list its publication details.
//Given an author name, list all of her publications and detailed publication information.
//Note: No need to consider authors with the same name.
//Given some keywords, list all publications that contain some or all of the keywords in the paper title.
//Given two author names, find out whether they ever co-author some papers and if yes, the details.
@WebService(endpointInterface = "org.me.Test")
public class Result {
	PreparedStatement stmt_authors, stmt_journals;
	Connection conn;
	private static String dbUrl = "jdbc:mysql://127.0.0.1:3306/dblp";
	private static String Driver = "com.mysql.jdbc.Driver";
	private static String user = "root";
	private static String password = "Since1992!";
	
	public Result() {
		super();
		try {
			   //Class.forName("org.gjt.mm.mysql.Driver");
				Class.forName(Driver).newInstance();
			   //org.gjt.mm.mysql.Driver
			 conn = DriverManager.getConnection(dbUrl, user, password);
			} catch (Exception e) {
				System.out.println("Error while opening a conneciton to database server: "
									+ e.getMessage());
				e.printStackTrace();
			}
	}
	@WebMethod(action = "operation")
	public String operation(@WebParam(name = "param_name") String param) {
		// implement the web service operation her
		return param;
	}
	@WebMethod(action = "findCoauthors")
	public String findCoauthors(@WebParam(name = "authorname") String authorname) {
		PreparedStatement stmt_authors, stmt_journals;
		
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
	@WebMethod(action = "findpaper")
	public String findpaper(@WebParam(name = "papertitle") String papertitle) {
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
	@WebMethod(action = "findpublications")
	public String findpublications(@WebParam(name = "authorname") String authorname) throws SQLException {
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

	@WebMethod(action = "keywordsfinding")
	public String keywordsfinding(@WebParam(name = "keywords") String keywords) {
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
	@WebMethod(action = "authorsfinding")
	public String authorsfinding(@WebParam(name = "authorname1,authorname2") String authorname1,String authorname2) {
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

package dblpServlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import javax.xml.xquery.XQException;

import org.me.lab3.xquery.*;
import org.me.lab3.xquery.xquery.selectTitle;
/**
 * Servlet implementation class dblp
 */
@WebService
public class dblpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String url;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dblpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  
		response.setContentType("text/html;charset=UTF-8");
//		
		
		  url= request.getRequestURI();
		  System.out.print(url);
		  
//		  FileOutputStream fos=new FileOutputStream("output.txt",true);
//		  System.out.print("------------");
//          fos.write(url.toString().getBytes());
		  if(url.contains("selectTitles")){
		    	 response.sendRedirect("titles.html");
		    	 url="http://localhost:8080/XQuery_Test/titles.html";
		     }else
		  if(url.contains("selectTitleFirstArc")){
			 
		    	 response.sendRedirect("firstTitle.html");
		    	 url="http://localhost:8080/XQuery_Test/firstTitle.html";
		     }else
		   
		
		     if(url.contains("selectAllAuthors")){
		    	 response.sendRedirect("authors.html");
		    	url=" http://localhost:8080/XQuery_Test/authors.html";
		    	
		     }else
		    
		     if(url.contains("selectArcByYear")){
		    	 response.sendRedirect("articles.html");
		    	url="http://localhost:8080/XQuery_Test/articles.html";
		     }else
		    
		     if(url.contains("selectTitleByYear")){
		    	 response.sendRedirect("selectTitle.html");
		    	url="http://localhost:8080/XQuery_Test/selectTitle.html";
		     }
		  
		
	
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

}

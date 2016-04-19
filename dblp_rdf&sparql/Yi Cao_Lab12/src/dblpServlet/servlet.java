package dblpServlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

import dblpSparql.dblpQueries;

/**
 * Servlet implementation class servlet
 */
public class servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String searchType=request.getParameter("type");
	    System.out.println(searchType);
	    response.setHeader("Content-Type", " text/plain;charset=UTF-8");
	    response.setHeader("success", "s");
	    String nameString=request.getParameter("name");
	    String year1=request.getParameter("year1");
	    String year2=request.getParameter("year2");
	    String titleString=request.getParameter("title");
	    String keyword=request.getParameter("keyword");
	    String string=null;
	    if(searchType.equals("byName")){
	       string= dblpQueries.queryByName(nameString);
	    }else if(searchType.equals("byRange")){
	    	System.out.println(nameString+";"+year1+";"+year2);
	    	string=dblpQueries.queryByTimeRange(nameString, year1, year2);
	    }
	    else if(searchType.equals("byCo-authors")){
	    	string=dblpQueries.queryCoAuthor(nameString);
	    }
	    else if(searchType.equals("byTitle")){
	    	string=dblpQueries.queryDetails(titleString);
	    }
	    else if(searchType.equals("byKeywords")){
			string=dblpQueries.queryByKeyword(keyword);
		}
	    System.out.println("result"+string);
		request.setAttribute("data", string);
		try {
			request.getRequestDispatcher("/result.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	
}

package org.me.restapi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("/SearchEngine")
public class searchEngineClient {
		   @GET
		   @Path("/result")
		   @Produces(MediaType.TEXT_HTML)
		   public String getWeatherReport(){
			   FileInputStream fiStream=null;
				BufferedReader bReader=null;
				InputStreamReader isr=null;
				StringBuilder sBuilder=new StringBuilder();
				try{
					fiStream=new FileInputStream("/Users/caoyi/Downloads/eclipse/Eclipse.app/Contents/MacOS/keyWordSearch.txt");
					isr=new InputStreamReader(fiStream);
					bReader=new BufferedReader(isr);
					String len;
				    while((len=bReader.readLine())!=null){
				    	sBuilder.append(len);
				    }
				}catch(Exception e){
					e.printStackTrace();
				}
				System.out.println(sBuilder.toString());
			return sBuilder.toString();
			   
		   }
    
}
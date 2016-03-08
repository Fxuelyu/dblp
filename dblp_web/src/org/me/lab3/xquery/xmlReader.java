package org.me.lab3.xquery;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public interface xmlReader {
	public static void writeFile (String xml,String filename){
	    BufferedWriter out=null;
	    
	    try { 
	    	 out = new BufferedWriter(new FileWriter(filename));
	    	 
	    		 out.write(xml);
	    	 
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
}

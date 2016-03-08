package org.me.lab3.xquery;
import javax.xml.transform.*;
import java.net.*;
import java.io.*;


public interface xmlTohtml{
	
	public static void transform(String xmlname, String htmlname, String xslname) {
	  try {

	    TransformerFactory tFactory = TransformerFactory.newInstance();

	    Transformer transformer =
	      tFactory.newTransformer
	         (new javax.xml.transform.stream.StreamSource
	            (xslname));

	    transformer.transform
	      (new javax.xml.transform.stream.StreamSource
	            (xmlname),
	       new javax.xml.transform.stream.StreamResult
	            ( new FileOutputStream(htmlname)));
	    }
	  catch (Exception e) {
	    e.printStackTrace( );
	    }
	  }
	
}

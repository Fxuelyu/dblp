package dblpTransformer;

import java.io.FileOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

public interface xmlToRdf{
	
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

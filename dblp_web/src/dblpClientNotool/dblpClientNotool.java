package dblpClientNotool;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.me.dblp.web.Dblp;


public class dblpClientNotool {
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://localhost:9090/ws/dblp?wsdl");
		//1st argument service URI, refer to wsdl document above //2nd argument is service name, refer to wsdl document above
		QName qname = new QName("http://web.dblp.me.org/", "dblpImpService");
		Service service = Service.create(url, qname); 
		Dblp  dd = service.getPort(Dblp.class); 
		//System.out.println();
		System.out.println(dd.operations("please input the main address in your browser:"+"\n"+"http://localhost:8080/XQuery_Test/index.html"));
		} 
}

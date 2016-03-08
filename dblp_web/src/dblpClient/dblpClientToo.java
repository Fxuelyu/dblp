package dblpClient;


import org.me.dblp.web.*;


public class dblpClientToo {
	public static void main(String[] args) {
		DblpImpService dblpService = new DblpImpService();
		Dblp hello = dblpService.getDblpImpPort();
		System.out.println(hello.operations("please input the main address in your browser:"+"\n"+"http://localhost:8080/XQuery_Test/index.html"));
	}
				
}

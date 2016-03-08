package org.me.dblp.web;

import javax.xml.ws.Endpoint;



public class dblppublish {
	public static void main(String[]args){
		 Endpoint.publish("http://localhost:9090/ws/dblp",new  dblpImp());
	 }
}

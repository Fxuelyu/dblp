package org.me.dblp.web;

import javax.jws.WebService;

import dblpServlet.dblpServlet;
@WebService(endpointInterface = "org.me.dblp.web.dblp")
public class dblpImp implements Dblp {

	@Override
	public String operations(String welcomeString) {
		return welcomeString;
	}

}

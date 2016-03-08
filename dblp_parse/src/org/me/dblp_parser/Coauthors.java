package org.me.dblp_parser;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Coauthors {
     public static  String findcoauthors(Article arcticle, Map<Integer,String> authorsmap){
    	 StringBuilder sb=new StringBuilder();
    	 for (Map.Entry<Integer, String> entry : authorsmap.entrySet()) {  
    		  Integer key= entry.getKey();
    		  String value=entry.getValue();
    		  if(arcticle.authors.contains(value)){
    			  sb.append(key+",");
    		  }
    		  
    		}  
    	
    	 return sb.toString();
    	 
     }
}

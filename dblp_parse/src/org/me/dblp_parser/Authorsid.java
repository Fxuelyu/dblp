package org.me.dblp_parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Authorsid {
	
	protected static Map<Integer,String> authorsmap=new HashMap<Integer,String>();
	private static ArrayList<String> authorsdel=new ArrayList<String>();
    public static void insert(ArrayList<Article> acts){
    	 int k=1;
     for(int i=0;i<acts.size();i++){
    	 for(int j=0;j<acts.get(i).authors.size();j++){
    		
    		String author=acts.get(i).authors.get(j);
    		 if(!(authorsdel.contains(author))){
    			 authorsdel.add(author);
    			 authorsmap.put(k, author);
    			 k++;
    		 }
    		
		  }
     }
    }
    
//    public static void getMap(){
//    	for (Map.Entry<Integer, String> entry : authorsmap.entrySet()) {  
//  		  Integer key= entry.getKey();
//  		  String value=entry.getValue();
//  		  System.out.println("key:"+key+";value:"+value);
//    }
//    }
    
    
  
    
}

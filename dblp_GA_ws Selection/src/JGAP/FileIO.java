package JGAP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * This class is used to read txt file
 * @author caoyi
 *
 */

public class FileIO {
	public static void readServices(String fileName, ArrayList<String> clusterNames,  HashMap<String, Cluster> wsMap){
		Double minCost=minCost(fileName);
		//System.out.println("here is the minCost "+minCost);
		try {
			BufferedReader brBufferedReader=new BufferedReader(new FileReader(fileName));
			brBufferedReader.readLine();
			String line="";
			while((line=brBufferedReader.readLine())!=null){
		    	String[] inputStrings=line.split(" ");
		    	String clusertName=inputStrings[0];
		    	if(!(wsMap.containsKey(clusertName))){
		    		wsMap.put(clusertName, new Cluster());
		    		clusterNames.add(clusertName);
		    	}
		    	
		    	String serviceName=inputStrings[1];
		    	//positively
		    	double reliability=(Double.valueOf(inputStrings[3])/100);
                double availability = (Double.valueOf(inputStrings[5]) / 100);
		    	//negatively
		    	double cost=(minCost/Double.valueOf(inputStrings[2]));
		    	double time=Double.valueOf(inputStrings[4]);
		    	 if (time <= 2)
	                    time = 0.9;
	             else if (time <= 5 && time > 2)
	                    time = 0.85;
	             else if (time <= 10 && time > 5)
	                    time = 0.7;
	             else if (time <= 20 && time > 10)
	                    time = 0.6;
	             else if (time > 20)
	                    time = 0.45;
		    	 Service newService=new Service(serviceName, cost, reliability, time, availability);
                 Cluster tmpCluster=wsMap.get(clusertName);
                 tmpCluster.addServiceToList(newService);
                 
			}
		    
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static  double minCost(String fileName){
	   double minCost=(double)Integer.MAX_VALUE;
		try {
			BufferedReader brBufferedReader=new BufferedReader(new FileReader(fileName));
			brBufferedReader.readLine();
		    String line="";
		    while((line=brBufferedReader.readLine())!=null){
		    	String[] inputStrings=line.split(" ");
		    	 double cost = Double.valueOf(inputStrings[2]);
	                if (cost<minCost) {
	                    minCost = cost;
	                }
		    }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return minCost;
		
	}
}

package JGAP;

import java.util.ArrayList;

/***
 * 
 * @author caoyi
 *This method is used to place clustered service
 */

public class Cluster {
     ArrayList<Service> serviceList=new ArrayList<Service>();
    public Cluster(){
    	
    }
	public Cluster(ArrayList<Service> serviceList) {
		super();
		this.serviceList = serviceList;
	}
     
	public Service getService(int index){
		return serviceList.get(index);
	}
	
	 public int numberOfServices(){
	        return serviceList.size();
	 }
	 
	 public void addServiceToList(Service service){
		 serviceList.add(service);
	 }
	 
}

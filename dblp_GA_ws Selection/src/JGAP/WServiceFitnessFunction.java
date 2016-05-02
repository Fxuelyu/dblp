package JGAP;

import java.util.ArrayList;
import java.util.HashMap;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class WServiceFitnessFunction extends FitnessFunction{
    private final double WEIGHTCOST = 0.35;
    private final double WEIGHTRELABILITY = 0.15;
    private final double WEIGHTPERFORMANCE = 0.15;
    private final double WEIGHTAVAILABILITY = 0.35;
    //place all services
    private HashMap<String, Cluster> wsMap=new HashMap<String, Cluster>();
    
   private ArrayList<String> clusterNames=new ArrayList<String>();

	public WServiceFitnessFunction(HashMap<String, Cluster> wsMap,
		ArrayList<String> clusterNames) {
	super();
	this.wsMap = wsMap;
	this.clusterNames = clusterNames;
}


	@Override
	protected double evaluate(IChromosome chromosome) {
		// TODO Auto-generated method stub
		
		ArrayList<Integer> indexArrayList=new ArrayList<Integer>();
		for(int i=0;i<chromosome.size();i++){
			int chosenIndex=(Integer)chromosome.getGene(i).getAllele();
            indexArrayList.add(chosenIndex);
		}
		ArrayList<Service> chosenServices=new ArrayList<Service>();
		for(int i=0;i<indexArrayList.size();i++){
			Service chosenService=wsMap.get(clusterNames.get(i)).getService(indexArrayList.get(i));
			chosenServices.add(chosenService);
		}
		
		double totalPerformance=0.0;
		double bestCost = 1.0;
	    double bestReliability = 1.0;
	    double bestTimePer = 1.0;
	    double bestAvailability = 1.0;
	        
	    for (int i=0;i<indexArrayList.size();i++){
	            bestCost *= chosenServices.get(i).cost;
	            bestReliability *= chosenServices.get(i).reliability;
	            bestTimePer *= chosenServices.get(i).time;
	            bestAvailability *= chosenServices.get(i).availability;
	        }
	        
	     totalPerformance = WEIGHTCOST*bestCost+WEIGHTRELABILITY*bestReliability+WEIGHTPERFORMANCE*
	                bestTimePer+WEIGHTAVAILABILITY*bestAvailability;
	        
	     return totalPerformance;
	}
	
    
}

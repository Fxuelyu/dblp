package JGAP;

import java.util.ArrayList;
import java.util.HashMap;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class Driver {
    private static final double WEIGHTCOST = 0.35;
    private static final double WEIGHTRELABILITY = 0.15;
    private static final double WEIGHTPERFORMANCE = 0.15;
    private static final double WEIGHTAVAILABILITY = 0.35;
    private static final int MAX_ALLOWED_EVOLUTIONS = 500;

    public static HashMap<String, Cluster> wsMap = new HashMap<>();
    public static ArrayList<String> clusterNames = new ArrayList<>();

	
	public static void main(String[]args) throws InvalidConfigurationException{
		
		String fileName="/Users/caoyi/Documents/workspace-lunaee/Yi_Cao_lab15/src/JGAP/input.txt";
		FileIO.readServices(fileName, clusterNames, wsMap);
        Configuration configuration = new DefaultConfiguration();
		
		FitnessFunction function=new WServiceFitnessFunction(wsMap, clusterNames);
	    configuration.setFitnessFunction(function);
	    
	    Gene[] sampleGenes=new Gene[wsMap.size()];
	    for(int i=0;i<wsMap.size();i++){
	    	sampleGenes[i]=new IntegerGene(configuration, 0, wsMap.get(clusterNames.get(i)).numberOfServices() - 1);
	    	System.out.println("Cluster "+clusterNames.get(i)+" has "+wsMap.get(clusterNames.get(i)).numberOfServices()+" services");
	    	
	    }
	    
	    Chromosome sampleChromosome = new Chromosome(configuration, sampleGenes);
        configuration.setSampleChromosome(sampleChromosome);
        configuration.setPopulationSize(500);

        Genotype population = Genotype.randomInitialGenotype(configuration);

        for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
            population.evolve();
        }

        IChromosome bestSolutionSoFar = population.getFittestChromosome();

        System.out.println("Optimal combinations of services are: ");
        ArrayList<Integer> chosenIndex = new ArrayList<>();
        for (int i = 0; i < wsMap.size(); i++) {
            int index = (int) bestSolutionSoFar.getGene(i).getAllele();
            chosenIndex.add(index);
            System.out.println(clusterNames.get(i) + " : " + wsMap.get(clusterNames.get(i)).getService(index).serviceName);
        }
        System.out.println("Best performance is " + bestPerformance(chosenIndex));
	}
	
	
    public static double bestPerformance(ArrayList<Integer> index) {
        ArrayList<Service> chosenServiceList = new ArrayList<>();
        for (int i = 0; i < index.size(); i++) {
            Service serviceChosen = wsMap.get(clusterNames.get(i)).getService(index.get(i));
            chosenServiceList.add(serviceChosen);
        }

        double bestPerformance = 0.0;

        double bestCost = 1.0;
        double bestReliability = 1.0;
        double bestTimePer = 1.0;
        double bestAvailability = 1.0;
        
        for (int i = 0; i < index.size(); i++) {
            bestCost *= chosenServiceList.get(i).cost;
            bestReliability *= chosenServiceList.get(i).reliability;
            bestTimePer *= chosenServiceList.get(i).time;
            bestAvailability *= chosenServiceList.get(i).availability;
        }

        bestPerformance = WEIGHTCOST * bestCost + WEIGHTRELABILITY * bestReliability + WEIGHTPERFORMANCE * bestTimePer
                + WEIGHTAVAILABILITY * bestAvailability;
        return bestPerformance;
    }
}

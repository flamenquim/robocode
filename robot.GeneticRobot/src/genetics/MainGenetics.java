package genetics;

import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;

import java.io.PrintWriter;

import org.jgap.Chromosome;

public class MainGenetics {

	public static void main(String[] args) throws Exception{
		
		System.setProperty("NOSECURITY", "true");
		
		
		Configuration conf = new DefaultConfiguration();
		FitnessFunction myFunc = new RobocodeFitnessFunction();
		conf.setFitnessFunction(myFunc);
		
		Gene genes[] = new Gene[4];
		
		genes[0] = new DoubleGene(conf, 120, 200);
		genes[1] = new DoubleGene(conf, 0.1, 0.5);
		genes[2] = new DoubleGene(conf, 6, 18);
		genes[3] = new DoubleGene(conf, 3, 8);
		
		Chromosome chromosome = new Chromosome(conf, genes);
		
		conf.setSampleChromosome(chromosome);
		
		conf.setPopulationSize(3);
		
		Genotype population = Genotype.randomInitialGenotype(conf);
		
		population.evolve();
		
		IChromosome bestSolutionSoFar = population.getFittestChromosome();
		
		int numEvolutions = 3;
		for( int i = 0; i < numEvolutions; i++ )
		{
		    population.evolve();
		}

		bestSolutionSoFar = population.getFittestChromosome();
		
		System.out.println( "The best solution contained the following: " );

		System.out.println(
		    RobocodeFitnessFunction.getValueAtGene(
		        bestSolutionSoFar, 0 ) + " distanceLimit." );

		System.out.println(
				RobocodeFitnessFunction.getValueAtGene(
		        bestSolutionSoFar, 1 ) + " probabilityChangeSpeed." );

		System.out.println(
				RobocodeFitnessFunction.getValueAtGene(
		        bestSolutionSoFar, 2 ) + " rangeRobotSpeeds." );

		System.out.println(
				RobocodeFitnessFunction.getValueAtGene(
		        bestSolutionSoFar, 3 ) + " minRobotSpeed." );
		
		System.exit(0);
	}
}
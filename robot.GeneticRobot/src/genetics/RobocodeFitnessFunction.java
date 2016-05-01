package genetics;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import robocode.control.RobocodeEngine;

public class RobocodeFitnessFunction extends FitnessFunction {

	private class ChromosomeWriter {
		public ChromosomeWriter(IChromosome chromosome) throws FileNotFoundException{
			PrintWriter pw = new PrintWriter("chromosome.txt");
			double clone;
			for(int i = 0; i < 4; i++){
				clone = (double) chromosome.getGene(i).getAllele();
				System.out.print(clone+" ");
				pw.print(chromosome.getGene(i).getAllele() + " ");
			}
			pw.close();
		}
	}
	
	private class FitnessReader{
		double fitness;
		
		public FitnessReader(){
			Scanner sc;
			try {
				sc = new Scanner(new java.io.File("fitness.txt"));
				fitness = Double.parseDouble(sc.next());
				sc.close();
			} catch (FileNotFoundException e) {
				System.out.println("Fitness reader error");
				e.printStackTrace();
			}
		}
		double getFitness(){ return fitness; }
	}
	
	protected double evaluate(IChromosome chromosome) {
		double fitness = 1;
		
		//first we need to do is to introduce the chromosome into the robot writing into a file
		try {
			ChromosomeWriter cw = new ChromosomeWriter(chromosome);
		} catch (FileNotFoundException e) {
			System.out.println("chromosome.txt is not found");
			e.printStackTrace();
		}
		
		//Then to generate the battle with specified chromosome several times in order to get better average of the fitness.
		//When the battle starts the robot reads written chromosome and fights with given parameters
		double fitnessAccumulator = 0;
		int rounds = 1;
		int numBattles = 3; //controls the number of battles being generated
		for(; rounds <= numBattles; rounds++){
			BattleFieldGenerator bf = new BattleFieldGenerator();
			FitnessReader fr = new FitnessReader();
			fitnessAccumulator += fr.getFitness();
		}

		fitness = fitnessAccumulator/rounds;
		System.out.println(fitness);
		return fitness;
	}
	
	public static double getValueAtGene(IChromosome chromosome, int i){
		return (double) chromosome.getGene(i).getAllele();
	}

}

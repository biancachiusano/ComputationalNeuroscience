import java.util.ArrayList;
import java.util.Random;



/**
* Some very basic stuff to get you started. It shows basically how each
* chromosome is built.
*
* @author Jo Stevens
* @version 1.0, 14 Nov 2008
*
* @author Alard Roebroeck
* @version 1.1, 12 Dec 2012
*
*/

public class prac2 {




	static final String TARGET = "HELLO WORLD";
	static char[] alphabet = new char[27];
	static double RATE_OF_MUTATION = 0.2;
	static double RATE_OF_CROSSOVER = 1;
	static int MAX_NUMBER_OF_MUTATIONS = 1;
	static int initialPopulation = 500;
	static int tournamentNumber = 2;
	static int NUMBER_TO_CARRY_OVER = (int) 0.2 * initialPopulation;

	
	static int maxNumGenerations = 100000;



	public static Individual[] init(){
		int popSize = initialPopulation;
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
		//adding it at the end , alfabet has 26 letters
		alphabet[26] = ' ';
		Random generator = new Random(System.currentTimeMillis());
		Individual[]  population = new Individual[popSize];
		// we initialize the population with random characters
		for (int i = 0; i < popSize; i++) {
			//creating a char array that is the length of the HELLO WORLD with 2 random words
			char[] tempChromosome = new char[TARGET.length()];
			for (int j = 0; j < TARGET.length(); j++) {
				tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)]; //choose a random letter in the alphabet
			}
			//storing thw words in an array.
			population[i] = new Individual(tempChromosome);
		}
		setFitnessOfPopulation(population);
		HeapSort.sort(population);

		return population;
	}



	/**
	* @param args
	*/
	public static void main(String[] args) {
		Individual[] population = init();
		int generationCount = 0;
		for (int i = 0; i < population.length; i++) {
			System.out.print(population[i].genoToPhenotype() + " ");
			//call method to set the fitnessScore for individuals
			System.out.println("Fitness: " + population[i].getFitness());
		}

		while(!checkIfPopulationIsReady(population) && generationCount < maxNumGenerations){
			population = evolve(population);
			if(generationCount % 10 == 0){
				System.out.println("Generation " + generationCount + "  ---  " + population[0].toString());
			}
			generationCount += 1;
		}




		//print results
		for (int i = 0; i < population.length; i++) {
			System.out.print(population[i].genoToPhenotype() + " ");
			//call method to set the fitnessScore for individuals
			System.out.println("Fitness: " + population[i].getFitness());
		}

		if(checkIfPopulationIsReady(population)){
			System.out.println("Found");
		} else {
			System.out.println("Not Found");
		}
		System.out.println("Genetions: " + generationCount);

	}




	public static Individual[] evolve(Individual[] population){
		Individual[] nextGen = new Individual[population.length];
		Individual[] parents;
		Individual[] kids;
		for(int i = 0; i < NUMBER_TO_CARRY_OVER; i ++){
			nextGen[i] = population[i];
		}
		for(int i = NUMBER_TO_CARRY_OVER ; i < nextGen.length; i += 2){
			// Choose 2 parents
			parents = chooseParents(population); 

			if (Math.random() < RATE_OF_CROSSOVER){
				// Create 2 kids
				kids = newIndividuals(parents[0], parents[1]);
				if(Math.random() < RATE_OF_MUTATION){
					mutate(kids[0]);
					mutate(kids[1]);
				}
				fitnessIndividual(kids[0]);
				fitnessIndividual(kids[1]);

				nextGen[i] = kids[0];
				nextGen[i+1] = kids[1];
			} else {
				nextGen[i] = parents[0];
				nextGen[i+1] = parents[1];

			}
			
		}

		HeapSort.sort(nextGen);
		return nextGen;
	}


	//set fitness score into individuals from population
	public static void setFitnessOfPopulation (Individual[] population){
		double fitnessScore;
		//access to the Individual from the population array and set its fitnessScore
		for (int i=0; i<population.length; i++){
			fitnessIndividual (population[i]);
		}
	}


	public static void fitnessIndividual (Individual individual){
		double fitnessScore = 0;
		//check if contains letter
		for (int i=0; i<TARGET.length(); i++){
			// check for letter in right possition, give 1/11 points
			if (individual.getChromosome()[i] == TARGET.charAt(i)){
				fitnessScore += 1;
			}
		}
		fitnessScore = fitnessScore / 11.0;
		individual.setFitness(fitnessScore);
	}

	public static Individual[] chooseParents(Individual[] population){
		Individual parent1 = tournamentSelection(population);
		Individual parent2 = tournamentSelection(population);
		while(parent1.genoToPhenotype().equals(parent2.genoToPhenotype())){
			parent2 = tournamentSelection(population);
		}

		return new Individual[] {parent1, parent2};
	}


	//REPRODUCE METHOD, returns 2 individuals from 2 parents
	public static Individual[] newIndividuals(Individual parent1, Individual parent2){
		Individual newIndividual1, newIndividual2;

		double positionCrossOver = (Math.random() *10)+1; 		
		int startCrossOverAt = (int) positionCrossOver;

		//considering single point cross-over
		//newindividual= clone parent1 with  replacement of  the rest of letters with the ones from parent2.
		newIndividual1 = parent1.clone();
		newIndividual2 = parent2.clone();
		//chromosomes of newIndividual
		for (int i=startCrossOverAt; i<newIndividual1.getChromosome().length; i++){
			newIndividual1.getChromosome()[i] = parent2.getChromosome()[i];
			newIndividual2.getChromosome()[i] = parent1.getChromosome()[i];
		}

		return new Individual[] {newIndividual1,newIndividual2};
	}

	public static Individual tournamentSelection(Individual[] population){
		int position = (int) ( Math.random() * population.length);
		Individual best = population[position];
		
		for (int i = 0; i < tournamentNumber; i++){
			position = (int) (Math.random() * population.length);
			if(best.getFitness() < population[position].getFitness()){
				best = population[position];
			}
		}
		return best;
	}

	////////MUTATION METHOD:
	public static void mutate (Individual newIndividual1){

		int numberOfMutations = (int) Math.random() * MAX_NUMBER_OF_MUTATIONS;
		double mutation1;

		for(int i = 0; i < MAX_NUMBER_OF_MUTATIONS; i ++){

			mutation1 = Math.random();
			//individual1
			int alphabetPosition1 = (int) (mutation1*27);
			int positionIndividual1 = (int) (mutation1*11);
			//get letter from alphabet
			newIndividual1.getChromosome()[positionIndividual1] = alphabet[alphabetPosition1];
		}
	}

	public static boolean checkIfPopulationIsReady (Individual[] population){
		if (population[0].genoToPhenotype().equalsIgnoreCase(TARGET)){
			return true;
		} else {
			return false;
		}
	}

}
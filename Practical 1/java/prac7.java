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

public class prac7 {



	// SET UP
	String TARGET;
	double RATE_OF_MUTATION;
	int initialPopulation;
	int ELITE_CHOICE;
	int maxNumGenerations;
	char[] alphabet = new char[27];

	// RUNNING EVOLUTION
	Individual[] population;
	int generationCount;
	boolean found;



	/**
	* @param args
	*/
	public static void main(String[] args) {


	}


	public prac7(String target, int population, double mutationRate
				, double eliteChoice, int maxNumGenerations){
		this.TARGET=target;
		this.initialPopulation=population;
		this.RATE_OF_MUTATION = mutationRate;
		this.ELITE_CHOICE = (int) ( eliteChoice * population);
		this.maxNumGenerations = maxNumGenerations;
		this.alphabet = new char[27];

		init();
	}



	private void init(){
		int popSize = initialPopulation;
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
		//adding it at the end , alfabet has 26 letters
		alphabet[26] = ' ';
		Random generator = new Random(System.currentTimeMillis());
		this.population = new Individual[popSize];
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
		setFitnessOfPopulation();
		HeapSort.sort(population);

	}


	public int runGA(){
		while(!checkIfPopulationIsReady() && generationCount < maxNumGenerations){
			evolve();

			if(generationCount % 10 == 0){
				//System.out.println("Generation " + generationCount + "  ---  " + population[0].toString());
			}
			generationCount += 1;
		}

		if(checkIfPopulationIsReady()){
			found = true;
		} else {
			found=false;
		}

		return generationCount;
	}


	public void printPopulation(){
		for(int i = 0; i<population.length;i++){
			System.out.println(population[i]);
		}
	}


	private void evolve(){
		Individual[] nextGen = new Individual[population.length];
		int pos = 0;
		Individual[] parents;
		Individual[] kids;

		for(int i = 0; i < ELITE_CHOICE; i++){
			for(int j = 0; j < ELITE_CHOICE; j++){
				if(i!=j && pos<population.length-1){
					kids = newIndividuals(population[i], population[j]);
					nextGen[pos] = kids[0];
					pos++;
					nextGen[pos] = kids[1];
					pos++;
				}

			}
		}

		for(int i = 0;i < nextGen.length; i++){
			mutate(nextGen[i]);
		}

		population = nextGen;
		setFitnessOfPopulation();
		HeapSort.sort(population);



	}


	//set fitness score into individuals from population
	private void setFitnessOfPopulation (){
		double fitnessScore;
		//access to the Individual from the population array and set its fitnessScore
		for (int i=0; i<population.length; i++){
			fitnessIndividual (population[i]);
		}
	}


	private void fitnessIndividual (Individual individual){
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


	//REPRODUCE METHOD, returns 2 individuals from 2 parents
	private static Individual[] newIndividuals(Individual parent1, Individual parent2){
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

	////////MUTATION METHOD:
	private void mutate (Individual individual){

		char[] chromIndiv = individual.getChromosome();
		int counter = 0;

		for(int i = 0; i < chromIndiv.length; i ++){
			//Does this directly change the individual itself? doesn't it change the char array?
			if(Math.random() < RATE_OF_MUTATION){
				chromIndiv[i] = alphabet[(int) (Math.random() * 27)];
				counter++;
				//What is the counter for?
			}

		}
	}

	private boolean checkIfPopulationIsReady (){
		if (population[0].genoToPhenotype().equalsIgnoreCase(TARGET)){
			return true;
		} else {
			return false;
		}
	}

}

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

public class Practical2 {

	static final String TARGET = "HELLO WORLD";
	static char[] alphabet = new char[27];
	static double probOfMutation = 0.1;
	static double probOfInversion = 0.01;
	static int initialPopulation = 100;

	
	static int maxNumGenerations = 100000;

	static int rangeOfFirstParent = 10;
	static int rangeOfSecondParent = 40;

	/**
	* @param args
	*/
	public static void main(String[] args) {
		int popSize = initialPopulation;
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
		//adding it at the end , alfabet has 26 letters
		alphabet[26] = ' ';
		Random generator = new Random(System.currentTimeMillis());
		Individual[] population = new Individual[popSize];
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

		// What does your population look like?
		/**for (int i = 0; i < population.length; i++) {
		System.out.print(population[i].genoToPhenotype() + " ");
		System.out.println("Fitness: " + population[i].getFitness());
	}*/

	//call method to set the fitnessScore for individuals
	setFitnessOfPopulation(population);
	//sort population based on fitnessScore
	HeapSort.sort(population);

	for (int i = 0; i < population.length; i++) {
		System.out.print(population[i].genoToPhenotype() + " ");
		//call method to set the fitnessScore for individuals
		System.out.println("Fitness: " + population[i].getFitness());
	}

	System.out.println("Number of generations needed: " + newGeneration(population)+ ".....................................................");
	for (int i = 0; i < population.length; i++) {
		System.out.print(population[i].genoToPhenotype() + " ");
		//call method to set the fitnessScore for individuals
		System.out.println("Fitness: " + population[i].getFitness());
	}

	// do your own cool GA here
	/**
	* Some general programming remarks and hints:
	->> * - A crucial point is to set each individual's fitness (by the setFitness() method) before sorting. When is an individual fit?
	->> * 	How do you encode that into a double (between 0 and 1)?
	* - Decide when to stop, that is: when the algorithm has converged. And make sure you  terminate your loop when it does.
	* - print the whole population after convergence and print the number of generations it took to converge.
	* - print lots of output (especially if things go wrong).
	* - work in an orderly and structured fashion (use tabs, use methods,..)
	* - DONT'T make everything private. This will only complicate things. Keep variables local if possible
	* - A common error are mistakes against pass-by-reference (this means that you pass the
	* 	address of an object, not a copy of the object to the method). There is a deepclone method included in the
	*  Individual class.Use it!
	* - You can compare your chromosome and your target string, using for eg. TARGET.charAt(i) == ...
	* - Check your integers and doubles (eg. don't use ints for double divisions).
	*/
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//set fitness score into individuals from population
public static void setFitnessOfPopulation (Individual[] population){
	double fitnessScore;
	for (int i=0; i<population.length; i++){
		fitnessScore =  fitnessIndividual (population[i].getChromosome());
		//access to the Individual from the population array and set its fitnessScore
		population[i].setFitness(fitnessScore);
	}
}


//if you have right letter= 1/15 points (H,E,L x3 , O x2 , W ,R, D  )
//if you have the right letter in right possition = 1/11 points
public static double fitnessIndividual (char[] individual){
	char letterFromIndividual;
	char letterFromTarget;
	double fitnessScore=0;

	ArrayList<Character> toCheckTarget= new ArrayList<Character>();
	ArrayList<Character> toCheckIndividual = new ArrayList<Character>();
	//copy values of Target  into toCheck
	for(int i=0; i<individual.length;i++){
		toCheckTarget.add(TARGET.charAt(i));
		toCheckIndividual.add(individual[i]);
	}


	//check if contains letter
	for (int i=0; i<toCheckTarget.size(); i++)
	{

		// check for letter in right possition, give 1/11 points
		if (toCheckIndividual.get(i)==toCheckTarget.get(i))
		{
			fitnessScore += 1;
			//change letter of toCheck with symbol, because if I have the same letter in the future i Dont want to give it a score.
			toCheckTarget.set(i, '&');
			toCheckIndividual.set(i, '%');
		}
	}

	fitnessScore = fitnessScore / 11.0;

	//Nested Loop
	/*for(int a = 0; a < toCheckTarget.size(); a++)
	{
		for(int b = 0; b < toCheckIndividual.size(); b++)
		{
			if(toCheckTarget.get(a) == toCheckIndividual.get(b))
			{
				fitnessScore += (1.0/16);
				toCheckIndividual.set(b, '%');
				break;
			}
		}
	}
	*/



	return fitnessScore;

		/*

	int editCounter = 0;
	//check if contains letter
	for (int i=0; i<toCheckTarget.size(); i++)
	{
		// check for letter in right possition, give 1/11 points
		if (toCheckIndividual.get(i)!=toCheckTarget.get(i))
		{
			editCounter++;
		}
	}

	fitnessScore = 1 - (editCounter/11.0);
	return fitnessScore;
	*/
}

public static boolean isLetterInArray (char letterToCheck, ArrayList <Character> toCheck){
	for (int i=0; i<toCheck.size(); i++){
		if (toCheck.get(i) == letterToCheck){
			return true;
		}
	}
	return false;
}


public static boolean isNumberInArray (int numberToCheck, int [] array){
	for (int i=0; i<array.length; i++){
		if (array[i] == numberToCheck){
			return true;
		}
	}
	return false;
}

public static boolean isIndividualInPop (Individual indiToCheck, Individual [] array){
	for (int i=0; i<array.length; i++){
		if (array[i].genoToPhenotype().equals(indiToCheck.genoToPhenotype())){
			return true;
		}
	}
	return false;
}

//ELITIST SELECTION of parents because we want the words that are more similar to HELLO WORLD-> so the parents with higher fitness score
public static int newGeneration (Individual[] population){
	int countNewGenerations=0;
//	Individual[] population2 = new Individual[population.length];

	while (!checkIfPopulationIsReady (population) && countNewGenerations<maxNumGenerations){

		int firstParentPos = (int) (Math.random() * rangeOfFirstParent )+1;
		int secondParentPos = (int) (Math.random() * rangeOfSecondParent )+1;



			//creating 2 individuals and storing them in an array of 2 individuals
			Individual[] newKids= newIndividuals(population[firstParentPos],population[secondParentPos]);


			// mutate

				//INVERSION:
			double inversion= Math.random();
			//do inversion with 50% chance for both invididuals
			if (inversion<probOfInversion && false){
				swapMutation(newKids[0]);

				swapMutation(newKids[1]);
			}

			double swap = Math.random();
			if (swap < probOfMutation){
				inversion (newKids[0]);
				inversion (newKids[1]);
			}

			//MUTATION:
			//generate random number, if number<0.5 -> mutation in both individuals
			double mutation = Math.random();
			if (mutation < probOfMutation){

				mutation(newKids[0],newKids[1]);


			}





			// set fitness of new kids
			double fitnessNewIndividual1= fitnessIndividual(newKids[0].getChromosome());
			newKids[0].setFitness(fitnessNewIndividual1);
			double fitnessNewIndividual2= fitnessIndividual(newKids[1].getChromosome());
			newKids[1].setFitness(fitnessNewIndividual2);


			//if the fitness of the newIndividual1> to the one that has the lowest, then replace new one for old.
			//if (fitnessNewIndividual1 > population[population.length-1].getFitness()){
			//if(!isIndividualInPop(newKids[0], population)){
				population[population.length-1] = newKids[0];
				HeapSort.sort(population);
			//}
			//}


			//if(!isIndividualInPop(newKids[1], population)){
				population[population.length-1] = newKids[1];
			//}
				HeapSort.sort(population);
			//}
			




	 	countNewGenerations++;
	 	if(countNewGenerations > 500){
	 		probOfMutation = 0.8;
	 	}
		}

	return countNewGenerations;
}



///////////////////////////////////////// 2 parents can have 2 kids:

//REPRODUCE METHOD, returns 2 individuals from 2 parents
public static Individual[] newIndividuals (Individual parent1, Individual parent2){
	//declaring a new individual with the size of the parents
	Individual newIndividual1, newIndividual2;

	double positionCrossOver = (Math.random() *9)+1; 		//I am writting a range from 1  to 10 because I always want it to take something from both parents.
	int startCrossOverAt = (int) positionCrossOver;
	//i think we should implement both types of cross-over to see what is more efficient:

	//considering single point cross-over
	//newindividual= clone parent1 with  replacement of  the rest of letters with the ones from parent2.
	newIndividual1= parent1.clone();
	newIndividual2= parent2.clone();
	//chromosomes of newIndividual
	for (int i=startCrossOverAt; i<(newIndividual1.getChromosome()).length; i++){
		newIndividual1.getChromosome()[i] = parent2.getChromosome()[i];
		newIndividual2.getChromosome()[i] = parent1.getChromosome()[i];
	}
	/**	System.out.println ("POSITION CROSSOVER"+ startCrossOverAt);
	System.out.println("parent1  _______________________________________"+ parent1.genoToPhenotype() );
	System.out.println("newIndividual1   _______________________________________"+ newIndividual1.genoToPhenotype() );
	System.out.println("parent2  _______________________________________"+ parent2.genoToPhenotype() );
	System.out.println("newIndividual2   _______________________________________"+ newIndividual2.genoToPhenotype() );
	*/
	return new Individual[] {newIndividual1,newIndividual2};
	


}


public static void swapMutation(Individual indi){
	int pos1 = (int) (Math.random()* 10)  + 1;
	int pos2 = (int) (Math.random()* 10)  + 1;
	char holding = indi.getChromosome()[pos1];
	indi.getChromosome()[pos1] = indi.getChromosome()[pos2];
	indi.getChromosome()[pos2] = holding;
}


//////////////////////INVERSION METHOD:
public static void inversion (Individual newIndividual){

	double inversionPoint1 = Math.random()*9 + 1;		//inversion points need to be between 1 and 10
	double inversionPoint2 = Math.random()*9 + 1;

	//clone of individual
	Individual copyOfIndividual = newIndividual.clone();

	//cast values
	int point1 = (int) inversionPoint1;
	int point2 = (int) inversionPoint2;

	//in case both inversion points are the same, do it again
	while (point1==point2){
		inversionPoint2 = Math.random()*9 + 1;
		point2 = (int) inversionPoint2;
	}

	//check whichh inversion point is higher
	int startInversionAt;
	int finishInversionAt;
	if (point1>point2){
		startInversionAt= point2;
		finishInversionAt= point1;
	} else {
		startInversionAt= point1;
		finishInversionAt= point2;
	}

	int position;

	position= finishInversionAt-1;
	for (int i=startInversionAt; i<finishInversionAt; i++){
			newIndividual.getChromosome()[i] = copyOfIndividual.getChromosome()[position];
			position--;
		}

}



////////MUTATION METHOD:
public static void mutation (Individual newIndividual1, Individual newIndividual2){

	double mutation1 = Math.random();
	double mutation2 = Math.random();
	//individual1
	int alphabetPosition1 = (int) (mutation1*27);
	int positionIndividual1 = (int) (mutation1*11);
	//get letter from alphabet
	newIndividual1.getChromosome()[positionIndividual1] = alphabet[alphabetPosition1];
	//individual2
	int alphabetPosition2 = (int) (mutation2*27);
	int positionIndividual2 = (int) (mutation2*11);
	//get letter from alphabet
	newIndividual2.getChromosome()[positionIndividual2]= alphabet[alphabetPosition2];

}


//METHOD that checks phenotype of  individual from population == target .
public static boolean checkIfPopulationIsReady (Individual[] population){
	for (int i=0; i<population.length;i++){
		if (population[i].genoToPhenotype().equalsIgnoreCase(TARGET)){
			return true;
		}
	}
	return false;
}
}

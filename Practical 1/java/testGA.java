public class testGA {


	public static void main(String[] args) {
		String target = "HELLO WORLD";
		int population = 100;
		double mutantRate = 0.03;
		double elitism = 0.1;
		int maxGenerations = 1000;
//Line 11 and 14 are not clear - the actual testing occurs in getAv
		prac7 test = new prac7(target, population, mutantRate, elitism, maxGenerations );
		//RunGA returns an integer, but this is not stored anywhere here
		//The integer is actually stored later (below) when method is called again
		test.runGA();

		for(int i = 0 ; i < 5; i++){
			//This is testing 5 times - the GA
			getAv(target, population, mutantRate, elitism, maxGenerations);
			mutantRate += 0.05;
		}
	}


	public static void getAv(String target, int population, double mutantRate, double elitism, int maxGenerations){

		//Why run 20? - to find the average generations to solve the GA
		int runs = 20;
		double total = 0.0;
		for(int i = 0; i < runs; i++){
			/**
			 * running 20 times
			 * creating an object of type prac7 passing variables
			 */
			prac7 test = new prac7(target, population, mutantRate, elitism, maxGenerations );
			int generations = test.runGA();
			total += generations;
			System.out.println(generations + ": " + i);
		}
//Average
		double average = total / runs;


//Printing out
		System.out.println("Target: " + target);
		System.out.println("Population: " + population);
		System.out.println("Rate of mutation: " + mutantRate);
		System.out.println("Elitism rate: " + elitism);
		System.out.println("Max number of generations: " + maxGenerations);
		System.out.println("AVE generations to solve: " + average );

	}

}

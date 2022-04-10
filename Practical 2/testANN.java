import java.util.Arrays;
public class testANN {
	public static void main(String[] args) {

		//Exercise 1
		System.out.println("Exercise 1: ");
		//final int[][] AND_INPUT_NODES = {{0,0,1,0},{0,1,1,0},{1,0,1,0},{1,1,1,1}};
		final int[][] AND_INPUT_NODES = {{1,0,0,0},{1,0,1,0},{1,1,0,0},{1,1,1,1}};
		ANN assignment = new ANN(AND_INPUT_NODES, false);
		int numberOfRuns = assignment.run();
		System.out.println("Number of Runs: " + numberOfRuns);

		//Exercise 2
		System.out.println();
		System.out.println("Exercise 2: ");
		//Give training data to the peceptron: points from the graph (f(x)=2x+1) that we know the classification.
		//If they are above the line result=1, if they are bellow=0
		final int[][] FUNCTION_INPUT_NODES = {{1,4,1,1}, {4,7,1,0},{5,12,1,1},{3,8,1,1},{5,13,1,1},{10,20,1,0}, {3,6,1,0}, {6,11,1,0}, {4,6,1,0}, {5,13,1,1}};

		int numberTargetValues= 10;
		int startRange= 1;
		int finishRange= 1000;
		//int [][] FUNCTION_INPUT_NODES= createData(numberTargetValues,startRange,finishRange);
		for (int i=0; i<FUNCTION_INPUT_NODES.length; i++){
			int x= FUNCTION_INPUT_NODES[i][0];
			int y= FUNCTION_INPUT_NODES[i][1];
			FUNCTION_INPUT_NODES[i][3] = validateFunction(x,y);
		}

		System.out.println("Target Values: " + Arrays.deepToString(FUNCTION_INPUT_NODES));
		ANN assignment2 = new ANN(FUNCTION_INPUT_NODES,true);
		int numberOfRuns2 = assignment2.run();
		System.out.println("Number of Runs: " +  numberOfRuns2);

		/**Testing accuracy of prediction: generate randomly a testing set
		*/
		int numberTargetValues2= 20;	//number of points
		int startRange2= 1;						//from which value to give to x and y
		int finishRange2= 10;			//up to which value
		int totalCorrectResults= 0;
		double totalNumberOfTrials= 0.0;
		double accuracy;

		int [][] testingSet= {{10,20,1,0}, {10,23,1,1}, {10,22,1,1}, {2,2,1,0}, {2,6,1,1},{1,4,1,1}, {4,7,1,0}, {3,6,1,0}, {4,10,1,1},{6,14,1,1}, {7,14,1,0}, {8,17,1,1}, {8,18,1,1}, {10,20,1,0}, {12,26,1,1}};
		/** To generate the testing set randomly:
		int [][] testingSet= createData(numberTargetValues2,startRange2,finishRange2);
		*/
		System.out.println("Test Data Set: " + Arrays.deepToString(testingSet));

		for (int i=0; i< testingSet.length; i++ ){
			int x= testingSet[i][0];
			int y= testingSet[i][1];
			if (assignment2.prediction(x,y) == validateFunction(x,y)){
				totalCorrectResults ++;
			}
			totalNumberOfTrials++;
		}
		accuracy= totalCorrectResults/ totalNumberOfTrials;
		System.out.println("Accuracy of prediction: " + accuracy);

	}

/**Method creates a data set
@param numberTargetValues: how many points I want to generate
@param startRange: from which value I want the points to start.
@param finishRange: up to which value should points go.
@return createData: array with new dataSet .
*/
	public static int[][] createData (int numberTargetValues, int startRange, int finishRange ){
		int[][] functionInputNodes = new int[numberTargetValues][4];
		for (int i=0; i<functionInputNodes.length; i++){
			for (int j=0; j<3; j++ ){
				int randomNumber = (int)(Math.random()*finishRange + startRange);
				//multiplying it by 2 to give higher chance to y values, for distribution of graph.
				int randomNumber2= randomNumber * 2;
				if (j==2){
					functionInputNodes[i][j]= 1; //bias
				}
				else if (j==1) {
				 functionInputNodes[i][j]= randomNumber2;
				}
				else {
				functionInputNodes[i][j]= randomNumber;
				}
			}
		}
		return functionInputNodes;
	}

/**Method validate Function: it checks for the correct classification of 2 points (x and y) in the graph f(x)= 2x+1
@param x
@param y
@return 1 if point is above the line, else 0.
*/
	public static int validateFunction(int x, int y){
			//true - points are above the function
			//false - points are below the function
			int f = (2 * x) + 1;
			if(y > f) { return 1;}
			else {return 0; }
	}

}

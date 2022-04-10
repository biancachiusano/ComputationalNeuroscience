import java.util.Arrays;
public class ANN {
	final int THRESHOLD = 0;
	int[][] INPUT_NODES;
	double [] weights;
	final double LEARNING_CONSTANT = 0.1;
	int sumError;
	int someValue;

	/**Constructor: initializes the input nodes.
	@param ArrayPassed: of input
	@param randomWeights: if you want to initialize them or not
	*/
	public ANN(int[][] ArrayPassed, boolean randomWeights) {
		this.INPUT_NODES = ArrayPassed;
		if (randomWeights){
			initializeWeights();
		} else {
			weights= new double [3];
			weights[0]= -0.3;
			weights[1]= 0.1;
			weights[2]= 0.3; //bias
		}
	}

	/**Method run: it runs the ANN
	The while loop runs until the correct weights are calculated, considering a margin of error 0.01.
	@return number of runs needed to calculate weights.
	*/
	public int run(){
		double someValue=0; 	//considering a marginal error of 0.01
		sumError=1;
		int activation;
		int error;
		int counter = 0;
		//someValue:  how accurate you want your software to be. It shouldnt be 0 because in some cases, the program wont be able to reach it.
		while (sumError > someValue){
			sumError=0;
			//for each node
			for (int i=0; i< INPUT_NODES.length; i++){
				activation = inputFunction(INPUT_NODES[i]);
				System.out.println("Activation: " + i + ": " + activation);
				error = INPUT_NODES[i][3] - activation; 				//activation is what we have
				sumError+= Math.abs(error);

				if (sumError != 0) {
					calculateDeltaWeight(error,INPUT_NODES[i]);
					break;
				}
			}
			counter++;
		}
		System.out.println(Arrays.toString(weights));
		return counter;
	}

	/** Method initialize Weights:
	It initializes all weights randomly, considering positive or negative cases.
	*/
	public void initializeWeights(){
		//initialize weights with random
		//weights ()-1,1)
		weights = new double[3];
		for (int i=0; i<2; i++){
			double sing =  Math.random();
			double weight =  Math.random();
			if ( sing > 0.5){
				weights[i] = weight;
			} else {
				weights[i] = -weight;
			}
		}
	}

	/**Method calculates the input function and calls method activaton function
	@param inputs
	@return value of activation function.
	*/
	public int inputFunction (int[] inputs){
		double result = 0;
		for (int i=0; i<weights.length; i++){
			result+= (weights[i] * inputs[i]);
		}
		//call step function
		return activationFuntion(result);
	}

	/**Method Activation function: of type Step function
	@result: from input function
	@return 1 or 0
	*/
	public int activationFuntion (double result){
		final double  EPSILON = 1E-14;
		//if is bellow 0 assign 0
		//if o or above assign  1
		if (result<-EPSILON){	//this number was changed to epsilon because of the representation of binary floating numbers in java. It should be 0.
			return 0;
		} else {
			return 1;
		}
	}

	/** Method to calculate delta Weight
	@param error
	@param row
	*/
	public void calculateDeltaWeight (int error, int [] row) {
		double  deltaWeight;
		for (int i=0; i<weights.length; i++) {
			deltaWeight= LEARNING_CONSTANT * error *row[i];
			weights[i] += deltaWeight;
		}
		System.out.println(Arrays.toString(weights));
	}

	/**Method prediction: it predicts where a point could be placed based on the weights calculated.
	@param x
	@param y
	@return result2: 1 if the point is above the decision line, else 0.
	*/
	public int prediction (int x, int y){
		//Im not passing the bias because is always 1.
		double result= weights[0]*x + weights[1]*y + weights[2];
		int result2=  activationFuntion(result);
		return result2;
	}
}

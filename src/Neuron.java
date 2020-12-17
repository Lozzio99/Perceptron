package Perceptron.src;


import java.util.Random;

abstract class Neuron {
    //create an abstract Class Neuron that represents the process of the Hebbian learning rule
    public double sum_Errors;
    public double [] weights ;
    public double [] w;
    private final double learningK =0.001; //constant that determines how fast the neuron can learn, bigger is faster but less precise

    public Neuron ()
    {
        //initialisation function for the neuron with given weights
        this.w = new double []{-0.3,0.1,0.3};
        this.sum_Errors = 0;
    }
    public Neuron(int numOfInputs)
    {
        //initialisation function for the neuron with random weights
        sum_Errors = 0;
        weights = new double[numOfInputs];
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i<weights.length; i++)
        {
            weights[i] = r.nextDouble();
            if(r.nextDouble()<0.5)
            {
                weights[i]*=-1;
            }
        }
        //wx starts as a random number between -1 and 1
        //wy starts as a random number between -1 and 1
        //wb starts as a random number between -1 and 1
        //learning constant is 0.01
    }

    public void train(double [] inputs , double target)
    { //training function for the GUI

        sum_Errors = 0;
        double error = target - eval(inputs); //the error is the correct answer - gues
        this.sum_Errors += Math.abs(error);
        for (int i = 0;i<weights.length; i++ )                  //wx is now wx + error * x * c
        {                                                       //wy is now wy + error * y * c
            double deltaWeight = error * inputs[i] * learningK;  //wb is now wb + error * c
            weights[i] = weights[i] + deltaWeight;
        }
    }
    public double [] getWeights()
    {
        return this.w;
    }
    public double guessLineY(double value)
    {

        double w0 = this.weights[0];
        double w1 = this.weights[1];
        double wb = this.weights[2];
        return  -(wb/w1) -(w0/w1)* value;    //return the formula for the y after the slope and the rise evaluation
    }
    public double sign(double i)
    {
        //function that returns 1 if input is positive and -1 if input is negative
        if (i >= 0)
        { //if the input is positive (or 0)
            return 1;
        }
        else
        { //if the input is negative
            return -1;
        }
    }
    public double getSum()
    {
        return this.sum_Errors;
    }

    public double eval (double [] inputs)
    {
        //produces the output from two inputs plus the bias node
        double guess = 0;
        for (int i = 0; i< this.weights.length; i++)
        {
            guess += this.weights[i]*inputs[i];
            //guess is the sum of the inputs times their weights, plus the bias(times 1 or -1)
        }
        //the output is the sign of guess
        return sign(guess);
    }

    public abstract void train(int[] inputs, int target);
}
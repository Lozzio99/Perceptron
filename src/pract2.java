package Perceptron.src;
import java.util.Arrays;
import static java.util.concurrent.TimeUnit.*;
/**
 * Class to implement the perceptron based on given weights and points
 * output is the value of the learning at each weight correction
 *
 */
public class pract2
{
    private static Neuron perceptron;
    private final int [] input;
    private final int id;
    private final int target;
    private static final double lk =  0.1;  // the learning constant for the assignment
    private static double START;
    private static pract2 [] test;
    public pract2(int []x, int t, int n)
    {
        this.input = x;
        this.target = t;
        this.id = n;
    }
    public static void main (String [] args)
    {
        START = System.nanoTime();
        setPerceptron();
        int [] coord1 = new int[]{0,0,1};      //data values
        pract2 a = new pract2(coord1,0, 1);    //data element (values, classifier, id)
        int[] coord2 = new int[]{0,1,1};
        pract2 b = new pract2(coord2,0,2);
        int [] coord3 = new int[]{1,0,1};
        pract2 c = new pract2(coord3,0,3);
        int [] coord4 = new int[]{1,1,1};
        pract2 d = new pract2(coord4,1,4);
        test = new pract2[] { a,b,c,d};
        do {
            for (pract2 in : test) {
                System.out.print(" point : " + in.id + " ");      //train the perceptron with the inputs
                perceptron.train(in.input, in.target);
                if (perceptron.getSum() != 0)                     // if the value hasn't been guessed correctly
                {                                                 //restart the loop and correct the weights
                    break;
                }
            }
        } while (perceptron.getSum() != 0);
        System.out.println("process finished with weights : "+ Arrays.toString(perceptron.getWeights()));
        //display results obtained
        System.out.println("time needed :");
        double end =System.nanoTime()-START;
        long convert = MILLISECONDS.convert((long) end, NANOSECONDS);
        System.out.println(String.format("Time needed > %s  ms", convert));


    }

    public static void setPerceptron()  //implementation of the abstract class Neuron
    {
        perceptron = new Neuron()   //initialize perceptron with the constructor providing given weights
        {
            /**
             * Training function for the assignment:
             * The abstract methods implemented here are for the purpose
             *   of the analysis for the given points and weights.
             *   Small differences :
             *   the analysis is restarted after each error
             *   for this performance integers have been used for classifier and target
             *   order and application of the evaluation - correction process
             */
            @Override
            public void train(int [] values , int target)
            {
                perceptron.sum_Errors = 0;
                System.out.println(Arrays.toString(values)+ " > { target : "+ target+ " }");
                double sumActivation  = 0;

                for (int i = 1; i< w.length; i++)    //calculate the activation function of the value
                {
                    sumActivation += values[i-1]*w[i];
                    System.out.print(values[i-1]+ " x "+ w[i] );
                    System.out.print(" + ");
                }
                sumActivation += values[2]*w[0];     //calculate the activation function for the bias
                System.out.print(values[2]+ " x "+ w[0] );
                System.out.print(" + ");
                sumActivation = (double) Math.round(sumActivation*10d)/10d;
                int guess = activation(sumActivation);
                System.out.print(" = "+ sumActivation +" --output ->"+guess);
                int error;
                if (guess == target)
                {
                    System.out.println("  correct!");
                }
                else
                {
                    error = target - guess;
                    System.out.println("  Incorrect !  "+error);
                }
                error = target - guess;
                //the error is the correct answer - guess
                if (guess != target )
                {
                    double deltaWeight;                              // deltaWeight is the change for the weight vectors
                    deltaWeight = error * values[2] * lk;                //wb is now wb + error * c
                    deltaWeight = (double) Math.round(deltaWeight*10d)/10d;
                    {
                        System.out.print(" dW"+ 0 +" [ "+ error + " ] x "+ values[2] + " * k .... >>"+ deltaWeight);
                        System.out.print(" || updating weight" +0+ "--> [ "+w[0]+ " + " + deltaWeight);
                        w[0] = w[0] + deltaWeight;
                        w[0] = (double) Math.round(w[0]*10d)/10d;
                        System.out.println(" ] = "+ w[0]);
                    }
                    for (int i = 1;i<w.length; i++ )
                    {
                        deltaWeight = error * values[i-1] * lk;             //wx is now wy + error * y * c
                        if (deltaWeight != 0)                               //wy is now wx + error * x * c
                        {
                            System.out.print(" dW"+ i +" [ "+ error + " ] x "+ values[i-1] + " * k .... >>"+ deltaWeight);
                            System.out.print(" || updating weight" +i+ "--> [ "+w[i]+ " + " + deltaWeight);
                            w[i] = w[i] + deltaWeight;
                            w[i] = (double) Math.round(w[i]*10d)/10d;
                            System.out.println(" ] = "+ w[i]);
                        }
                    }
                }
                perceptron.sum_Errors += Math.abs(error);     //instance field of the perceptron
            }
            public int activation(double i)    //activation function with t = 0
            {
                if(i>= 0)                      //return integer 1 or 0
                    return 1;
                return 0;
            }
        };
    }
}
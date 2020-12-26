package Perceptron.src;
import Perceptron.src.lib.Matrix;
import Perceptron.src.lib.data;

import java.util.Random;

public class NeuralNetwork
{
    private Matrix weights_ho;
    private Matrix weights_ih;
    private Matrix bias_hid;
    private Matrix bias_out;

    private static double lk = 0.1;

    public void setLearningRate(double lk)
    {
        this.lk = lk;
    }
    public NeuralNetwork(int in_nodes, int hid_nodes, int out_nodes)
    {
        this.weights_ih = new Matrix(hid_nodes, in_nodes);
        this.weights_ih= Matrix.randomize(this.weights_ih);
        this.weights_ho = new Matrix(out_nodes,hid_nodes);
        this.weights_ho= Matrix.randomize(this.weights_ho);

        this.bias_hid = new Matrix(hid_nodes,1);
        this.bias_hid = Matrix.randomize(this.bias_hid);
        this.bias_out = new Matrix(out_nodes,1);
        this.bias_out = Matrix.randomize(this.bias_out);
    }

    public static void main(String [] args)
    {
        data[] tests = new data[4];

        // XOR                     inputs  v         targets v
        tests[0] = new data(new double[] {1,1},new double []{1});
        tests[1] = new data(new double[] {1,0},new double []{0});
        tests[2] = new data(new double[] {0,1},new double []{1});
        tests[3] = new data(new double[] {0,0},new double []{0});
        Matrix [] in = new Matrix[tests.length];
        Matrix [] tg = new Matrix[tests.length];

        for (int i = 0; i< tests.length; i++)
        {
            in [i] = new Matrix(tests[i].getInput());
            tg [i] = new Matrix(tests[i].getTarget());
        }

        NeuralNetwork x = new NeuralNetwork(tests[0].getInput().length, 16, tests[0].getTarget().length);
        for (int i = 0; i< 50000; i++)
        {
            int k = new Random().nextInt(tests.length);
            x.train(in[k],tg[k]);
        }

        // simple XOR evaluation :)
        for (int k = 0; k< tests.length; k++)
        {
            x.feedforward(tests[k].getInput()).printMatrix();
        }
    }
    public Matrix feedforward(double [] input)
    {
        //generating hidden output
        Matrix inputs = new Matrix(input);
        Matrix hidden = Matrix.multiply(this.weights_ih,inputs);
        hidden = Matrix.add(hidden,this.bias_hid);
        hidden = Matrix.map(hidden);

        //generating final output   NEW MATRIX RESULTING FROM THE EVOLVING OUTPUT
        Matrix outputs = Matrix.multiply(this.weights_ho,hidden);
        outputs = Matrix.add(outputs,this.bias_out);
        outputs= Matrix.map(outputs);
        return outputs;
    }
    public void train(Matrix given, Matrix targets)
    {
        //generating hidden output
        Matrix inputs = given;
        Matrix hidden = Matrix.multiply(this.weights_ih,inputs);
        hidden = Matrix.add(hidden,this.bias_hid);
        //1
        hidden= Matrix.map(hidden);


        //generating final output   NEW MATRIX RESULTING FROM THE EVOLVING OUTPUT
        Matrix outputs =Matrix.multiply(this.weights_ho,hidden);
        outputs = Matrix.add(outputs,this.bias_out);
        outputs= Matrix.map(outputs);

        //   map((E-O)(dfunc(O))H^)

        //calculate out error
        //E-O
        Matrix tgt = new Matrix(targets.getMatrix());
        Matrix out_error = Matrix.subtract(tgt, outputs);
        // calculate out gradient
        Matrix o_gradient = Matrix.dfunc(outputs);
        o_gradient = o_gradient.multiply(out_error);
        o_gradient = o_gradient.multiply(this.lk);

        //hidden layer transposed
        Matrix hid_transp = Matrix.transpose(hidden);

        //calculate and adjust out weights
        //OH^
        Matrix dw_ho = Matrix.multiply(o_gradient,hid_transp);
        this.weights_ho = Matrix.add(this.weights_ho,dw_ho);
        this.bias_out = Matrix.add(this.bias_out,o_gradient);

        //calculate hidden error
        Matrix w_ho_transp = Matrix.transpose(this.weights_ho);

        Matrix hidden_er = Matrix.multiply(w_ho_transp,out_error);
        //calculate hidden gradient
        Matrix h_gradient = Matrix.dfunc(hidden);
        h_gradient = h_gradient.multiply(hidden_er);
        h_gradient = h_gradient.multiply(this.lk);

        //calculate and adjust hidden weights
        Matrix inputs_T = Matrix.transpose(inputs);
        Matrix d_ih = Matrix.multiply(h_gradient,inputs_T);

        this.weights_ih = Matrix.add(this.weights_ih,d_ih);

        this.bias_hid = Matrix.add(this.bias_hid,h_gradient);

    }
}

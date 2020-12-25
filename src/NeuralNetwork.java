package Perceptron.src;
import Perceptron.src.lib.Matrix;
import Perceptron.src.lib.data;

class NeuralNetwork
{
    private Matrix weights_ho;
    private Matrix weights_ih;
    private Matrix bias_hid;
    private Matrix bias_out;

    private final double lk = 0.1;

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
        tests[0] = new data(new double[] {1,0},new double []{0});
        tests[1] = new data(new double[] {0,1},new double []{0});
        tests[2] = new data(new double[] {1,1},new double []{1});
        tests[3] = new data(new double[] {0,0},new double []{1});


        Matrix [] in = new Matrix[4];
        Matrix [] tg = new Matrix[4];

        in [0] = new Matrix(tests[0].getInput());
        tg [0] = new Matrix(tests[0].getTarget());
        in [1] = new Matrix(tests[1].getInput());
        tg [1] = new Matrix(tests[1].getTarget());
        in [2] = new Matrix(tests[2].getInput());
        tg [2] = new Matrix(tests[2].getTarget());
        in [3] = new Matrix(tests[3].getInput());
        tg [3] = new Matrix(tests[3].getTarget());

        NeuralNetwork x = new NeuralNetwork(tests[0].getInput().length, 4, tests[0].getTarget().length);
        for (int i = 0; i< 300000; i++)
        {
            for (int k = 0; k< 4; k++)
            {
                x.train(in[k],tg[k]);
            }
        }

        // simple XOR evaluation :)
        x.feedforward(new double[]{1,0});
        x.feedforward(new double[]{0,1});
        x.feedforward(new double[]{1,1});
        x.feedforward(new double[]{0,0});

    }
    private void feedforward(double [] input)
    {
        //generating hidden output
        Matrix inputs = new Matrix(input);
        System.out.println(" inputs -> ");
        inputs.printMatrix();
        Matrix hidden = Matrix.multiply(this.weights_ih,inputs);
        hidden = Matrix.add(hidden,this.bias_hid);
        hidden= Matrix.map(hidden);

        //generating final output   NEW MATRIX RESULTING FROM THE EVOLVING OUTPUT
        Matrix outputs = Matrix.multiply(this.weights_ho,hidden);
        outputs = Matrix.add(outputs,this.bias_out);
        outputs= Matrix.map(outputs);
        System.out.print(" evaluation ->  ");
        outputs.printMatrix();
        System.out.println();
    }
    private void train (Matrix given , Matrix targets)
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
        // map(E-O)(dfunc)OH^
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

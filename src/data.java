package Perceptron.src;

public class data {
    double [] input;
    double [] target;
    public data(double [] inputs, double [] target )
    {
        this.input = inputs;
        this.target = target;
    }
    public double [] getInput()
    {
        return this.input;
    }
    public double [] getTarget()
    {
        return this.target;
    }
}

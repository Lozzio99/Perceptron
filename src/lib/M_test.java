package Perceptron.src.lib;

public class M_test
{
    public static void main (String [] args)
    {
        double [][] x = new double [] [] {
                {10,11},
                {12,13}
        };
        Matrix m = new Matrix(x);
        m.printMatrix();
        x =  new double[][]{
                {1,2},
                {3,4}
        };
        Matrix n = new Matrix(x);

        m.printMatrix();
        m = Matrix.map(m);
        m.printMatrix();
    }
}

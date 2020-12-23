package Perceptron.src;

import java.util.Random;

public class Matrix {
    public int n_rows;
    public int n_cols;
    public double [][] matrix;
    public static void main(String [] args )
    {
        Matrix m = new Matrix(3,4);
        Matrix n = new Matrix(4,3);
        m.randomize();
        n.randomize();
        Matrix c ;
        //c = m.multiply(n);
        //c.printMatrix();
        c= new Matrix(m.n_rows,n.n_cols);
        c.setMatrix(m.multiply(m,n));
        c.printMatrix();
        c.transpose(c);
        System.out.println("\n");
        c.printMatrix();
        activate(c);
        System.out.println("\n");
        c.printMatrix();
    }
    public Matrix (int num_rows,int num_cols)
    {
        this.n_cols = num_cols;
        this.n_rows = num_rows;
        this.matrix = new double[num_rows][num_cols];
    }
    public void transpose()
    {
        Matrix result = new Matrix(this.n_cols,this.n_rows);
        for ( int i = 0; i< this.n_cols; i++)
        {
            for (int k = 0; k< this.n_rows; k++)
            {
                result.matrix[i][k] = this.matrix[k][i];
            }
        }
        setMatrix(result);
    }
    public static double activation (double x)
    {
        return x*0.0000001;
    }
    public static void activate(Matrix m)
    {
        double [][] result = new double[m.n_rows][m.n_cols];
        for ( int i = 0; i< m.n_rows; i++)
        {
            for ( int k = 0; k< m.n_cols; k++)
            {
                result [i][k] = activation(m.matrix[i][k]);
            }
        }
        m.setMatrix(result);
    }
    public static void transpose(Matrix m)
    {
        double [][] result = new double[m.n_cols][m.n_rows];
        for ( int i = 0; i< m.n_cols; i++)
        {
            for (int k = 0; k< m.n_rows; k++)
            {
                result[i][k] = m.matrix[k][i];
            }
        }
        m.setMatrix(result);
    }
    public void setMatrix (Matrix m)
    {
        this.matrix = m.matrix;
    }
    public void setMatrix (double [][] m)
    {
        this.matrix = m;
    }
    public double [][] getMatrix ()
    {
        return this.matrix;
    }
    public Matrix multiply( Matrix m)
    {
        if (this.n_cols != m.n_rows)
        {
            System.out.println("error -> dimensions mismatching");
            return null;
        }
        Matrix result = new Matrix(this.n_rows,m.n_cols);
        for (int i = 0; i<this.n_rows; i++)
        {
            for ( int j = 0; j< m.n_cols; j++)
            {
                for (int k = 0; k< this.n_cols; k++)
                {
                    result.matrix[i][j] += this.matrix[i][k] * m.matrix[k][j];
                }
            }
        }
        return result;
    }
    //static method that multiplies two given matrices
    public static double [][] multiply( Matrix n, Matrix m)
    {
        if (n.n_cols != m.n_rows)
        {
            System.out.println("error -> dimensions mismatching");
            return null;
        }
        double [][] result = new double[n.n_rows][m.n_cols];
        for (int i = 0; i<n.n_rows; i++)
        {
            for ( int j = 0; j< m.n_cols; j++)
            {
                for (int k = 0; k< n.n_cols; k++)
                {
                    result[i][j] += n.matrix[i][k] * m.matrix[k][j];
                }
            }
        }
        return result;
    }
    public void randomize()
    {

        for (int i = 0; i< this.n_rows; i++)
        {
            for ( int k = 0; k< this.n_cols; k++)
            {
                this.matrix[i][k] =new Random().nextDouble();
            }
        }
    }

    public void printMatrix (){
        for (int i = 0; i< this.n_rows; i++)
        {
            for ( int k = 0; k< this.n_cols; k++)
            {
                System.out.print(this.matrix[i][k]+ "   ");
            }
            System.out.println();
        }
    }
    public void scaleMatrix (double value){
        for (int i = 0; i< this.n_rows; i++)
        {
            for ( int k = 0; k< this.n_cols; k++)
            {
                this.matrix[i][k] *= value;
            }
        }
    }
    //increase manually by a value
    public void add (double value){
        for (int i = 0; i< this.n_rows; i++)
        {
            for ( int k = 0; k< this.n_cols; k++)
            {
                this.matrix[i][k] += value;
            }
        }
    }
    //increase manually by a value
    public void subtract (Matrix m){
        for (int i = 0; i< this.n_rows; i++)
        {
            for ( int k = 0; k< this.n_cols; k++)
            {
                this.matrix[i][k] -= m.matrix[i][k];
            }
        }
    }
    public void add (Matrix m){
        for (int i = 0; i< this.n_rows; i++)
        {
            for ( int k = 0; k< this.n_cols; k++)
            {
                this.matrix[i][k] += m.matrix[i][k];
            }
        }
    }
    //decrease manually by a value
    public void subtract (double value){
        for (int i = 0; i< this.n_rows; i++)
        {
            for ( int k = 0; k< this.n_cols; k++)
            {
                this.matrix[i][k] -= value;
            }
        }
    }
}

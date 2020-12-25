package Perceptron.src.lib;
import java.util.Random;

public class Matrix
{
    public int n_rows;
    public int n_cols;
    public double [][] matrix;
    public Matrix (int num_rows,int num_cols)
    {
        this.n_cols = num_cols;
        this.n_rows = num_rows;
        this.matrix = new double[num_rows][num_cols];
    }
    public Matrix (double [] input)
    {
        this.matrix = clone(input);
        this.n_rows = input.length;
        this.n_cols = 1;
    }
    public Matrix (double [][] matrix)
    {
        this.n_rows = matrix.length;
        this.n_cols = matrix[0].length;
        this.matrix = clone(matrix);
    }

    public double [][] clone (double [][] m)
    {
        double [][] result = new double[m.length][m[0].length];
        for (int i = 0; i< m.length; i++)
        {
            for (int k = 0;k< m[0].length; k++)
            {
                result [i][k] = m[i][k];
            }
        }
        return result;
    }
    public static Matrix clone (Matrix m)
    {
        double [][] result = new double[m.n_rows][m.n_cols];
        for (int i = 0; i< m.n_rows; i++)
        {
            for (int k = 0;k< m.n_cols; k++)
            {
                result [i][k] = m.getMatrix()[i][k];
            }
        }
        return new Matrix(result);
    }
    public double [][] clone (double [] m)
    {
        double [][] result = new double[m.length][1];
        for (int i = 0; i< m.length; i++)
        {
            result [i][0] = m[i];
        }
        return result;
    }
    public static Matrix transpose (Matrix m)
    {
        double [][] result = new double[m.n_cols][m.n_rows];
        for ( int i = 0; i< m.n_cols; i++)
        {
            for (int k = 0; k< m.n_rows; k++)
            {
                result[i][k] = m.getMatrix()[k][i];
            }
        }
        return new Matrix(result);
    }

    public void setMatrix (double [][] m)
    {
        this.n_rows = m.length;
        this.n_cols = m[0].length;
        this.matrix = clone(m);
    }
    public void setMatrix (Matrix m)
    {
        this.n_rows = m.n_rows;
        this.n_cols = m.n_cols;
        this.matrix = clone(m.getMatrix());
    }

    public double [][] getMatrix ()
    {
        return this.matrix;
    }

    public static Matrix product(Matrix n, Matrix m)
    {
        if (n.n_rows != m.n_rows|| n.n_cols != m.n_cols)
        {
            System.out.println(n.n_rows + " -> "+ m.n_rows);
            System.out.println(n.n_cols + " -> "+ m.n_cols);
            System.out.println("error");
            return null;
        }
        double [][] res = new double[n.n_rows][m.n_cols];
        for (int i = 0; i<n.n_rows; i++)
        {
            for ( int j = 0; j< n.n_cols; j++)
            {
                res[i][j] = n.getMatrix()[i][j] * m.getMatrix()[i][j];
            }
        }
        return new Matrix(res);
    }
    public Matrix multiply(Matrix m)
    {
        if (m.n_rows == 1 && m.n_cols == 1)
        {
            return this.multiply(m.getMatrix()[0][0]);
        }
        if (this.n_rows != m.n_rows|| this.n_cols != m.n_cols)
        {
            System.out.println(this.n_rows + " -> "+ m.n_rows);
            System.out.println(this.n_cols + " -> "+ m.n_cols);
            System.out.println("error");
            return null;
        }
        double [][] res = new double[this.n_rows][m.n_cols];
        for (int i = 0; i<this.n_rows; i++)
        {
            for ( int j = 0; j< this.n_cols; j++)
            {
                res[i][j] = this.getMatrix()[i][j] * m.getMatrix()[i][j];
            }
        }
        return new Matrix(res);
    }

    public static double [][] multiplyM (Matrix n, Matrix m)
    {
        if (n.n_cols != m.n_rows)
        {
            System.out.println("error");
            return null;
        }
        double [][] result = new double[n.n_rows][m.n_cols];
        for (int i = 0; i<n.n_rows; i++)
        {
            for ( int j = 0; j< m.n_cols; j++)
            {
                double sum = 0;
                for (int k = 0; k< n.n_cols; k++)
                {
                    sum += n.getMatrix()[i][k] * m.getMatrix()[k][j];
                }
                result [i][j] = sum;
            }
        }
        return result;
    }
    public static Matrix multiply (Matrix n, Matrix m)
    {
        if (n.n_cols != m.n_rows)
        {
            System.out.println("error");
            return null;
        }
        double [][] res = new double[n.n_rows][m.n_cols];
        for (int i = 0; i<n.n_rows; i++)
        {
            for ( int j = 0; j< m.n_cols; j++)
            {
                double sum = 0;
                for (int k = 0; k< n.n_cols; k++)
                {
                    sum += n.getMatrix()[i][k] * m.getMatrix()[k][j];
                }
                res [i][j] = sum;
            }
        }
        return new Matrix(res);
    }

    public static double activation (double x)
    {
        return 1/(1 + Math.exp(-x));
    }

    public static Matrix dfunc (Matrix m)
    {
        double [][] result = new double[m.n_rows][m.n_cols];
        for ( int i = 0; i< m.n_rows; i++)
        {
            for ( int k = 0; k< m.n_cols; k++)
            {
                result [i][k] = m.getMatrix()[i][k] * (1 - m.getMatrix()[i][k]);
            }
        }
        return new Matrix(result);
    }

    public static Matrix map(Matrix m)
    {
        double [][] result = new double[m.n_rows][m.n_cols];
        for (int i = 0; i<m.n_rows; i++)
        {
            for (int k = 0; k< m.n_cols; k++)
            {
                result[i][k] = activation(m.getMatrix()[i][k]);
            }
        }
        return new Matrix(result);
    }
    public void map()
    {
        double [][] res = new double[this.n_rows][this.n_cols];
        for (int i = 0; i<this.n_rows; i++)
        {
            for (int k = 0; k< this.n_cols; k++)
            {
                res[i][k] = activation(this.getMatrix()[i][k]);
            }
        }
        this.setMatrix(res);
    }



    public static Matrix randomize(Matrix m)
    {
        double [][] res = new double [m.n_rows][m.n_cols];
        for (int i = 0; i< m.n_rows; i++)
        {
            for ( int k = 0; k< m.n_cols; k++)
            {
                res[i][k] =new Random().nextDouble();
                if(new Random().nextDouble()<0.5)
                {
                    res[i][k] *=-1;
                }
            }
        }
        return new Matrix(res);
    }

    public void printMatrix ()
    {
        //System.out.println(this.n_rows+" -> "+ this.n_cols);
        for (int i = 0; i< this.n_rows; i++)
        {
            for ( int k = 0; k< this.n_cols; k++)
            {
                System.out.print(this.matrix[i][k]+ "   ");
            }
            System.out.println();
        }
    }

    public static Matrix scaleMatrix (Matrix m, double value)
    {
        double [][] res = new double [m.n_rows][m.n_cols];
        for (int i = 0; i< m.n_rows; i++)
        {
            for ( int k = 0; k< m.n_cols; k++)
            {
                res [i][k] = m.matrix[i][k] * value;
            }
        }
        return new Matrix(res);
    }

    public Matrix multiply ( double value)
    {
        double [][] res = new double [this.n_rows][this.n_cols];
        for (int i = 0; i< this.n_rows; i++)
        {
            for ( int k = 0; k< this.n_cols; k++)
            {
                res [i][k] = this.getMatrix()[i][k] * value;
            }
        }
        return new Matrix(res);
    }




    public static Matrix subtract (Matrix n , Matrix m)
    {
        if (n.n_rows!= m.n_rows||n.n_cols!= m.n_cols)
        {
            System.out.println("error");
            return null;
        }
        double [][] result =  new double[n.n_rows][n.n_cols];
        for (int i = 0; i< n.n_rows; i++)
        {
            for ( int k = 0; k< n.n_cols; k++)
            {
                result[i][k] = n.getMatrix()[i][k] - m.getMatrix()[i][k];
            }
        }
        return new Matrix(result);
    }


    //--------------------------------------------------------//

    //for later usage methods

    //-------------------------------------------------------//


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
    public static double [][] subtract (double [][] n , double [][] m)
    {
        if (n.length!= m.length||n[0].length!= m[0].length)
        {
            System.out.println("error");
            return null;
        }
        double [][] result = new double[n.length][n[0].length];
        for (int i = 0; i< n.length; i++)
        {
            for ( int k = 0; k< n[0].length; k++)
            {
                result [i][k] = n[i][k] - m[i][k];
            }
        }
        return result;
    }
    //increase manually by a value
    public void subtract (Matrix m)
    {

        if (this.n_rows!= m.n_rows||this.n_cols!= m.n_cols)
        {
            System.out.println("error");
            return ;
        }
        for (int i = 0; i< this.n_rows; i++)
        {
            for ( int k = 0; k< this.n_cols; k++)
            {
                this.matrix[i][k] -= m.matrix[i][k];
            }
        }
    }

    public static double [][] add (double [][] m, double [][] n)
    {

        if (m.length!=n.length&& m[0].length!= n[0].length)
        {
            System.out.println("error");
            return null;
        }
        for (int i = 0; i< m.length; i++)
        {
            for ( int k = 0; k< m[0].length; k++)
            {
                m[i][k] += n[i][k];
            }
        }
        return m;
    }

    //increase manually by a value
    public double [][] add (double value)
    {

        double [][] res  = new double [this.n_rows][this.n_cols];

        for (int i = 0; i< this.n_rows; i++)
        {
            for ( int k = 0; k< this.n_cols; k++)
            {

                res[i][k] = this.getMatrix()[i][k] + value;
            }
        }
        return res;
    }
    public void transpose()
    {
        Matrix result = new Matrix(this.n_cols,this.n_rows);
        for ( int i = 0; i< this.n_cols; i++)
        {
            for (int k = 0; k< this.n_rows; k++)
            {
                result.matrix[i][k] = this.getMatrix()[k][i];
            }
        }
        setMatrix(result.getMatrix());
    }


    public static Matrix ddfunc(Matrix m)
    {
        double [][] res = new double[m.n_rows][m.n_cols];
        for ( int i = 0; i< m.n_rows; i++)
        {
            for ( int k = 0; k< m.n_cols; k++)
            {
                res [i][k] = (activation(m.getMatrix()[i][k])*(1-activation(m.getMatrix()[i][k])));
            }
        }
        Matrix result = new Matrix(res);
        return result;
    }


    public void add (Matrix m)
    {

        if (this.n_rows!= m.n_rows||this.n_cols!= m.n_cols)
        {
            System.out.println("error");
            return;
        }

        double [][] res = new double[n_rows][n_cols];
        if (m.n_rows== 1 && m.n_cols ==1 )
        {
            res = this.add(m.getMatrix()[0][0]);
            this.setMatrix(res);
            return;
        }
        else
            for (int i = 0; i< this.n_rows; i++)
            {
                for ( int k = 0; k< this.n_cols; k++)
                {
                    res [i][k]= this.getMatrix()[i][k] + m.getMatrix()[i][k];
                }
            }
        this.setMatrix(res);
    }
    public static Matrix add (Matrix n , Matrix m)
    {
        if (n.n_rows!= m.n_rows||n.n_cols!= m.n_cols)
        {
            System.out.println("error");
            return null;
        }
        double [][] res =  new double[n.n_rows][n.n_cols];
        for (int i = 0; i< n.n_rows; i++)
        {
            for ( int k = 0; k< n.n_cols; k++)
            {
                res[i][k] = n.getMatrix()[i][k] + m.getMatrix()[i][k];
            }
        }
        return new Matrix(res);
    }



}

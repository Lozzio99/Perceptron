package Perceptron.src.lib;

public class Map {
    double point;
    public Map(double x)
    {
        this.point = x;
    }
    public static void main(String[] args)
    {
        Map value = new Map(0.1);   // test mapping
        double se = value.map(0 ,0,700);
        double es = value.map(0 ,700,0);

    }


        //start 1 - stop 1 -   start2 - stop 2  number

    public double map(double n, double stop1, double stop2)
    {
        double newval = Math.abs((float)n);
        if (stop1<stop2)     // Mapping from a 4 quadrants plane to the java plane //0,0 mapped on the center
        {
            double d = stop2/2.0;
            newval*=d;
            if (n>0)
            {
                return (d+newval);
            }
            else
            {
                return d-newval;
            }
        }
        if (stop1>stop2)
        {
            double d = stop1/2.0;
            newval*=d;
            if (n>0)
            {
                return (d-newval);
            }
            else
            {
                return d+newval;
            }
        }
        return newval;
    }
}

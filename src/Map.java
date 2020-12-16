
public class Map {
    double point;
    public Map(double x)
    {
        this.point = x;
    }
    public static void main(String[] args)
    {
        Map value = new Map(0.1);   // test mapping
        double se = value.map(0 ,-1, 1,0,700);
        double es = value.map(0 ,-1, 1,700,0);

    }


        //start 1 - stop 1 -   start2 - stop 2  number

    public double map(double n, double start1, double start2, double stop1, double stop2)
    {
        double newval = Math.abs((float)n);
        if (stop1<stop2)     // Mapping from a 4 quadrants plane to the java plane //0,0 mapped on the center
        {
            newval*=350;
            if (n>0)
            {
                return (350+newval);
            }
            else
            {
                return 350-newval;
            }
        }
        if (stop1>stop2)
        {
            newval*=350;
            if (n>0)
            {
                return (350-newval);
            }
            else
            {
                return 350+newval;
            }
        }
        return newval;
    }
}

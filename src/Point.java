package Perceptron.src;
import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Point  extends JComponent{

    private Neuron perceptron;
    private Neuron perceptron2;
    private double[] inputs; //create an array of input for the point, to show points on the screen
    private double x; //the x coordinate
    private double y; //the y coordinate
    private double classifier; //1 or -1 depending on the output of the Perceptron weights
    private double classifier2; //here the second classifier
    private double classified; //the classifier given by the perceptron
    private double classified2; //the classifier given by the perceptron
    private Map value;
    private int iteration;

    public Point(double x_, double y_, double o_, double p_)
    {
        classified = 0;
        //initialisation function for Point
        this.inputs = new double[3];
        this.inputs[0] = x_; //x is the value specified
        this.x = x_;
        this.inputs[1] = y_; //y is the value specified
        this.y = y_;
        this.inputs[2] = 1;
        this.classifier = o_;   //gets a classifier between 1 and -1, (green or red);
        this.classifier2 = p_;
        value = new Map(0 );
    }

    public double setX(double x1)
    {
        double d = DoubleCore.frame.getWidth()-18;
        return value.map(x1,0,d);
    }
    public double setY(double x1)
    {
        double d = DoubleCore.frame.getHeight()-18;
        return value.map(x1,d,0);
    }
    public double getClassifier() { return this.classifier;   }
    public double getClassifier2(){return this.classifier2 ;   }
    public double getClassified(){return this.classified;}
    public double getClassified2(){return this.classified2;}
    public void setAssignedClassified(double given) {this.classified = given;   }
    public void setAssignedClassified2(double given){this.classified2 = given;}
    public double [] getInput () { return this.inputs;   }
    public void setValue1 (Neuron n1)
    {
        this.perceptron = n1;
    }
    public void setValue2 (Neuron n2)
    {
        this.perceptron2 = n2;
    }
    public Line2D.Double guess1()
    {
        double guessYLine = this.perceptron.guessLineY(-1);
        double guessYLine2 = this.perceptron.guessLineY(1);
        Line2D.Double guessLine = new Line2D.Double(setX(-1),setY(guessYLine),setX(1),setY(guessYLine2));
        return guessLine;
    }
    public Line2D.Double guess2()
    {
        double guessYLineTwo = perceptron2.guessLineY(-1);
        double guessYLineTwo2 = perceptron2.guessLineY(1);
        Line2D.Double guessLine2 = new Line2D.Double(setX(-1),setY(guessYLineTwo),setX(1),setY(guessYLineTwo2));
        return guessLine2;
    }
    public Line2D.Double line1 (){
        Point2D.Double a = new Point2D.Double(setX(-1), setY(f(-1)));
        Point2D.Double a1 = new Point2D.Double(setX(1),setY(f(1)));
        Line2D.Double line = new Line2D.Double(a ,a1);
        return line;
    }
    public Line2D.Double line2 (){
        Point2D.Double A = new Point2D.Double(setX(-1), setY(g(-1)));
        Point2D.Double A2 = new Point2D.Double(setX(1),setY(g(1)));
        Line2D.Double line2 = new Line2D.Double(A ,A2);
        return line2;
    }

    public void paintComponent(Graphics g)
    {
        iteration++;
        Graphics2D g2 =(Graphics2D)  g;
        Ellipse2D.Double b ;
        g2.setStroke(new BasicStroke(2.0f));
        g2.setColor(Color.black);
        g2.draw(line1());
        g2.draw(line2());
        g2.draw(guess1());
        g2.draw(guess2());


        double frameX = setX(this.x);
        double frameY = setY(this.y);
        b = new Ellipse2D.Double(frameX,frameY, 12, 12);
        if (this.getClassifier() == 1 && this.getClassifier2()== 1 )    //distinct colours for different classifiers
        {
            g2.setColor(Color.red);
        }
        if (this.getClassifier() == -1 && this.getClassifier2() == 1)
        {
            g2.setColor(Color.green);
        }
        if (this.getClassifier2()== -1&& this.getClassifier()== -1){
            g2.setColor(Color.red);
        }
        if (this.getClassifier()== 1&& this.getClassifier2()== -1){
            g2.setColor(Color.GREEN);
        }
        g2.fill(b);
        if (this.getClassifier2()==this.getClassified2()&&this.getClassifier()==this.getClassified())
        {
            g2.setColor(Color.black);
            g2.draw(b);
        }
    }
    private double f(double x) { return DoubleCore.f(x) ; }
    private double g (double x ){return DoubleCore.g(x);}
}

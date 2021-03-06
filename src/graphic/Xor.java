package Perceptron.src.graphic;
import Perceptron.src.NeuralNetwork;
import Perceptron.src.lib.Matrix;
import Perceptron.src.lib.data;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.JFrame;


public class Xor
{
    private static WindowEvent listen;
    public static JFrame frame;
    public double r = 0.01;
    public static int WIDTH = 700;
    public static int HEIGHT = 700;
    public int iteration = 1;
    public Matrix[] in;
    public Matrix[] tg;
    public data[] tests;

    public static void main (String [] args)
    {
        setup();
        new Xor();
    }
    public Xor ()
    {
        test_data();
        visualize();
    }
    public void visualize ()
    {
        graphic gfx = new graphic();
        do {
            gfx.evaluate();
            frame.add(gfx);
            frame.setVisible(true);
        } while (true);
    }
    public void test_data ()
    {
        this.tests = new data[4];
        // XOR                          inputs  v         targets v
        this.tests[0] = new data(new double[] {1,1},new double []{0});
        this.tests[1] = new data(new double[] {1,0},new double []{1});
        this.tests[2] = new data(new double[] {0,1},new double []{1});
        this.tests[3] = new data(new double[] {0,0},new double []{0});
        this.in = new Matrix[this.tests.length];
        this.tg = new Matrix[this.tests.length];

        for (int i = 0; i< this.tests.length; i++)
        {
            this.in [i] = new Matrix(this.tests[i].getInput());
            this.tg [i] = new Matrix(this.tests[i].getTarget());
        }
    }

    public static void setup()
    {
        frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);//700
        frame.setMinimumSize(new Dimension(WIDTH,HEIGHT));
        frame.getContentPane();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //this window listener should give a stable access to the window closing tool,
        // given the program the time to escape the current method
        WindowAdapter closed = new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                Xor.listen = new WindowEvent(Xor.frame, 201);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(Xor.listen);
                System.out.println("System closed by user");
                System.exit(0);
            }
        };
        frame.addWindowListener(closed);
        frame.setVisible(true);
        frame.pack();
    }

    public class graphic extends JComponent
    {
        public NeuralNetwork brain;
        public graphic()
        {
            // the more hidden nodes the more it's accurate
            this.brain = new NeuralNetwork(2,128,1);
        }
        public void evaluate ()
        {
            //give it a bit to run
            if (iteration%100 == 0)
            {
                System.out.println(iteration + "  - learning rate : "+ r);
            }
            iteration++;
            int k = new Random().nextInt(tests.length);
            brain.train(in[k],tg[k]);
            if(r<0.5)
            {
                r +=0.0001;
                brain.setLearningRate(r);
            }
        }

        public void paintComponent (Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g;
            int resolution = 10;
            int cols = Xor.WIDTH/resolution;
            int rows = Xor.HEIGHT/resolution;
            for (int i = 0; i<cols; i++)
            {
                for (int k = 0; k<rows; k++)
                {
                    double x1 = i/(double)cols;
                    double x2 = k/(double)rows;
                    double [] inputs = new double[]{x1,x2};
                    int y = (int)(this.brain.feedforward(inputs).getMatrix()[0][0]*255);
                    Color r = new Color(255-y,y,y/2);
                    g2.setColor(r);
                    g2.fillRect(i*resolution,k*resolution,resolution,resolution);
                    g2.setColor(Color.white);
                    g2.drawRect(i*resolution,k*resolution,resolution,resolution);
                }
            }
            frame.repaint();

        }

    }



}

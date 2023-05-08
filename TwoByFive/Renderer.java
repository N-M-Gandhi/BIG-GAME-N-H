import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
/**
 * Write a description of class Renderer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Renderer
{
    private int slices;
    private int scale;
    private int viewDist;
    private String[][] view;
    //private InputListener input;

    private static final Color BACKGROUND = Color.BLACK;
    private JFrame frame;
    private BufferedImage image;

    public Renderer(InputActivator input)
    {
        slices = 320/16;
        scale = 16;
        viewDist = 200/16;
        view = new String[viewDist * 1][slices * 1];
        //input = i;
        for(int y = 0; y < view.length; y++)
        {
            for(int x = 0; x < view[y].length; x++)
            {
                view[y][x] = " . ";
            }
        }

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    createAndShowGUI(input);
                }
            });

        //Wait until display has been drawn
        try
        {
            while (frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void createAndShowGUI(InputActivator input)
    {
        frame = new JFrame();
        image = new BufferedImage(slices*scale,viewDist*scale,BufferedImage.TYPE_INT_RGB);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(input);
        frame.add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setVisible(true);
    }
    
    // Sets the title of the window.
    public void setTitle(String title)
    {
        frame.setTitle(title);
    }

    public Renderer(int s, int w, int v)
    {
        slices = s;
        scale = w;
        viewDist = v;
        view = new String[viewDist * scale][slices * scale];
        for(int y = 0; y < view.length; y++)
        {
            for(int x = 0; x < view[y].length; x++)
            {
                view[y][x] = " . ";
            }
        }
    }

    public void render(Player player, String[][] map)
    {
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(BACKGROUND);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();

        for(int i = 0; i < slices; i++)
        {
            //calculate the slope of this slice
            double viewIncrement = (double)90/(double)(slices-1);
            double viewDeg = player.r() + 45 - viewIncrement * i;
            double slope = Math.tan(Math.toRadians(viewDeg));
            //calculate the length of this particular slice
            int sliceLength = viewDist - rayCast(slope, player, map);
            //fill slice with blank space so I dont get permanenet screen tarring
            for(int y = 0; y < view.length; y++)
            {
                view[y][i] = " . ";
                //translate to graphics
                if(y < view.length/2)
                {
                    graphics = image.createGraphics();
                    graphics.setColor(BACKGROUND);
                    graphics.fillRect(i*scale, y*scale, 1*scale, 1*scale);
                    graphics.dispose();
                }
                else
                {
                    graphics = image.createGraphics();
                    graphics.setColor(new Color(128,128,128));
                    graphics.fillRect(i*scale, y*scale, 1*scale, 1*scale);
                    graphics.dispose();
                }
            }
            //fill a slice to said length with at desired length
            for(int y = view.length/2 - sliceLength/2; y <  view.length/2 + sliceLength/2; y++)
            {
                view[y][i] = "000";
                //translate to graphics
                graphics = image.createGraphics();
                graphics.setColor(new Color(0,0,255));
                graphics.fillRect(i*scale, y*scale, 1*scale, 1*scale);
                graphics.dispose();
            }
        }
        //print();
        //transate to graphics
        frame.repaint();
    }

    public int rayCast(double slope, Player player, String[][] map)
    {
        int shortestIndex = viewDist;
        int flipMult = 1;
        if(slope > 0){slope = slope * -1;flipMult = -1;}
        //increment until hit a wall or reach view limit
        //x fisrt calculation
        for(double i = 0; i < viewDist; i+=0.1)
        {
            //flip the counting of i if the slope is positive
            double i2 = i * flipMult;

            //set up the square being checked for collision
            double lossYOffset = (double) i2 * slope;
            double lossyY = (double) player.y() + lossYOffset;
            int yValue = (int)(lossyY);
            int xValue = (int)player.x() + (int)i2;
            //check that you are in the bounds of the map
            if(yValue < 0 || xValue < 0 || yValue > map.length - 1 || xValue > map[yValue].length - 1)
            {
                //System.out.println("stopped - " + viewDist + " Slope: " + slope);
            }
            else if(map[(int)(yValue)][xValue].equals("000") || map[(int)(yValue)][xValue].equals(" 0 "))
            {
                //System.out.println("i - " + i + " Slope: " + slope);
                if((int)i < shortestIndex){shortestIndex = (int)i;}
            }
        }

        //y first calculation
        for(double i = 0; i < viewDist; i+=0.1)
        {
            //flip the counting of i if the slope is positive
            double i2 = i;
            if(slope > 0){i2 = i2 * flipMult;}

            //set up the square being checked for collision
            double lossXOffset = (double) i2/slope;
            double lossyX = (double) player.x() + lossXOffset;
            double yValue = player.y() + i2;
            double xValue = player.x() + lossyX;
            //check that you are in the bounds of the map
            if(yValue < 0 || xValue < 0 || yValue > map.length - 1 || xValue > map[(int)yValue].length - 1)
            {
                //System.out.println("alt stopped - " + viewDist + " Slope: " + slope);
            }
            else if(map[(int)(yValue)][(int)xValue].equals("000") || map[(int)(yValue)][(int)xValue].equals(" 0 "))
            {
                //System.out.println("alt i - " + i + " Slope: " + slope);
                //calculate distance
                Math.sqrt((xValue)*(xValue)+(yValue)*(yValue));
                if((int)i < shortestIndex){shortestIndex = (int)i;}
            }
        }
        //if(shortestIndex == viewDist){System.out.println("max - " + viewDist + " Slope: " + slope * flipMult);}
        return shortestIndex;
    }

    public void print()
    {
        String output = "";
        for(int y = 0; y < view.length - 1; y++)
        {
            for(int x = 0; x < view[y].length -1; x++)
            {
                // System.out.println("3");
                // System.out.println(x);
                // System.out.println(view[y].length -1);
                // if((int)(Math.random()*4) == 2){System.out.println("AAAAHAHAHAHAH");}
                output += view[y][x];
            }
            output += "\n";
        }
        System.out.println(output);
    }
}

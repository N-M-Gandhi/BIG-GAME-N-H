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
    private int height;
    private String[][] view;
    //private InputListener input;

    private static final Color BACKGROUND = Color.BLACK;
    private JFrame frame;
    private BufferedImage image;

    public Renderer(InputActivator input)
    {
        slices = 320;
        scale = 2;
        height = 320;
        view = new String[height * 1][slices * 1];
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
        image = new BufferedImage(slices*scale,height*scale,BufferedImage.TYPE_INT_RGB);
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
        height = v;
        view = new String[height * scale][slices * scale];
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
        //set up window
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(BACKGROUND);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();

        double viewIncrement = (double)90/(double)(slices-1); //how many degrees is ech slice from each other
        for(int i = 0; i < slices; i++)
        {
            double rayAngle = player.r() + 45 - viewIncrement * i; //the angle of this speciic raycast
            Vector2D collisionPoint = RayCast.cast(rayAngle, player, map); //returns collision Vector2D
            double rayLength = Math.sqrt(Math.pow(collisionPoint.x() - player.x(), 2) + Math.pow(collisionPoint.y() - player.y(), 2)); //the length of a casted ray from player position at rayAngle
            rayLength = rayLength * Math.cos(Math.toRadians(rayAngle - player.r())); //remove fish eye distortion
            int sliceLength = 0;
            if(height > (int)rayLength * height / 16 && (int)rayLength * height / 16 > 0)
            {sliceLength = (int)(height - rayLength * height / 16);} // calculate length of slice

            //fill slice with blank space so I dont get permanenet screen tarring
            for(int y = 0; y < view.length; y++)
            {
                //fill in ceiling
                if(y < view.length/2)
                {
                    graphics = image.createGraphics();
                    graphics.setColor(BACKGROUND);
                    graphics.fillRect(i*scale, y*scale, 1*scale, 1*scale);
                    graphics.dispose();
                }
                else
                {
                    //fill in floor
                    graphics = image.createGraphics();
                    graphics.setColor(new Color(128,128,128));
                    graphics.fillRect(i*scale, y*scale, 1*scale, 1*scale);
                    graphics.dispose();
                }
            }
            //fill a slice to said length with at desired length
            for(int y = view.length/2 - sliceLength/2; y <  view.length/2 + sliceLength/2; y++)
            {
                graphics = image.createGraphics();
                graphics.setColor(new Color(0,0,255));
                graphics.fillRect(i*scale, y*scale, 1*scale, 1*scale);
                graphics.dispose();
            }
        }   
        frame.repaint();
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

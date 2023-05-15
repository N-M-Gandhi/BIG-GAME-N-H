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
    private double wallHeight;
    //private InputListener input;

    private static final Color BACKGROUND = Color.BLACK;
    private JFrame frame;
    private BufferedImage image;

    public Renderer(InputActivator input)
    {
        slices = 320 * 2;
        scale = 2;
        height = 200 * 2;
        wallHeight = 3.80;

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

    public void render(Player player, int[][] map)
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
            CastInfo collisionPoint = RayCast.castLodev(rayAngle, player, map); //returns collision Vector2D
            double rayLength = Math.sqrt(Math.pow(collisionPoint.x() - player.x(), 2) 
                                        + Math.pow(collisionPoint.y() - player.y(), 2)); //the length of a casted ray from player position at rayAngle
            rayLength = rayLength * Math.cos(Math.toRadians(rayAngle - player.r())); //remove fish eye distortion
            //image size based off of distance formula
            //x/360 = (wall height)/((2 pi) * distance)
            double screenRatio = wallHeight/(2 * (Math.PI) * rayLength); //makes walls 3 meters high
            int screenScaledRayLength = (int)(height * screenRatio); //calculate length of slice
            int sliceLength = 0;
            if(screenScaledRayLength >= height){sliceLength = height -1;}//if greater than screen, slice length set to maximum
            if(screenScaledRayLength < height && (int)screenScaledRayLength >= 0)
            {sliceLength = (int)(screenScaledRayLength);}// set slicelength if within screen

            //fill slice with blank space so I dont get permanenet screen tarring
            for(int y = 0; y < height; y++)
            {
                //fill in ceiling
                if(y < height/2)
                {
                    graphics = image.createGraphics();
                    graphics.setColor(new Color(56, 56, 56));
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
            for(int y = height/2 - sliceLength/2; y <  height/2 + sliceLength/2; y++)
            {
                graphics = image.createGraphics();
                graphics.setColor(new Color(0,0,255));
                if(collisionPoint.getShade())
                {
                    graphics.setColor(new Color(0,0,200));//shade darker
                }
                graphics.fillRect(i*scale, y*scale, 1*scale, 1*scale);
                graphics.dispose();
            }
        }   
        frame.repaint();
    }
}

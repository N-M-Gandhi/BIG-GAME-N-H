import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
/**
 * Write a description of class MapRenderer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MapRenderer
{
    private int scale;
    private Map map;
    private Player player;

    private static final Color BACKGROUND = Color.WHITE;
    private JFrame frame;
    private BufferedImage image;

    /**
     * Constructor for objects of class MapRenderer
     */
    public MapRenderer(int s, InputActivator input, Map m, Player p)
    {
        scale = s;
        map = m;
        player = p;
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
        image = new BufferedImage(map.getMap().length*scale,map.getMap().length*scale,BufferedImage.TYPE_INT_RGB);
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

    public void render()
    {
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(BACKGROUND);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();

        //render walls
        for(int y = 0; y < map.getMap().length; y++)
        {
            for(int x = 0; x< map.getMap()[0].length; x++)
            {
                graphics = image.createGraphics();
                graphics.setColor(new Color(0,0,0));
                graphics.fillRect(x*scale + 1, y*scale + 1, 1*(scale-1), 1*(scale-1));
                graphics.dispose();
                if(map.getMap()[y][x].equals("1"))
                {
                    graphics = image.createGraphics();
                    graphics.setColor(new Color(0,0,255));
                    graphics.fillRect(x*scale + 1, y*scale + 1, 1*(scale-1), 1*(scale-1));;
                    graphics.dispose();
                }
            }
        }

        //render player
        graphics = image.createGraphics();
        graphics.setColor(new Color(255,0,0));
        graphics.fillRect((int)player.x()*scale + 1, (int)player.y()*scale + 1, 1*(scale-1), 1*(scale-1));
        graphics.dispose();

        //render rayCasts
        graphics = image.createGraphics();
        
        

        int feildOfVeiw = 90;
        int slices = 30;

        double viewIncrement = (double)feildOfVeiw/(double)(slices-1);
        for(int i = 0; i < slices; i++)
        {
            double rayAngle = player.r() + 45 - viewIncrement * i; //the angle of this speciic raycast
            double slope = Math.tan(Math.toRadians(rayAngle));//calculate slope of rayCast
            CastInfo collisionPoint = RayCast.cast(rayAngle, player, map.getMap()); //returns collision Vector2D
            double raycastDist = Math.sqrt(Math.pow(collisionPoint.x() - player.x(), 2) 
                                            + Math.pow(collisionPoint.y() - player.y(), 2));
            // CastInfo innitIntersect = RayCast.cast2(rayAngle, player, map.getMap()); //returns collision Vector2D
            // double innitDist = Math.sqrt(Math.pow(innitIntersect.x() - player.x(), 2) 
                                            // + Math.pow(innitIntersect.y() - player.y(), 2));
            //System.out.println(raycastDist);
            
            //draw line
            graphics.setColor(new Color(0,255,0));
            graphics.drawLine((int)(player.x() * scale), (int)(player.y() * scale), 
                            (int)(collisionPoint.x() * scale), (int)(collisionPoint.y() * scale));
            //draw line to innitial intersect
            // graphics.setColor(new Color(0,0,255));
            // graphics.drawLine((int)(player.x() * scale), (int)(player.y() * scale), 
                            // (int)(innitIntersect.x() * scale), (int)(innitIntersect.y() * scale));
        }
        graphics.dispose();
        
        frame.repaint();
    }
}

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

    private static final Color BACKGROUND = Color.BLACK;
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
                if(map.getMap()[y][x].equals("000") || map.getMap()[y][x].equals(" 0 "))
                {
                    graphics = image.createGraphics();
                    graphics.setColor(new Color(0,0,255));
                    graphics.fillRect(x*scale, y*scale, 1*scale, 1*scale);
                    graphics.dispose();
                }
            }
        }

        //render player
        graphics = image.createGraphics();
        graphics.setColor(new Color(255,0,0));
        graphics.fillRect((int)player.x()*scale, (int)player.y()*scale, 1*scale, 1*scale);
        graphics.dispose();

        //render rayCasts
        raycasts();

        frame.repaint();
    }

    // public void raycasts()
    // {
    // Graphics2D graphics = image.createGraphics();
    // graphics = image.createGraphics();
    // graphics.setColor(new Color(0,255,0));

    // int feildOfVeiw = 90;
    // int slices = 90;

    // double viewIncrement = (double)feildOfVeiw/(double)(slices-1);
    // for(int i = 0; i < slices; i++)
    // {
    // double rayAngle = player.r() + 45 - viewIncrement * i; //the angle of this speciic raycast
    // double slope = Math.tan(Math.toRadians(rayAngle));//calculate slope of rayCast
    // int stepDirection = 1;
    // if(rayAngle < 90){slope = slope * -1;}
    // if(rayAngle > 180 && rayAngle < 270){slope = slope * -1;}
    // Vector2D[] intersections = new Vector2D[2]; //set up the store of vertical and horizontal collisions
    // intersections[0] = new Vector2D(); intersections[1] = new Vector2D(); //make sure thery filled by default

    // //Vertical line collision
    // //find innitial vertical line intersection point
    // double HoriDistFromFirstVertLine = player.x() - ((int)player.x());//horizontal distance from first vertical line
    // double intersectHeight = slope * HoriDistFromFirstVertLine; //acts as an offset from the cell
    // //the player is currently in vertically
    // //this one will hit a vertivcl line eventually
    // for(int x = 0; x < 64; x++)
    // {
    // double y = (slope * x) + intersectHeight;
    // double realX = x * 1 + player.x();
    // double realY = y + player.y();
    // graphics.fillRect((int)(realX * scale), (int)(realY * scale), 1, 1);

    // if((int)realX < 0 || (int)realY < 0 || (int)realX > map.getMap().length - 1 || (int)realY > map.getMap()[0].length - 1)
    // {
    // //just do nothing so I don't get an out of bonds error
    // }
    // else if(map.getMap()[(int)realY][(int)realX].equals("000") || map.getMap()[(int)realY][(int)realX].equals(" 0 "))
    // {
    // //record wall intersection value and break
    // intersections[0] = new Vector2D(realX, realY);
    // break;
    // }
    // }

    // //horizontal line collision
    // //find the innitial horizontal line intersect point
    // double VertDistFromFirstHoriLine = player.y() - ((int)player.y());//vertical distance from first vertical line
    // double intersectBreadth = VertDistFromFirstHoriLine / slope; //acts as an offset from the cell
    // //the player is currently in horizontally
    // //this one will hit a horizontal line eventually
    // for(int y = 0; y < 64; y++)
    // {
    // double x = y / slope + intersectBreadth;
    // double realX = x * -1 + player.x();
    // double realY = y * -1 + player.y();
    // graphics.fillRect((int)(realX * scale), (int)(realY * scale), 1, 1);

    // if((int)realX < 0 || (int)realY < 0 || (int)realX > map.getMap().length - 1 || (int)realY > map.getMap()[0].length - 1)
    // {
    // //just do nothing so I don't get an out of bonds error
    // }
    // else if(map.getMap()[(int)realY][(int)realX].equals("000") || map.getMap()[(int)realY][(int)realX].equals(" 0 "))
    // {
    // //record wall intersection value and break
    // intersections[1] = new Vector2D(realX, realY);
    // break;
    // }
    // }
    // }
    // graphics.dispose();
    // }
    public void raycasts()
    {
        Graphics2D graphics = image.createGraphics();
        graphics = image.createGraphics();
        graphics.setColor(new Color(0,255,0));

        int feildOfVeiw = 90;
        int slices = 90;

        double viewIncrement = (double)feildOfVeiw/(double)(slices-1);
        for(int i = 0; i < slices; i++)
        {
            double rayAngle = player.r() + 45 - viewIncrement * i; //the angle of this speciic raycast
            double slope = Math.tan(Math.toRadians(rayAngle));//calculate slope of rayCast
            int firstXMult = 1;
            int firstYMult = 1;
            int secondXMult = 1;
            int secondYMult = 1;
            int thirdXMult = 1;
            int thirdYMult = 1;
            if(rayAngle <= 90)//fuckery
            {
                firstXMult = -1; secondXMult = 1; thirdXMult = 1;
                firstYMult = -1; secondYMult = -1; thirdYMult = -1;
            }
            else if(rayAngle > 90 && rayAngle <= 180)
            {
                firstXMult = 1; secondXMult = -1; thirdXMult = 1;
                firstYMult = -1; secondYMult = -1; thirdYMult = -1;
            }
            else if(rayAngle > 180 && rayAngle <= 270)
            {
                firstXMult = 1; secondXMult = -1; thirdXMult = 1;
                firstYMult = 1; secondYMult = -1; thirdYMult = 1;
            }
            else if(rayAngle > 270)
            {
                firstXMult = -1; secondXMult = 1; thirdXMult = 1;
                firstYMult = -1; secondYMult = 1; thirdYMult = 1;
            }
            Vector2D[] intersections = new Vector2D[2]; //set up the store of vertical and horizontal collisions
            intersections[0] = new Vector2D(); intersections[1] = new Vector2D(); //make sure thery filled by default

            //Vertical line collision
            //find innitial vertical line intersection point
            double HoriDistFromFirstVertLine = player.x() - ((int)player.x());//horizontal distance from first vertical line
            double intersectHeight = firstXMult * slope * HoriDistFromFirstVertLine; //acts as an offset from the cell
            //the player is currently in vertically
            //this one will hit a vertivcl line eventually
            for(int x = 0; x < 64; x++)
            {
                double y = firstXMult * (slope * x) + intersectHeight;
                double realX = secondXMult * x + player.x();
                double realY = thirdXMult * y + player.y();
                graphics.fillRect((int)(realX * scale), (int)(realY * scale), 1, 1);

                if((int)realX < 0 || (int)realY < 0 || (int)realX > map.getMap().length - 1 || (int)realY > map.getMap()[0].length - 1)
                {
                    //just do nothing so I don't get an out of bonds error
                }
                else if(map.getMap()[(int)realY][(int)realX].equals("000") || map.getMap()[(int)realY][(int)realX].equals(" 0 "))
                {
                    //record wall intersection value and break
                    intersections[0] = new Vector2D(realX, realY);
                    break;
                }
            }

            //horizontal line collision
            //find the innitial horizontal line intersect point
            double VertDistFromFirstHoriLine = player.y() - ((int)player.y());//vertical distance from first vertical line
            double intersectBreadth = firstYMult * VertDistFromFirstHoriLine / slope; //acts as an offset from the cell
            //the player is currently in horizontally
            //this one will hit a horizontal line eventually
            for(int y = 0; y < 64; y++)
            {
                double x = firstYMult * (y / slope) + intersectBreadth;
                double realX = secondYMult * 1 * x + player.x();
                double realY = thirdYMult * y + player.y();
                graphics.fillRect((int)(realX * scale), (int)(realY * scale), 1, 1);

                if((int)realX < 0 || (int)realY < 0 || (int)realX > map.getMap().length - 1 || (int)realY > map.getMap()[0].length - 1)
                {
                    //just do nothing so I don't get an out of bonds error
                }
                else if(map.getMap()[(int)realY][(int)realX].equals("000") || map.getMap()[(int)realY][(int)realX].equals(" 0 "))
                {
                    //record wall intersection value and break
                    intersections[1] = new Vector2D(realX, realY);
                    break;
                }
            }
        }
        graphics.dispose();
    }
}

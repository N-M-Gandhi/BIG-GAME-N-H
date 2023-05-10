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
            int sliceLength = height - rayCast(slope, player, map);
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

    public void render2(Player player, String[][] map)
    {
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(BACKGROUND);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();
        Vector2D[] rayPosition = new Vector2D[slices];
        for(int i = 0; i < slices; i++)
        {
            rayPosition[i] = new Vector2D(player.x(), player.y());
            //calculate the angle of this slice
            double angle = player.r() - 90 / 2 + ((double)90 / slices) * i;
            //calculate direction
            Vector2D direction = new Vector2D(Math.cos(angle * 0.0174533) / 100, Math.cos(angle * 0.0174533) / 100);
            //increment ad infinitum
            for(int j = 0; j < 9999; j ++)
            {
                rayPosition[i].add(direction);
                if((int)rayPosition[i].y() < 0 || (int)rayPosition[i].x() < 0 || (int)rayPosition[i].y() > map.length - 1 || 
                (int)rayPosition[i].x() > map[0].length - 1)
                {
                    rayPosition[i] = new Vector2D(9999, 9999);
                }
                else if (map[(int)(rayPosition[i].y())][(int)rayPosition[i].x()].equals("000") || 
                map[(int)(rayPosition[i].y())][(int)rayPosition[i].x()].equals(" 0 "))
                {
                    break;
                }
            }
            //find distance
            double xValue = rayPosition[i].x() - player.x();
            double yValue = rayPosition[i].y() - player.y();
            double distance = Math.sqrt(Math.abs((xValue)*(xValue) + (yValue)*(yValue)));
            //calculate the length of this particular slice
            int sliceLength = 0;
            if(height > (int)distance){sliceLength = height - (int)distance * (int)((double)height/64);}

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

    public void render3(Player player, String[][] map)
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
            double rayLength = rayCast2(rayAngle, player, map); //the length of a casted ray from player position at rayAngle
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

    public double rayCast2(double rayAngle, Player player, String[][] map) //returns distance of first collision
    {
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
        //if(rayAngle > 180 && rayAngle < 270){stepDirection = -1;}
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
            if((int)realX < 0 || (int)realY < 0 || (int)realX > map.length - 1 || (int)realY > map[0].length - 1)
            {
                //just do nothing so I don't get an out of bonds error
                break;
            }
            else if(map[(int)realY][(int)realX].equals("000") || map[(int)realY][(int)realX].equals(" 0 "))
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
            double x = firstYMult * y / slope + intersectBreadth;
            double realX = secondYMult * x + player.x();
            double realY = thirdYMult * y + player.y();
            if((int)realX < 0 || (int)realY < 0 || (int)realX > map.length - 1 || (int)realY > map[0].length - 1)
            {
                //just do nothing so I don't get an out of bonds error
                break;
            }
            else if(map[(int)realY][(int)realX].equals("000") || map[(int)realY][(int)realX].equals(" 0 "))
            {
                //record wall intersection value and break
                intersections[1] = new Vector2D(realX, realY);
                break;
            }
        }

        //calculate which intersection point is closest using distance formula
        double verticalLineAlgorithmDistance = Math.sqrt(Math.pow(intersections[0].x(), 2) + Math.pow(intersections[0].y(), 2));
        double horizontalLineAlgorithmDistance = Math.sqrt(Math.pow(intersections[1].x(), 2) + Math.pow(intersections[1].y(), 2));
        if(verticalLineAlgorithmDistance < horizontalLineAlgorithmDistance)
        {
            return verticalLineAlgorithmDistance;
        }
        else
        {
            return horizontalLineAlgorithmDistance;
        }
    }

    public int rayCast(double slope, Player player, String[][] map)
    {
        int shortestIndex = height;
        int flipMult = 1;
        if(slope > 0){slope = slope * -1;flipMult = -1;}
        //increment until hit a wall or reach view limit
        //x fisrt calculation
        for(double i = 0; i < height; i+=0.1)
        {
            //flip the counting of i if the slope is positive
            double i2 = i * flipMult;

            //set up the square being checked for collision
            double lossYOffset = (double) i2 * slope;
            double lossyY = (double) player.y() + lossYOffset;
            int yValue = (int)(lossyY);
            int xValue = (int)(player.x() + i2);
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
        for(double i = 0; i < height; i+=0.1)
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

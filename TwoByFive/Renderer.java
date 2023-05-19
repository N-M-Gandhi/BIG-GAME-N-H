import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.ArrayList;
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
    private ImageReader imageReader;
    private ArrayList<Sprite> spriteList;
    private int FOV;
    //private InputListener input;

    private static final Color BACKGROUND = Color.BLACK;
    private JFrame frame;
    private BufferedImage image;

    public Renderer(InputActivator input)
    {
        slices = 320;
        scale = 3;
        height = 200;
        wallHeight = 3.80;
        FOV = 90;
        imageReader = new ImageReader();
        imageReader.cacheImages();
        spriteList = new ArrayList<Sprite>();
        spriteList.add(new Sprite(new Vector2D(29, 29), 9));
        spriteList.add(new Sprite(new Vector2D(12, 19), 8));

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
        //render 3D stuff
        renderWalls(player, map, graphics);
        //render sprites
        //renderSprites(player, map, graphics);
        //render weapon
        renderUI(player, map, graphics);

        graphics.dispose();
        frame.repaint();
    }

    private void renderSprites(Player player, int[][] map, Graphics2D graphics, double[] rayDistances)
    {
        for(int i = 0; i < spriteList.size(); i++)
        {
            Sprite sprite = spriteList.get(i);
            double angle = Math.toDegrees(sprite.getAngleFrom(player.getVector2D())); //returns angle from player to sprite in degrees
            double distance = sprite.getDistance(player.getVector2D());
            double xScale = 1 - ((angle - (player.r() - FOV/2)) / FOV);
            int xOnScreen = (int) (xScale * slices);
            if(angle < player.r() + FOV/2 && angle > player.r() - FOV/2 && rayDistances[xOnScreen] > distance)
            {
                int imageNumber = sprite.getImageNumber();
                int imageHeight = imageReader.getHeight(imageNumber);
                int imageWidth = imageReader.getWidth(imageNumber);
                int midPoint = imageWidth/2;
                double doubleHeight = (imageHeight/4)/(2 * (Math.PI) * distance); // 64 pixels = 1 meter 
                double doubleWidth = (imageWidth/4)/(2 * (Math.PI) * distance);
                int intHeight = (int)(imageHeight * doubleHeight);
                int intWidth = (int)(imageWidth * doubleWidth);
                //System.out.println(intHeight);
                //System.out.println(intWidth);
                for(int y = height/2 - intHeight/2; y <  height/2 + intHeight/2; y++)
                {
                    double imageScalerY = (double)(y - height/2 + intHeight/2)/intHeight; //System.out.println("scalerY " + (y - height/2 + intHeight/2) + "/" + intHeight + "=" + imageScalerY);
                    int imageY = (int)(imageHeight * imageScalerY); //System.out.println("y " + imageY);
                    for(int x = midPoint - intWidth/2; x < midPoint + intWidth/2; x++)
                    {
                        double imageScalerX = (double)(x - midPoint + intWidth/2)/intWidth; //System.out.println("scalerX " + (x - midPoint + intWidth/2) + "/" + intWidth + "=" + imageScalerX);
                        int imageX = (int)(imageWidth * imageScalerX); //System.out.println("x " + imageX);
                        Color color = imageReader.getColor(imageX, imageY, imageNumber);
                        if(!color.equals(new Color(255, 0, 255)))
                        {
                            graphics.setColor(color);
                            graphics.fillRect((xOnScreen + x)*scale, y*scale, 1*scale, 1*scale);
                        }
                    }
                }
            }
        }
    }

    private void renderUI(Player player, int[][] map, Graphics2D graphics)
    {
        for(int x = 120; x < 200; x++)
        {
            for(int y = 120; y < 200; y++)
            {
                Color color = imageReader.getColor(x - 120, y - 120, 7);
                if(!color.equals(new Color(255, 0, 255)))
                {
                    graphics.setColor(color);
                    graphics.fillRect(x*scale, y*scale, 1*scale, 1*scale);
                }
            }
        }
    }

    private void renderWalls(Player player, int[][] map, Graphics2D graphics)
    {
        double[] distance = new double[slices];
        double viewIncrement = (double)FOV/(double)(slices-1); //how many degrees is ech slice from each other
        for(int i = 0; i < slices; i++)
        {
            double rayAngle = player.r() + FOV/2 - viewIncrement * i; //the angle of this speciic raycast
            CastInfo collisionPoint = RayCast.castLodev(rayAngle, player, map); //returns collision Vector2D
            double rayLength = Math.sqrt(Math.pow(collisionPoint.x() - player.x(), 2) 
                    + Math.pow(collisionPoint.y() - player.y(), 2)); //the length of a casted ray from player position at rayAngle
            distance[i] = rayLength;//stow this value for comparint to sprite
            rayLength = rayLength * Math.cos(Math.toRadians(rayAngle - player.r())); //remove fish eye distortion
            //image size based off of distance formula
            //x/360 = (wall height)/((2 pi) * distance)
            double screenRatio = wallHeight/(2 * (Math.PI) * rayLength); //makes walls 3.8 meters high
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
                    graphics.setColor(new Color(56, 56, 56));
                    graphics.fillRect(i*scale, y*scale, 1*scale, 1*scale);
                }
                else
                {
                    //fill in floor
                    graphics.setColor(new Color(128,128,128));
                    graphics.fillRect(i*scale, y*scale, 1*scale, 1*scale);
                }
            }
            //fill a slice to said length with at desired length
            int wallType = collisionPoint.getWall();
            double imageHeight = imageReader.getHeight(wallType);
            double imageWidth = imageReader.getWidth(wallType);
            double pixelScaler = 0;
            if(!collisionPoint.getShade())
            {
                pixelScaler = collisionPoint.x() - (int)collisionPoint.x();
            }
            else
            {
                pixelScaler = collisionPoint.y() - (int)collisionPoint.y();
            }
            //System.out.println(pixelScaler);
            int imageX = (int)(imageWidth * pixelScaler); //System.out.println(imageX);
            for(int y = height/2 - sliceLength/2; y <  height/2 + sliceLength/2; y++)
            {
                double sliceScaler = (double)(y - height/2 + sliceLength/2)/sliceLength; //System.out.println("scaler " + (y - height/2 + sliceLength/2) + "/" + sliceLength + "=" + sliceScaler);
                int imageY = (int)(imageHeight * sliceScaler); //System.out.println("y " + imageY);
                Color color = imageReader.getColor(imageX, imageY, wallType);
                if(collisionPoint.getShade())
                {
                    graphics.setColor(color.darker());//shade darker
                }
                else
                {
                    graphics.setColor(color);
                }
                graphics.fillRect(i*scale, y*scale, 1*scale, 1*scale);
            }
        }   
        renderSprites(player, map, graphics, distance);
    }
}

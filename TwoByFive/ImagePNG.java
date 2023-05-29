import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import java.util.Locale;
import java.awt.Color;
import java.util.*;
import java.awt.Color;
/**
 * Write a description of class ImagePNG here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ImagePNG
{
    public int height;
    public int width;
    BufferedImage image;
    public ImagePNG()
    {
        
    }
    public ImagePNG(String name)
    {
        try{
        image = ImageIO.read(new File(name));
        height = image.getHeight();
        width = image.getWidth();
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: image in "+name+" too big");
        } catch(FileNotFoundException e) {
            System.out.println("Error: file "+name+" not found");
        } catch(IOException e) {
            System.out.println("Error: end of stream encountered when reading "+name);
        }
    }
    public Color getColor(int x, int y)
    {
        //System.out.println(x + ", " + y);
        return new Color(image.getRGB(x, y));
    }
    public int height()
    {
        return height;
    }
    public int width()
    {
        return width;
    }
}

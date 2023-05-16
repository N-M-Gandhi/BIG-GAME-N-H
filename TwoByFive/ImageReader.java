import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.*;

public class ImageReader
{
    int length;
    int height;
    ImagePPM image1;
    ImagePPM image2;
    ImagePPM image3;
    ImagePPM image4;
    ImagePPM image5;
    public ImageReader()
    {
        length = 64;
        height = 64;
    }
    public Color getColor(int x, int y, int number)
    {
        String name = "";
        switch(number)
        {
            case 1: return image1.getColor(x, y);
        }
        return new Color(255, 192, 203);//if nothing mathces
    }
    public void cacheImages()
    {
        image1 = new ImagePPM();
        image1.ReadPPM("Multibrick.ppm");
    }
    public int getLength()
    {
        return length;
    }
    public int getHeight()
    {
        return height;
    }
}

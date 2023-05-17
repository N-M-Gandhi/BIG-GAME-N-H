import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.*;

public class ImageReader
{
    ImagePNG image1;
    ImagePNG image2;
    ImagePNG image3;
    ImagePNG image4;
    ImagePNG image5;
    ImagePNG image6;
    ImagePNG image7;
    public ImageReader()
    {
    }

    public Color getColor(int x, int y, int number)
    {
        switch(number)
        {
            case 1: return image1.getColor(x, y);
            case 2: return image2.getColor(x, y);
            case 7: return image7.getColor(x, y);
        }
        return new Color(255, 192, 203);//if nothing mathces
    }

    public void cacheImages()
    {
        image1 = new ImagePNG("multibrick.png");
        image2 = new ImagePNG("nazi_banner.png");
        image7 = new ImagePNG("pistol_model.png");
    }

    public int getWidth(int number)
    {
        switch(number)
        {
            case 1: return image1.width();
            case 2: return image2.width();
            case 7: return image7.width();
        }
        return 320;
    }

    public int getHeight(int number)
    {
        switch(number)
        {
            case 1: return image1.height();
            case 2: return image2.height();
            case 7: return image7.height();
        }
        return 200;
    }
}

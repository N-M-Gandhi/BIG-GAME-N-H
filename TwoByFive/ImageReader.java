import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.*;
import java.util.ArrayList;

public class ImageReader
{
    ArrayList<ImagePNG> imageList;
    public ImageReader()
    {
        imageList = new ArrayList<ImagePNG>();
    }

    public Color getColor(int x, int y, int number)
    {
        return imageList.get(number).getColor(x, y);
    }

    public void cacheImages()
    {
        for(int i = 0; i < 128; i++){imageList.add(new ImagePNG());}//fills ArrayList with empt ImagePNG objects to later be set to real images
        imageList.set(1, new ImagePNG("multibrick.png"));
        imageList.set(2, new ImagePNG("nazi_banner.png"));
        imageList.set(7, new ImagePNG("pistol_model.png"));
        imageList.set(9, new ImagePNG("fortnite_burger.png"));
        imageList.set(8, new ImagePNG("gunmanstand.png"));
    }

    public int getWidth(int number)
    {
        return imageList.get(number).width();
    }

    public int getHeight(int number)
    {
        return imageList.get(number).height();
    }
}

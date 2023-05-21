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
        imageList.set(7, new ImagePNG("wolf_pistol.png"));
        imageList.set(9, new ImagePNG("fortnite_burger.png"));
        imageList.set(8, new ImagePNG("gunmanstand.png"));
        imageList.set(10, new ImagePNG("wolf_gaurd.png"));
        imageList.set(11, new ImagePNG("wolf_pistol_fire.png"));
        imageList.set(12, new ImagePNG("wolf_gaurd_dead.png"));
        imageList.set(13, new ImagePNG("slimeinator.png"));
        imageList.set(14, new ImagePNG("slimeinator_fire.png"));
        imageList.set(15, new ImagePNG("wolf_gaurd_shot.png"));
        imageList.set(16, new ImagePNG("wolf_gaurd_ready.png"));
        imageList.set(17, new ImagePNG("wolf_gaurd_shooting.png"));
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

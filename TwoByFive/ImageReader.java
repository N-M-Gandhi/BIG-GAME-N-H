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
        //imageList.set(2, new ImagePNG("nazi_banner.png"));
        //imageList.set(7, new ImagePNG("wolf_pistol.png"));
        imageList.set(0, new ImagePNG("default_texture.png"));
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
        imageList.set(18, new ImagePNG("death_screen.png"));
        imageList.set(19, new ImagePNG("maxhp.png"));
        imageList.set(20, new ImagePNG("34hp.png"));
        imageList.set(21, new ImagePNG("24hp.png"));
        imageList.set(22, new ImagePNG("minhp.png"));
        imageList.set(23, new ImagePNG("medical_pickup.png"));
        imageList.set(24, new ImagePNG("you_died.png"));
        imageList.set(25, new ImagePNG("map_1.png"));
        imageList.set(26, new ImagePNG("school_chair.png"));
        imageList.set(27, new ImagePNG("hatsune_miku.png"));
        imageList.set(28, new ImagePNG("wood_table.png"));
        imageList.set(29, new ImagePNG("teleprompter.png"));
        imageList.set(34, new ImagePNG("prisonmap.png"));
        //wall textures
        imageList.set(1, new ImagePNG("multibrick.png"));
        imageList.set(2, new ImagePNG("brick_wall.png"));
        imageList.set(3, new ImagePNG("steel_wall.png"));
        imageList.set(4, new ImagePNG("wood_wall.png"));
        imageList.set(5, new ImagePNG("tile_wall.png"));
        imageList.set(6, new ImagePNG("book_wall.png"));
        imageList.set(7, new ImagePNG("blue_wall.png"));
        //klaus textures
        imageList.set(30, new ImagePNG("klauss.png"));
        imageList.set(31, new ImagePNG("klauss_dead.png"));
        imageList.set(32, new ImagePNG("klauss_fire.png"));
        imageList.set(33, new ImagePNG("klauss_hit.png"));
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

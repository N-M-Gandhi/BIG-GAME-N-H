import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.*;

public class ImageReader
{
    public static Color getColor(int x, int y, int number)
    {
        String name = "";
        switch(number)
        {
            case 1: name = "Multibrick.ppm";
        }
        return fetch(x, y, name);
    }
    public static Color fetch(int x, int y, String name)
    {
        File input = new File("name");
        return new Color(0, 0, 0);
    }
}

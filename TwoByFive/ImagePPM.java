import java.io.*;
import java.util.*;
import java.awt.Color;

public class ImagePPM{
    public int [][][] pixels;
    public int depth,width,height;

    public ImagePPM(){
        pixels = new int[3][128][128];
        depth = width = height = 0;}

    public ImagePPM(int inDepth, int inWidth, int inHeight){
        pixels = new int[3][inWidth][inHeight];
        width = inWidth;
        height = inHeight;
        depth = inDepth;}

    public int[][][] getPixels()
    {
        return pixels;
    }

    public Color getColor(int x, int y)
    {
        return new Color(pixels[0][x][y], pixels[1][x][y], pixels[2][x][y]);
    }

    public void ReadPPM(String fileName){
        String line;
        StringTokenizer st;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                        new BufferedInputStream(
                            new FileInputStream(fileName))));

            DataInputStream in2 =
                new DataInputStream(
                    new BufferedInputStream(
                        new FileInputStream(fileName)));

            // read PPM image header

            // skip comments
            line = in.readLine();
            in2.skip((line+"\n").getBytes().length);
            do {
                line = in.readLine();
                in2.skip((line+"\n").getBytes().length);
            } while (line.charAt(0) == '#');

            // the current line has dimensions
            st = new StringTokenizer(line);
            width = Integer.parseInt(st.nextToken());
            height = Integer.parseInt(st.nextToken());

            // next line has pixel depth
            line = in.readLine();
            in2.skip((line+"\n").getBytes().length);
            st = new StringTokenizer(line);
            depth = Integer.parseInt(st.nextToken());

            // read pixels now
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    //System.out.print(" (");
                    for (int i = 0; i < 3; i++)
                    {
                        pixels[i][x][y] = in2.readUnsignedByte(); System.out.println(" " + pixels[i][x][y]);
                    }
                    //System.out.print(")");
                }
                //System.out.println();
            }

            in.close();
            in2.close();
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: image in "+fileName+" too big");
        } catch(FileNotFoundException e) {
            System.out.println("Error: file "+fileName+" not found");
        } catch(IOException e) {
            System.out.println("Error: end of stream encountered when reading "+fileName);
        }
    }
}

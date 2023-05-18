
/**
 * Write a description of class Wall here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Wall
{
    private int length;
    private boolean horizontal;
    private int xPos;
    private int yPos;
    public Wall()
    {
        length = 1;
        horizontal = true;
        xPos = 0;
        yPos = 0;
    }
    public Wall(int l, boolean h, int x, int y, int[][] map)
    {
        //set up variables
        length = l;
        horizontal = h;
        xPos = x;
        yPos = y;
        
        //make sure they dont go too far
        if(xPos < 0){xPos = 0;}
        if(yPos < 0){yPos = 0;}
        if(xPos + length > map.length - 1 && horizontal){length = map.length - x;}
        if(yPos + length > map.length - 1 && !horizontal){length = map.length - y;}
        
        //actually place it
        if(horizontal)
        {
            for(int i = xPos; i < xPos + length; i++)
            {
                map[yPos][i] = 1;
                //if((int)(Math.random() * 25) == 0){map[yPos][i] = 2;}
            }
        }
        if(!horizontal)
        {
            for(int i = yPos; i < yPos + length; i++)
            {
                map[i][xPos] = 1;
            }
        }
    }
}

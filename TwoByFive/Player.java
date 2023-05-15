
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    private double xPos;
    private double yPos;
    private double rot;
    private String playerChar;
    public Player()
    {
        xPos = 0;
        yPos = 0;
        rot = 0;
        playerChar = ">┐^┌<└v┘> ";
    }
    public Player(double x, double y, double r)
    {
        xPos = x;
        yPos = y;
        rot = r;
        playerChar = ">┐^┌<└v┘>";
    }
    public double x()
    {
        //returns current x position
        return xPos;
    }
    public void x(double x)
    {
        //sets x position
        xPos = x;
    }
    public void moveX(double dist, int map[][])
    {
        map[(int)this.y()][(int)this.x()] = 0;
        if(!(xPos + 1 > map.length - 1) && !(xPos + 1 < 0) && 
        !(map[(int)this.y()][(int)this.x() + 1] > 0))
        {
            if(dist > 0){xPos = xPos + dist;}
        }
        if(!(xPos - 1 > map.length - 1) && !(xPos - 1 < 0) && 
        !(map[(int)this.y()][(int)this.x() - 1] > 0))
        {
            if(dist < 0){xPos = xPos + dist;}
        }
        //map[(int)this.y()][(int)this.x()] = " " + playerChar.substring(((int)rot/45), (((int)rot/45)+1)) + " ";
    }
    public double y()
    {
        //returns current x position
        return yPos;
    }
    public void y(int y)
    {
        //sets x position
        yPos = y;
    }
    public void moveY(double dist, int map[][])
    {
        map[(int)this.y()][(int)this.x()] = 0;
        if(!(yPos + 1 > map.length - 1) && !(yPos + 1 < 0) && 
        !(map[(int)this.y() + 1][(int)this.x()] > 0))
        {
            if(dist > 0){yPos = yPos + dist;}
        }
        if(!(yPos - 1 > map.length - 1) && !(yPos - 1 < 0) && 
        !(map[(int)this.y() - 1][(int)this.x()] > 0))
        {
            if(dist < 0){yPos = yPos + dist;}
        }
        //map[(int)this.y()][(int)this.x()] = " " + playerChar.substring(((int)rot/45), (((int)rot/45)+1)) + " ";
    }
    public double r()
    {
        //returns current x position
        return rot;
    }
    public void r(double r)
    {
        //sets x position
        rot = r;
    }
    public void transR(double rotation, int[][] map)
    {
        rot = rot + rotation;
        if(rot < 0)
        {
            rot = rot + 360;
        }
        if(rot > 360)
        {
            rot = rot - 360;
        }
        //map[(int)this.y()][(int)this.x()] = " " + playerChar.substring(((int)rot/45), (((int)rot/45)+1)) + " ";
    }
}

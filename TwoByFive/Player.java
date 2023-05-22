
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

    private boolean wPressed;
    private boolean aPressed;
    private boolean sPressed;
    private boolean dPressed;
    private boolean qPressed;
    private boolean ePressed;

    private double metersPerTick;
    private double anglesPerTick;
    private int lastX;
    private int moveDistance;
    
    private int health;

    public Player()
    {
        xPos = 0;
        yPos = 0;
        rot = 0;
        playerChar = ">┐^┌<└v┘> ";
        metersPerTick = 0.1;
        anglesPerTick = 3;
        lastX = 0;
        health = 15;
    }

    public Player(double x, double y, double r)
    {
        xPos = x;
        yPos = y;
        rot = r;
        playerChar = ">┐^┌<└v┘>";
        metersPerTick = 0.1;
        anglesPerTick = 3;
        lastX = 0;
        health = 15;
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

    public double getRadians()
    {
        return Math.toRadians(rot);
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
    
    public void damage(int d)
    {
        health-=d;
    }
    
    public int getHealth()
    {
        return health;
    }
    
    public void addHealth(int i)
    {
        health+=i;
    }

    public Vector2D getVector2D()
    {
        return new Vector2D(xPos, yPos);
    }

    public void wPressed(boolean isPressed) //there has to be a btter way but IDK it
    {
        wPressed = isPressed;
    }

    public void aPressed(boolean isPressed)
    {
        aPressed = isPressed;
    }

    public void sPressed(boolean isPressed)
    {
        sPressed = isPressed;
    }

    public void dPressed(boolean isPressed)
    {
        dPressed = isPressed;
    }
    
    public void qPressed(boolean isPressed)
    {
        qPressed = isPressed;
    }
    public void ePressed(boolean isPressed)
    {
        ePressed = isPressed;
    }
    
    //native java mouse tracking sucks for games; requires a library to work well
    public void mouseX(int x) // handles mouse input
    {
        moveDistance = lastX - x;
        lastX = x;
    }

    public void tick(Map map)
    {
        double distX = (Math.cos(Math.toRadians(this.r())));
        double distY = (Math.sin(Math.toRadians(this.r())));
        if(wPressed)
        {
            this.moveX(distX * metersPerTick, map.getMap());
            this.moveY(distY * -metersPerTick, map.getMap());
        }
        if(sPressed)
        {
            this.moveX(distX * -metersPerTick, map.getMap());
            this.moveY(distY * metersPerTick, map.getMap());
        }
        if(aPressed)
        {
            this.moveX(distY * -metersPerTick, map.getMap());
            this.moveY(distX * -metersPerTick, map.getMap());
        }
        if(dPressed)
        {
            this.moveX(distY * metersPerTick, map.getMap());
            this.moveY(distX * metersPerTick, map.getMap());
        }
        if(qPressed)
        {
            this.transR(anglesPerTick, map.getMap());
        }
        if(ePressed)
        {
            this.transR(-anglesPerTick, map.getMap());
        }
        
        if(moveDistance != 0) //native java mouse tracking sucks for games
        {
            this.transR(moveDistance, map.getMap());
            moveDistance = 0;
        }
    }
}

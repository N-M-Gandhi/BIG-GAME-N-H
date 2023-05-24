
/**
 * Write a description of class CastInfo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CastInfo
{
    private Vector2D position;
    private int wallType;
    private boolean shade;
    
    public CastInfo()
    {
        position = new Vector2D();
        wallType = 0;
        shade = false;
    }
    public CastInfo(Vector2D p, int w, boolean s)
    {
        position = p;
        wallType = w;
        shade = s;
    }
    public double x()
    {
        return position.x();
    }
    public double y()
    {
        return position.y();
    }
    public Vector2D getPosition()
    {
        return position;
    }
    public int getWall()
    {
        return wallType;
    }
    public boolean getShade()
    {
        return shade;
    }
    public void setInfo(Vector2D p, int w, boolean s)
    {
        position = p;
        wallType = w;
        shade = s;
    }
    public void setPosition(Vector2D p)
    {
        position = p;
    }
    public void setWall(int w)
    {
        wallType = w;
    }
    public void setShade(boolean s)
    {
        shade = s;
    }
}

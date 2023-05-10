
/**
 * Write a description of class Vector2D here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Vector2D
{
    private double x;
    private double y;
    
    public Vector2D()
    {
        x = 0; //what are numerical values normally set as  by default?
        y = 0; //cant set them as null!
    }
    public Vector2D(double ex, double ey)
    {
        x = ex;
        y = ey;
    }
    public double x()
    {
        return x;
    }
    public double y()
    {
        return y;
    }
    public void x(double ex)
    {
        x = ex;
    }
    public void y(double ey)
    {
        y = ey;
    }
    public void add(Vector2D other)
    {
        this.x(this.x() + other.x());
        this.y(this.y() + other.y());
    }
}

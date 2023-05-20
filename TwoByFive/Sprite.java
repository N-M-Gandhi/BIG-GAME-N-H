
/**
 * Write a description of class sprite here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Sprite extends Vector2D
{
    private int imageNumber;
    public Sprite()
    {
        super(new Vector2D(0, 0));
        imageNumber = 10;
    }
    public Sprite(Vector2D v, int number)
    {
        super(v);
        imageNumber = number;
    }

    public double getDistance(Vector2D v)//returns distance from this sprite to the other vector
    {
        return  Math.sqrt(Math.pow(v.x() - this.x(), 2) + Math.pow(v.y() - this.y(), 2));
    }

    public double getAngleFrom(Vector2D v)//returns the angle from the other vector to this vector
    {
        double x = this.x() - v.x();
        double y = v.y() - this.y();
        double angle = Math.atan2(y, x);

        if (angle < 0) {
            angle += 2 * Math.PI; // Adjust the angle to be between 0 and 2*pi
        }

        return angle;
    }

    public int getImageNumber()
    {
        return imageNumber;
    }
    
    public void setImageNumber(int n)
    {
        imageNumber = n;
    }
}

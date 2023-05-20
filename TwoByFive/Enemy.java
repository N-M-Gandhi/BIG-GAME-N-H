
/**
 * Write a description of class Enemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Enemy extends Sprite
{
    public boolean dead;
    int deadImageNumber;
    int health = 3;
    public Enemy()
    {
        super();
        deadImageNumber = 1;
    }

    public Enemy(Vector2D v, int number, int deadNumber)
    {
        super(v, number);
        deadImageNumber = deadNumber;
    }

    public void shot()
    {
        if(!dead)
        {
            health--;
            if(health < 1)
            {
                dead = true;
                this.setImageNumber(deadImageNumber);
            }
        }
    }
}

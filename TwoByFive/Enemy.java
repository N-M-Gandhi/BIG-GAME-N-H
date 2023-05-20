
/**
 * Write a description of class Enemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Enemy extends Sprite
{
    public boolean dead;
    public boolean shot;
    int normImageNumber;
    int deadImageNumber;
    int shotImageNumber;
    int health = 3;
    private int HIT_COOLDOWN;
    private int hitCooldown;
    public Enemy()
    {
        super();
        deadImageNumber = 1;
        shotImageNumber = 2;
        HIT_COOLDOWN = 7;
    }

    public Enemy(Vector2D v, int number, int shotNumber, int deadNumber)
    {
        super(v, number);
        normImageNumber = number;
        deadImageNumber = deadNumber;
        shotImageNumber = shotNumber;
        HIT_COOLDOWN = 7;
    }

    public void shot()
    {
        if(!dead)
        {
            health--;
            hitCooldown = HIT_COOLDOWN;
            this.setImageNumber(shotImageNumber);
            if(health < 1)
            {
                dead = true;
                this.setImageNumber(deadImageNumber);
            }
        }
    }
    
    public void tick()
    {
        if(hitCooldown > 0)
        {
            hitCooldown--;
        }
        else if(!dead)
        {
            this.setImageNumber(normImageNumber);
        }
    }
}

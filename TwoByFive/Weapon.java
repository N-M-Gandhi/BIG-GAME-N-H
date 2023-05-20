import java.util.ArrayList;
/**
 * Write a description of class weapon here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Weapon
{
    private int ammo;
    private int imageNumber;
    private int fireNumber;
    boolean fire;
    private int FLASH_COOLDOWN;
    private int flashCooldown;
    private int SHOOT_COOLDOWN;
    private int shootCooldown;
    private double fireCone;

    /**
     * Constructor for objects of class weapon
     */
    public Weapon()
    {
        imageNumber = 7;
        fireNumber = 11;
        fire = false;
        FLASH_COOLDOWN = 5;
        SHOOT_COOLDOWN = 15;
        fireCone = Math.toRadians(20);
    }

    public Weapon(int n, int f)
    {
        imageNumber = n;
        fireNumber = f;
        fire = false;
        FLASH_COOLDOWN = 5;
        SHOOT_COOLDOWN = 10;
        fireCone = Math.toRadians(20);
    }

    public int getImageNumber()
    {
        if(!fire)
        {
            return imageNumber;
        }
        else
        {
            return fireNumber;
        }
    }

    public void fire(boolean f, Player player, Map map)
    {
        if(shootCooldown == 0)
        {
            fire = f;
            flashCooldown = FLASH_COOLDOWN;
            shootCooldown = SHOOT_COOLDOWN;
            calcHits(player, map);
        }
    }

    private void calcHits(Player player, Map map)
    {
        ArrayList<Sprite> spriteList = map.getSpriteList();
        for(int i = 0; i < spriteList.size(); i++)
        {
            Sprite sprite = spriteList.get(i);
            if(sprite instanceof Enemy)
            {
                Enemy enemy = (Enemy)sprite;
                double angle = sprite.getAngleFrom(player.getVector2D());
                double distance = sprite.getDistance(player.getVector2D());
                if(angle > player.getRadians() - fireCone/2 && angle < player.getRadians() + fireCone/2 && distance < 2)
                {
                    enemy.shot();
                }
            }
        }
    }

    public void tick()
    {
        if(flashCooldown > 0)
        {
            flashCooldown--;
        }
        else
        {
            fire = false;
        }
        if(shootCooldown > 0)
        {
            shootCooldown--;
        }
    }
}

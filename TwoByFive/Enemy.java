
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
    public boolean alert;
    int normImage;
    int deadImage;
    int shotImage;
    int readyImage;
    int shootingImage;
    int health = 3;
    private int HIT_COOLDOWN;
    private int hitCooldown;
    private int SHOOT_COOLDOWN;
    private int shootCooldown;
    private int CHECKALERT_COOLDOWN;
    private int alertCooldown;
    AudioManager audioManager;
    
    //sounds
    public Enemy()
    {
        super();
        deadImage = 1;
        shotImage = 2;
        HIT_COOLDOWN = 11;
        audioManager = new AudioManager();
        alert = false;
    }

    public Enemy(Vector2D v, int number, int shotNumber, int deadNumber, int readyNumber, int shootingNumber)
    {
        super(v, number);
        normImage = number;
        deadImage = deadNumber;
        shotImage = shotNumber;
        readyImage = readyNumber;
        shootingImage = shootingNumber;
        HIT_COOLDOWN = 11;
        SHOOT_COOLDOWN = 45;
        shootCooldown = SHOOT_COOLDOWN;
        CHECKALERT_COOLDOWN = 60; // 2 seconds
        audioManager = new AudioManager();
        alert = false;
        dead = false;
    }

    public void shot(Map map)
    {
        if(!dead)
        {
            audioManager.play(4);
            health--;
            hitCooldown = HIT_COOLDOWN;
            this.setImage(shotImage);
            if(health < 1)
            {
                audioManager.play(5);
                dead = true;
                this.setImage(deadImage);
                map.addSprite(new HealthPickup(new Vector2D(this.x(), this.y())));
            }
        }
    }

    public void tick(Player player)
    {
        //for when player is shooting it
        if(hitCooldown > 0)
        {
            hitCooldown--;
        }
        else if(!dead)
        {
            if(alert)
            {
                this.setImage(readyImage);
            }
            else
            {
                this.setImage(normImage);
            }
        }
        
        //for when checking if player sees it
        if(!alert && alertCooldown > 0 && !dead)
        {
            alertCooldown--;
        }
        else if(alertCooldown == 0)
        {
            checkSee();
            alertCooldown = CHECKALERT_COOLDOWN;
        }
        
        //for checking if player has not seen it
        if(this.getSeen() == false && alert && !dead)
        {
            audioManager.play(7);
            alert = false;
        }
        
        //for checking to shoot player
        if(alert && shootCooldown > 0)
        {
            shootCooldown--;
        }
        else if(!dead && shootCooldown == 0)
        {
            shootPlayer(player);
        }
    }

    private void checkSee()
    {
        if(this.getSeen())//this stuff is all set in renderer
        {
            audioManager.play(6);
            alert = true;
            shootCooldown = SHOOT_COOLDOWN;
        }
        else
        {
            alert = false;
        }
    }
    
    private void shootPlayer(Player player)
    {
        this.setImage(shootingImage);
        audioManager.play(9);
        shootCooldown = SHOOT_COOLDOWN;
        if((int)(Math.random() * 100) < 75) //75% chance of hit
        {
            player.damage(1);
            audioManager.play(8);
        }
    }
}

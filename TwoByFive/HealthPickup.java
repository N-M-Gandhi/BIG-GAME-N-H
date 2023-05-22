
/**
 * Write a description of class HealthPickup here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class HealthPickup extends Sprite
{
    private boolean isUsed;
    private int healthIncrement;
    private AudioManager audioManager;
    public HealthPickup()
    {
        
    }
    public HealthPickup(Vector2D v, int number)
    {
        super(v, number);
        isUsed = false;
        healthIncrement = 3;
        audioManager = new AudioManager();
    }
    public void tick(Player player)
    {
        double distance = Math.sqrt(Math.pow((player.x() - this.x()), 2) + Math.pow((player.y() - this.y()), 2));
        if(distance < 0.5 && !isUsed)
        {
            player.addHealth(healthIncrement);
            this.setImage(0); //empy image
            audioManager.play(11);
            isUsed = true;
        }
    }
}

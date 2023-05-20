import java.util.Scanner;
/**
 * Write a description of class Runner here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Runner implements InputListener
{
    private Player player;
    private Map map;
    private MapRenderer mapRenderer;
    private Renderer renderer;
    private InputActivator input;
    private Weapon weapon;
    public Runner()
    {
        player = new Player(12, 20, 90);
        map = new Map(32, player);
        input = new InputActivator();
        input.setInputListener(this);
        renderer = new Renderer(input);
        renderer.setTitle("Default TwoByFive Project Name");
        //walls
        Wall wall1 = new Wall(10, true, 3, 6, map.getMap());
        Wall wall2 = new Wall(20, true, 12, 10, map.getMap());
        Wall wall3 = new Wall(10, false, 4, 17, map.getMap());
        Wall wall4 = new Wall(10, true, 3, 5, map.getMap());
        Wall wall5 = new Wall(10, true, 3, 4, map.getMap());

        //outside walls
        Wall wall8 = new Wall(map.getMap().length, true, 0, 0, map.getMap());
        Wall wall6 = new Wall(map.getMap().length, false, 0, 0, map.getMap());
        Wall wall7 = new Wall(map.getMap().length, true, 0, map.getMap().length - 1, map.getMap());
        Wall wall9 = new Wall(map.getMap().length, false, map.getMap().length - 1, 0, map.getMap());

        //mapRenderer = new MapRenderer(32, input, map, player);
        //mapRenderer.setTitle("TwoByFive Map Renderer");
        //map.print();
        //renderer.render(player, map.getMap());

        //cache images
        ImageReader imageReader = new ImageReader();
        imageReader.cacheImages();

        weapon = new Weapon(13, 14);
    }

    public static void main(String[] args)
    {
        Runner runner = new Runner();
        runner.Play();
    }

    public void Play()
    {
        boolean dead = false;
        while(!dead)
        {
            try { Thread.sleep(1000/30); } catch(Exception e) {}
            renderer.render(player, map, weapon);
            weapon.tick();
            map.tick();
        }
    }

    public void wPressed()
    {
        double distX = (Math.cos(Math.toRadians(player.r())));
        double distY = (Math.sin(Math.toRadians(player.r())));

        player.moveX(distX * 0.10, map.getMap());
        player.moveY(distY * -0.10, map.getMap());
    }

    public void sPressed()
    {
        double distX = (Math.cos(Math.toRadians(player.r())));
        double distY = (Math.sin(Math.toRadians(player.r())));

        player.moveX(distX * -0.10, map.getMap());
        player.moveY(distY * 0.10, map.getMap());
    }

    public void aPressed()
    {
        double distX = (Math.sin(Math.toRadians(player.r())));
        double distY = (Math.cos(Math.toRadians(player.r())));

        player.moveX(distX * -0.10, map.getMap());
        player.moveY(distY * -0.10, map.getMap());
    }

    public void dPressed()
    {
        double distX = (Math.sin(Math.toRadians(player.r())));
        double distY = (Math.cos(Math.toRadians(player.r())));

        player.moveX(distX * 0.10, map.getMap());
        player.moveY(distY * 0.10, map.getMap());
    }

    public void qPressed()
    {
        player.transR(3, map.getMap());
    }

    public void ePressed()
    {player.transR(-3, map.getMap());
    }

    public void spacePressed()
    {
        weapon.fire(true, player, map);
    }
}

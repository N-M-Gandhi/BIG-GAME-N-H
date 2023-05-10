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
    public Runner()
    {
        player = new Player(12, 20, 90);
        map = new Map(32, player);
        input = new InputActivator();
        input.setInputListener(this);
        renderer = new Renderer(input);
        renderer.setTitle("Default TwoByFive Project Name");
        //walls
        // Wall wall1 = new Wall(10, true, 3, 6, map.getMap());
        // Wall wall2 = new Wall(20, true, 12, 10, map.getMap());
        // Wall wall3 = new Wall(10, false, 4, 17, map.getMap());
        // Wall wall4 = new Wall(10, true, 3, 5, map.getMap());
        // Wall wall5 = new Wall(10, true, 3, 4, map.getMap());
        
        //outside walls
        Wall wall8 = new Wall(map.getMap().length, true, 0, 0, map.getMap());
        Wall wall6 = new Wall(map.getMap().length, false, 0, 0, map.getMap());
        Wall wall7 = new Wall(map.getMap().length, true, 0, map.getMap().length - 1, map.getMap());
        Wall wall9 = new Wall(map.getMap().length, false, map.getMap().length - 1, 0, map.getMap());
        
        mapRenderer = new MapRenderer(32, input, map, player);
        mapRenderer.setTitle("TwoByFive Map Renderer");
        map.print();
        renderer.render3(player, map.getMap());
    }

    public static void main(String[] args)
    {
        Runner runner = new Runner();
        runner.Play();
        //Scanner scan = new Scanner(System.in);
        //System.out.println("Name: ");
        //String name = myObj.nextLine();
        //System.out.println(name);

        boolean dead = false;

        //Renderer renderer = new Renderer();
        //renderer.render(player, map.getMap());

        while(!dead)
        {
            //System.out.println("before loop");
            //System.out.println("w, a, s, d");
            //String input = scan.nextLine();
            //checkMove(input, player, map.getMap());
            //checkRotate(input, player, map.getMap());
            //map.print();
            //renderer.render(player, map.getMap());
            //System.out.println(player.r());
            //System.out.println("after loop");
        }
    }

    public void Play()
    {
        boolean dead = false;
        while(!dead)
        {
            try { Thread.sleep(1000/30); } catch(Exception e) {}
            map.print();
            mapRenderer.render();
            renderer.render3(player, map.getMap());
        }
    }

    private static void checkMove(String input, Player player , String[][] map)
    {
        int dirMult = 1;
        double dubRot = (double)(player.r());
        if(input.equals("w"))
        {
            dirMult = 1;
        }
        else if(input.equals("s"))
        {
            dirMult = -1;
        }
        else
        {
            return;
        }

        double distX = (Math.cos(Math.toRadians(player.r()))) * 2;
        double distY = (Math.sin(Math.toRadians(player.r()))) * -2;

        player.moveX((int)(distX) * dirMult, map);
        player.moveY((int)(distY) * dirMult, map);

        System.out.println((int)(distX) * dirMult);
        System.out.println((int)(distY) * dirMult);
    }

    private static void checkRotate(String input, Player player , String[][] map)
    {
        if(input.equals("a"))
        {
            player.transR(45, map);
        }
        else if(input.equals("d"))
        {
            player.transR(-45, map);
        }
        else
        {
            return;
        }
    }

    public void wPressed()
    {
        // double distX = (Math.cos(Math.toRadians(player.r()))) * 2;
        // double distY = (Math.sin(Math.toRadians(player.r()))) * 2;

        // player.moveX((int)(distX) * -1, map.getMap());
        // player.moveY((int)(distY) * -1, map.getMap());

        // System.out.println((int)(distX) * -1);
        // System.out.println((int)(distY) * -1);
        
        player.moveY(-0.25, map.getMap());
    }

    public void sPressed()
    {
        // double distX = (Math.cos(Math.toRadians(player.r()))) * 1;
        // double distY = (Math.sin(Math.toRadians(player.r()))) * 1;

        // player.moveX((int)(distX) * 1, map.getMap());
        // player.moveY((int)(distY) * 1, map.getMap());

        // System.out.println((int)(distX) * 1);
        // System.out.println((int)(distY) * 1);
        
        player.moveY(0.25, map.getMap());
    }

    public void aPressed()
    {
        // player.transR(6, map.getMap());
        
        player.moveX(-0.25, map.getMap());
    }

    public void dPressed()
    {
        // player.transR(-6, map.getMap());
        
        player.moveX(0.25, map.getMap());
    }

    public void qPressed()
    {
        player.transR(6, map.getMap());
    }

    public void ePressed()
    {player.transR(-6, map.getMap());
    }
}

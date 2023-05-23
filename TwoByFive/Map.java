import java.util.ArrayList;
/**
 * Write a description of class Map here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Map
{
    private int size;
    private int[][] map;
    Player player;
    ArrayList<Sprite> spriteList;
    public Map(InputActivator input)
    {
        //make empty map
        size = 25;
        map = new int[size][size];
        for(int y = 0; y < size; y++)
        {
            for(int x = 0; x < size; x++)
            {
                map[y][x] = 0;
            }
        }
    }
    public Map(int s, Player p)
    {
        player = p;
        //make empty map
        size = s;
        map = new int[size][size];
        for(int y = 0; y < size; y++)
        {
            for(int x = 0; x < size; x++)
            {
                map[y][x] = 0;
            }
        }
        spriteList = new ArrayList<Sprite>();
        // spriteList.add(new Sprite(new Vector2D(29, 29), 10));
        // spriteList.add(new Sprite(new Vector2D(12, 19), 9));
        spriteList.add(new Enemy(new Vector2D(12, 19), 10, 15, 12, 16, 17));
        spriteList.add(new Enemy(new Vector2D(13, 19), 10, 15, 12, 16, 17));
        spriteList.add(new Enemy(new Vector2D(14, 19), 10, 15, 12, 16, 17));
        spriteList.add(new Enemy(new Vector2D(15, 19), 10, 15, 12, 16, 17));
        spriteList.add(new Enemy(new Vector2D(16, 19), 10, 15, 12, 16, 17));
        // spriteList.add(new Enemy(new Vector2D(1, 1), 10, 15, 12, 16, 17));
    }
    public void print()
    {
        String output = "";
        for(int y = 0; y < size; y++)
        {
            for(int x = 0; x < size; x++)
            {
                output += map[y][x];
            }
            output += "\n";
        }
        System.out.println(output);
    }
    public int[][] getMap()
    {
        return map;
    }
    public void setMap(int[][] newMap)
    {
        map = newMap;
    }
    public void setMap(int x, int y, int info)
    {
        map[y][x] = info;
    }
    public ArrayList<Sprite> getSpriteList()
    {
        return spriteList;
    }
    public void addSprite(HealthPickup health)
    {
        spriteList.add(health);
    }
    public void tick()
    {
        for(int i = 0; i < spriteList.size(); i++)
        {
            Sprite sprite = spriteList.get(i);
            if(sprite instanceof Enemy)
            {
                Enemy enemy = (Enemy)sprite;
                enemy.tick(player);
            }
            else if(sprite instanceof HealthPickup)
            {
                HealthPickup health = (HealthPickup)sprite;
                health.tick(player);
            }
        }
    }
}

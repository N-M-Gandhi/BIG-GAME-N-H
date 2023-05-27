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
    public Map(Player p, int mapNumber)
    {
        player = p;
        ImageReader imageReader = new ImageReader();
        imageReader.cacheImages();
        spriteList = new ArrayList<Sprite>();
        //make map
        size = 64;
        map = new int[size][size];
        for(int y = 0; y < size; y++)
        {
            for(int x = 0; x < size; x++)
            {
                map[y][x] = 0;
                int redValue = imageReader.getColor(x, y, mapNumber).getRed();
                int greenValue = imageReader.getColor(x, y, mapNumber).getGreen();
                int blueValue = imageReader.getColor(x, y, mapNumber).getBlue();
                switch(redValue)//walls
                {
                    case 255: map[y][x] = 1; break; //multibrick
                    case 225: map[y][x] = 2; break; //brick_wall
                    case 195: map[y][x] = 3; break; //steel wall
                    case 165: map[y][x] = 4; break; //wood wall
                    case 135: map[y][x] = 5; break; //tile wall
                    case 105: map[y][x] = 6; break; //book wall
                    case 75: map[y][x] = 7; break; //blue wall
                }
                switch(greenValue)
                {
                    case 255: spriteList.add(new Sprite(new Vector2D(x, y), 26)); break; //chair
                    case 225: spriteList.add(new Sprite(new Vector2D(x, y), 28)); break; //table
                    case 195: spriteList.add(new Sprite(new Vector2D(x, y), 29)); break; //telepropmter
                    case 165: spriteList.add(new Sprite(new Vector2D(x, y), 27)); break; //hatsune miku
                }
                switch(blueValue)
                {
                    case 255: spriteList.add(new Enemy(new Vector2D(x, y), 10, 15, 12, 16, 17)); break; //gaurd
                }
            }
        }
        // spriteList.add(new Sprite(new Vector2D(29, 29), 10));
        // spriteList.add(new Sprite(new Vector2D(12, 19), 9));
        //spriteList.add(new Enemy(new Vector2D(12, 19), 10, 15, 12, 16, 17));
        //spriteList.add(new Enemy(new Vector2D(13, 19), 10, 15, 12, 16, 17));
        //spriteList.add(new Enemy(new Vector2D(14, 19), 10, 15, 12, 16, 17));
        //spriteList.add(new Enemy(new Vector2D(15, 19), 10, 15, 12, 16, 17));
        //spriteList.add(new Enemy(new Vector2D(16, 19), 10, 15, 12, 16, 17));
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

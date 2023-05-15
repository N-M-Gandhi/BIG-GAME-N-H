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
}

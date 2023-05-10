
/**
 * Write a description of class RayCast here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RayCast
{
    public static Vector2D cast(double rayAngle, Player player, String[][] map)
    {
        if(rayAngle < 0)
        {
            rayAngle = rayAngle + 360;
        }
        if(rayAngle > 360)
        {
            rayAngle = rayAngle - 360;
        }
        
        double slope = Math.tan(Math.toRadians(rayAngle));//calculate slope of rayCast
        
        int firstXMult = 1;
        int firstYMult = 1;
        int secondXMult = 1;
        int secondYMult = 1;
        int thirdXMult = 1;
        int thirdYMult = 1;
        if(rayAngle <= 90)//fuckery
        {
            firstXMult = -1; secondXMult = 1; thirdXMult = 1;
            firstYMult = -1; secondYMult = -1; thirdYMult = -1;
        }
        else if(rayAngle > 90 && rayAngle <= 180)
        {
            firstXMult = 1; secondXMult = -1; thirdXMult = 1;
            firstYMult = -1; secondYMult = -1; thirdYMult = -1;
        }
        else if(rayAngle > 180 && rayAngle <= 270)
        {
            firstXMult = 1; secondXMult = -1; thirdXMult = 1;
            firstYMult = 1; secondYMult = -1; thirdYMult = 1;
        }
        else if(rayAngle > 270)
        {
            firstXMult = -1; secondXMult = 1; thirdXMult = 1;
            firstYMult = -1; secondYMult = 1; thirdYMult = 1;
        }
        //if(rayAngle > 180 && rayAngle < 270){stepDirection = -1;}
        Vector2D[] intersections = new Vector2D[2]; //set up the store of vertical and horizontal collisions
        intersections[0] = new Vector2D(999, 999); intersections[1] = new Vector2D(999, 999); //make sure thery filled by default

        //Vertical line collision
        //find innitial vertical line intersection point
        double HoriDistFromFirstVertLine = player.x() - ((int)player.x());//horizontal distance from first vertical line
        double intersectHeight = firstXMult * slope * HoriDistFromFirstVertLine; //acts as an offset from the cell
        //the player is currently in vertically
        //this one will hit a vertivcl line eventually
        for(int x = 0; x < 64; x++)
        {
            double y = firstXMult * (slope * x) + intersectHeight;
            double realX = secondXMult * x + player.x();
            double realY = thirdXMult * y + player.y();
            if((int)realX < 0 || (int)realY < 0 || (int)realX > map.length - 1 || (int)realY > map[0].length - 1)
            {
                //just do nothing so I don't get an out of bonds error
                break;
            }
            else if(map[(int)realY][(int)realX].equals("000") || map[(int)realY][(int)realX].equals(" 0 "))
            {
                //record wall intersection value and break
                intersections[0] = new Vector2D(realX, realY);
                break;
            }
        }

        //horizontal line collision
        //find the innitial horizontal line intersect point
        double VertDistFromFirstHoriLine = player.y() - ((int)player.y());//vertical distance from first vertical line
        double intersectBreadth = firstYMult * VertDistFromFirstHoriLine / slope; //acts as an offset from the cell
        //the player is currently in horizontally
        //this one will hit a horizontal line eventually
        for(int y = 0; y < 64; y++)
        {
            double x = firstYMult * y / slope + intersectBreadth;
            double realX = secondYMult * x + player.x();
            double realY = thirdYMult * y + player.y();
            if((int)realX < 0 || (int)realY < 0 || (int)realX > map.length - 1 || (int)realY > map[0].length - 1)
            {
                //just do nothing so I don't get an out of bonds error
                break;
            }
            else if(map[(int)realY][(int)realX].equals("000") || map[(int)realY][(int)realX].equals(" 0 "))
            {
                //record wall intersection value and break
                intersections[1] = new Vector2D(realX, realY);
                break;
            }
        }

        //calculate which intersection point is closest using distance formula
        double verticalLineAlgorithmDistance = Math.sqrt(Math.pow(intersections[0].x(), 2) + Math.pow(intersections[0].y(), 2));
        double horizontalLineAlgorithmDistance = Math.sqrt(Math.pow(intersections[1].x(), 2) + Math.pow(intersections[1].y(), 2));
        if(verticalLineAlgorithmDistance < horizontalLineAlgorithmDistance)
        {
            return intersections[0];
        }
        else
        {
            return intersections[1];
        }
    }
}


/**
 * Write a description of class RayCast here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RayCast
{
    public static CastInfo cast(double rayAngle, Player player, String[][] map)
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
        //boolean verticleCollision = true;
        
        int firstXMult = 1;
        int firstYMult = 1;
        int secondXMult = 1;
        int secondYMult = 1;
        int thirdXMult = 1;
        int thirdYMult = 1;
        int extraXCellCheck = 0;
        int extraYCellCheck = 0;
        if(rayAngle <= 90)//blasphemy
        {
            firstXMult = -1; secondXMult = 1; thirdXMult = 1;
            firstYMult = -1; secondYMult = -1; thirdYMult = -1;
            //extraXCellCheck = 0;
            extraYCellCheck = -1; //bring down horizontal hits down one
        }
        else if(rayAngle > 90 && rayAngle <= 180) //2nd quadrant
        {
            firstXMult = 1; secondXMult = -1; thirdXMult = 1;
            firstYMult = -1; secondYMult = -1; thirdYMult = -1;
            extraXCellCheck = -1; //bringe left verticle hits right one
            extraYCellCheck = -1; //bring down horizontal hits down one
        }
        else if(rayAngle > 180 && rayAngle <= 270) //3rd quadrant
        {
            firstXMult = 1; secondXMult = -1; thirdXMult = 1;
            firstYMult = 1; secondYMult = -1; thirdYMult = 1;
            extraXCellCheck = -1; //bringe left verticle hits right one
            //extraYCellCheck = 0;
        }
        else if(rayAngle > 270) //4th quadrant
        {
            firstXMult = -1; secondXMult = 1; thirdXMult = 1;
            firstYMult = -1; secondYMult = 1; thirdYMult = 1;
            //extraXCellCheck = 0;
            //extraYCellCheck = 0;
        }
        //if(rayAngle > 180 && rayAngle < 270){stepDirection = -1;}
        CastInfo[] intersections = new CastInfo[2]; //set up the store of vertical and horizontal collisions
        intersections[0] = new CastInfo(new Vector2D(64, 64), 1, false);
        intersections[1] = new CastInfo(new Vector2D(64, 64), 1, false);; //make sure thery filled by default

        //Vertical line collision
        //find innitial vertical line intersection point
        double HoriDistFromFirstVertLine = player.x() - ((int)player.x());//horizontal distance from first vertical line
        double intersectHeight = firstXMult * slope * HoriDistFromFirstVertLine; //acts as an offset from the cell
        intersectHeight = 0;
        //the player is currently in vertically
        //this one will hit a vertivcl line eventually
        for(double x = 0; x < 64; x+=1)
        {
            double y = firstXMult * (slope * x) + intersectHeight;
            double realX = secondXMult * x + (int)player.x();
            double realY = thirdXMult * y + player.y();
            if((int)realX < 0 || (int)realY < 0 || (int)realX > map.length - 1 || (int)realY > map[0].length - 1)
            {
                //just do nothing so I don't get an out of bonds error
                break;
            }
            else if(map[(int)realY][(int)realX].equals("1") || map[(int)realY][(int)realX + extraXCellCheck].equals("1"))
            {
                //record wall intersection value and break
                intersections[0].setInfo(new Vector2D(realX, realY), 1, true);
                break;
            }
        }

        //horizontal line collision
        //find the innitial horizontal line intersect point
        double VertDistFromFirstHoriLine = player.y() - ((int)player.y());//vertical distance from first vertical line
        double intersectBreadth = firstYMult * VertDistFromFirstHoriLine / slope; //acts as an offset from the cell
        intersectBreadth = 0;
        //the player is currently in horizontally
        //this one will hit a horizontal line eventually
        for(double y = 0; y < 64; y+=1)
        {
            double x = firstYMult * y / slope + intersectBreadth;
            double realX = secondYMult * x + player.x();
            double realY = thirdYMult * y + (int)player.y();
            if((int)realX < 0 || (int)realY < 0 || (int)realX > map.length - 1 || (int)realY > map[0].length - 1)
            {
                //just do nothing so I don't get an out of bonds error
                break;
            }
            else if(map[(int)realY][(int)realX].equals("1") || map[(int)realY + extraYCellCheck][(int)realX].equals("1"))
            {
                //record wall intersection value and break
                intersections[1].setInfo(new Vector2D(realX, realY), 1, false);
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

    public static Vector2D cast2(double rayAngle, Player player, String[][] map)
    {
        if(rayAngle < 0)
        {
            rayAngle = rayAngle + 360;
        }
        if(rayAngle > 360)
        {
            rayAngle = rayAngle - 360;
        }
        
        int r,mx,my,mp,dof,side; float vx,vy,rx,ry,xo=0,yo=0,disV,disH;
        float py = (float)player.y(); float px = (float)player.x();
        int mapX = map[0].length; int mapY = map.length;
        double ra = Math.toRadians(rayAngle);

        //---Vertical--- 
        dof=0; side=0; disV=100000;
        float Tan=(float)Math.tan(ra);
        if(Math.cos(ra)> 0.001){ rx=(((int)px>>6)<<6)+64;      ry=(px-rx)*Tan+py; xo= 64; yo=-xo*Tan;}//looking left
        else if(Math.cos(ra)<-0.001){ rx=(((int)px>>6)<<6) -0.0001f; ry=(px-rx)*Tan+py; xo=-64; yo=-xo*Tan;}//looking right
        else { rx=px; ry=py; dof=8;}                                                  //looking up or down. no hit  

        while(dof<8) 
        { 
            mx=(int)(rx)>>6; my=(int)(ry)>>6; mp=my*mapX+mx;                     
            if(my < mapY && mx < mapX && my >= 0 && mx >= 0 && map[my][mx].equals("1")){ dof=8; disV=(float)Math.cos(ra)*(rx-px)-(float)Math.cos(ra)*(ry-py);}//hit         
            else{ rx+=xo; ry+=yo; dof+=1;}                                               //check next horizontal
        } 
        vx=rx; vy=ry;

        //---Horizontal---
        dof=0; disH=100000;
        Tan=1.0f/Tan; 
        if(Math.sin(ra)> 0.001){ ry=(((int)py>>6)<<6) -0.0001f; rx=(py-ry)*Tan+px; yo=-64; xo=-yo*Tan;}//looking up 
        else if(Math.sin(ra)<-0.001){ ry=(((int)py>>6)<<6)+64;      rx=(py-ry)*Tan+px; yo= 64; xo=-yo*Tan;}//looking down
        else{ rx=px; ry=py; dof=8;}                                                   //looking straight left or right

        while(dof<8) 
        { 
            mx=(int)(rx)>>6; my=(int)(ry)>>6; mp=my*mapX+mx;                          
            if(my < mapY && mx < mapX && my >= 0 && mx >= 0 && map[my][mx].equals("1")){ dof=8; disH=(float)Math.cos(ra)*(rx-px)-(float)Math.sin(ra)*(ry-py);}//hit         
            else{ rx+=xo; ry+=yo; dof+=1;}                                               //check next horizontal
        } 

        if(disV<disH){ rx=vx; ry=vy; disH=disV;}                  //horizontal hit first
        
        return new Vector2D(rx, ry);
    }
}


/**
 * Write a description of class RayCast here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RayCast
{
    public static CastInfo cast(double rayAngle, Player player, int[][] map)
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
        int xCellOffset = 0;
        int yCellOffset = 0;
        if(rayAngle <= 90)//blasphemy
        {
            firstXMult = -1; secondXMult = 1; thirdXMult = 1;
            firstYMult = -1; secondYMult = -1; thirdYMult = -1;
            extraXCellCheck = 0;
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
            extraYCellCheck = 0;
        }
        else if(rayAngle > 270) //4th quadrant
        {
            firstXMult = -1; secondXMult = 1; thirdXMult = 1;
            firstYMult = -1; secondYMult = 1; thirdYMult = 1;
            extraXCellCheck = 0;
            extraYCellCheck = 0;
        }
        //if(rayAngle > 180 && rayAngle < 270){stepDirection = -1;}
        CastInfo[] intersections = new CastInfo[2]; //set up the store of vertical and horizontal collisions
        intersections[0] = new CastInfo(new Vector2D(64, 64), 1, false);
        intersections[1] = new CastInfo(new Vector2D(64, 64), 1, false);; //make sure thery filled by default

        //Vertical line collision
        //find innitial vertical line intersection point
        double HoriDistFromFirstVertLine = 0;
        HoriDistFromFirstVertLine = (int)player.x() - player.x();
        if(rayAngle <= 90 || rayAngle > 270){HoriDistFromFirstVertLine = (int)player.x() + 1  - player.x();xCellOffset = 1;}//positive value from right wall if looking righy(works)
        else{HoriDistFromFirstVertLine = (int)player.x() - player.x();}//negative value to lef wall if looking left(notw work)
        double intersectHeight = -1 * slope * HoriDistFromFirstVertLine; //acts as an offset from the cell
        //the player is currently in vertically
        //this one will hit a vertivcl line eventually
        for(int x = 0; x < 64; x+=1)
        {
            double y = firstXMult * (slope * x) + intersectHeight;
            double realX = secondXMult * x + (int)player.x() + xCellOffset;
            double realY = thirdXMult * y + (int)player.y();
            if((int)realX < 0 || (int)realY < 0 || (int)realX > map.length - 1 || (int)realY > map[0].length - 1)
            {
                //just do nothing so I don't get an out of bonds error
                break;
            }
            else if(map[(int)realY][(int)realX] > 0 || map[(int)realY][(int)realX + extraXCellCheck] > 0)
            {
                //record wall intersection value and break
                intersections[0].setInfo(new Vector2D(realX, realY), 1, true);
                break;
            }
        }

        //horizontal line collision
        //find the innitial horizontal line intersect point
        double VertDistFromFirstHoriLine = 0;
        if(rayAngle < 180){VertDistFromFirstHoriLine = (int)(player.y()) - player.y();}//negative value from top wall if looking up(works)
        else if(rayAngle > 270){VertDistFromFirstHoriLine = (int)(player.y()) + 1 - player.y(); yCellOffset = 1;}//positive value to bottom wall if looking down(works)
        double intersectBreadth = -1 * VertDistFromFirstHoriLine / slope; //acts as an offset from the cell
        //the player is currently in horizontally
        //this one will hit a horizontal line eventually
        for(int y = 0; y < 64; y+=1)
        {
            double x = firstYMult * y / slope + intersectBreadth;
            double realX = secondYMult * x + (int)player.x();
            double realY = thirdYMult * y + (int)player.y() + yCellOffset;
            if((int)realX < 0 || (int)realY < 0 || (int)realX > map.length - 1 || (int)realY > map[0].length - 1)
            {
                //just do nothing so I don't get an out of bonds error
                break;
            }
            else if(map[(int)realY][(int)realX] > 0 || map[(int)realY + extraYCellCheck][(int)realX] > 0)
            {
                //record wall intersection value and break
                intersections[1].setInfo(new Vector2D(realX, realY), 1, false);
                break;
            }
        }

        //calculate which intersection point is closest using distance formula
        double verticalLineAlgorithmDistance = Math.sqrt(Math.pow(intersections[0].x() - player.x(), 2) 
                + Math.pow(intersections[0].y() - player.y(), 2));
        double horizontalLineAlgorithmDistance = Math.sqrt(Math.pow(intersections[1].x() - player.x(), 2) 
                + Math.pow(intersections[1].y() - player.y(), 2));
        if(verticalLineAlgorithmDistance <= horizontalLineAlgorithmDistance)
        {
            return intersections[0];
        }
        else
        {
            return intersections[1];
        }
    }

    public static CastInfo castClose(double rayAngle, Player player, int[][] map)
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
        int fourthXMult = 1;
        int fourthxMult = 1;
        int extraXCellCheck = 0;
        int extraYCellCheck = 0;
        if(rayAngle <= 90)//blasphemy
        {
            firstXMult = -1; secondXMult = 1; thirdXMult = 1;
            firstYMult = -1; secondYMult = -1; thirdYMult = -1;
            extraXCellCheck = 0;
            extraYCellCheck = -1; //bring down horizontal hits down one
        }
        else if(rayAngle > 90 && rayAngle <= 180) //2nd quadrant
        {
            firstXMult = 1; secondXMult = -1; thirdXMult = 1; fourthXMult = -1;//changed firstxmult to -1
            firstYMult = -1; secondYMult = -1; thirdYMult = -1; fourthXMult = -1;
            extraXCellCheck = -1; //bringe left verticle hits right one
            extraYCellCheck = -1; //bring down horizontal hits down one
        }
        else if(rayAngle > 180 && rayAngle <= 270) //3rd quadrant
        {
            firstXMult = -1; secondXMult = -1; thirdXMult = 1; fourthXMult = -1;//changed firstxmult to -1
            firstYMult = -1; secondYMult = -1; thirdYMult = 1; fourthXMult = -1;//changed firstymult to -1
            extraXCellCheck = -1; //bringe left verticle hits right one
            extraYCellCheck = 0;
        }
        else if(rayAngle > 270) //4th quadrant
        {
            firstXMult = -1; secondXMult = 1; thirdXMult = 1;
            firstYMult = -1; secondYMult = 1; thirdYMult = 1;
            extraXCellCheck = 0;
            extraYCellCheck = 0;
        }
        //if(rayAngle > 180 && rayAngle < 270){stepDirection = -1;}
        CastInfo[] intersections = new CastInfo[2]; //set up the store of vertical and horizontal collisions
        intersections[0] = new CastInfo(new Vector2D(64, 64), 1, false);
        intersections[1] = new CastInfo(new Vector2D(64, 64), 1, false);; //make sure thery filled by default

        //Vertical line collision
        //find innitial vertical line intersection point
        double HoriDistFromFirstVertLine = 0;
        if(rayAngle <= 90 || rayAngle > 270){HoriDistFromFirstVertLine = (int)player.x() + 1  - player.x();}//positive value from right wall if looking righy(works)
        else{HoriDistFromFirstVertLine = (int)player.x() - player.x();}//negative value to lef wall if looking left(notw work)
        //HoriDistFromFirstVertLine = ((int)Math.round(player.x())) - player.x();//horizontal distance from first vertical line
        //double HoriDistFromFirstVertLine = player.x() - ((int)Math.round(player.x()));//horizontal distance from first vertical li
        double intersectHeight = -1 * slope * HoriDistFromFirstVertLine; //acts as an offset from the cell
        intersections[0].setInfo(new Vector2D(HoriDistFromFirstVertLine + player.x(), intersectHeight + player.y()), 1, true);

        //horizontal line collision
        //find the innitial horizontal line intersect point
        double VertDistFromFirstHoriLine = 0;
        if(rayAngle < 180){VertDistFromFirstHoriLine = (int)(player.y()) - player.y();}//negative value from top wall if looking up(works)
        else{VertDistFromFirstHoriLine = (int)(player.y()) + 1 - player.y();}//positive value to bottom wall if looking down(works)
        //VertDistFromFirstHoriLine = (int)Math.round(player.y()) - player.y();//vertical distance from first vertical line
        //double VertDistFromFirstHoriLine = player.y() - (int)Math.round(player.y());//vertical distance from first vertical line
        double intersectBreadth = -1 * VertDistFromFirstHoriLine / slope; //acts as an offset from the cell
        //System.out.println(HoriDistFromFirstVertLine + ", " + VertDistFromFirstHoriLine);
        intersections[1].setInfo(new Vector2D(intersectBreadth + player.x(), VertDistFromFirstHoriLine + player.y()), 1, false);

        //calculate which intersection point is closest using distance formula
        double verticalLineAlgorithmDistance = Math.sqrt(Math.pow(intersections[0].x() - player.x(), 2) 
                + Math.pow(intersections[0].y() - player.y(), 2));
        double horizontalLineAlgorithmDistance = Math.sqrt(Math.pow(intersections[1].x() - player.x(), 2) 
                + Math.pow(intersections[1].y() - player.y(), 2));
        if(verticalLineAlgorithmDistance <= horizontalLineAlgorithmDistance)
        {
            return intersections[0];
        }
        else
        {
            return intersections[1];
        }
    }

    public static CastInfo castLodev(double rayAngle, Player player, int[][] map) {
        if (rayAngle < 0) {
            rayAngle = rayAngle + 360;
        }
        if (rayAngle > 360) {
            rayAngle = rayAngle - 360;
        }
        double theta = Math.toRadians(rayAngle);
        double rayDirX = Math.cos(theta);
        double rayDirY = -1 * Math.sin(theta);//idk why *-1; the code below is probably fugged up
        int mapX = (int) player.x();
        int mapY = (int) player.y();
        double sideDistX;
        double sideDistY;
        double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
        double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
        double perpWallDist;
        int stepX;
        int stepY;
        int hit = 0;
        int side = 0;
        //mine
        int xCellOff = 0;
        int yCellOff = 0;
        if (rayDirX < 0) {
            stepX = -1;
            sideDistX = (player.x() - mapX) * deltaDistX;
            xCellOff = -1;
        } else {
            stepX = 1;
            sideDistX = (mapX + 1.0 - player.x()) * deltaDistX;
        }
        if (rayDirY < 0) {
            stepY = -1;
            sideDistY = (player.y() - mapY) * deltaDistY;
            yCellOff = -1;
        } else {
            stepY = 1;
            sideDistY = (mapY + 1.0 - player.y()) * deltaDistY;
        }
        while (hit == 0) {
            if (sideDistX < sideDistY) {
                sideDistX += deltaDistX;
                mapX += stepX;
                side = 0;
            } else {
                sideDistY += deltaDistY;
                mapY += stepY;
                side = -1;
            }
            if (map[mapY][mapX] > 0)
            {
                hit = 1;
                xCellOff = 0;
                yCellOff = 0;
            }
            else if(map[mapY][mapX + xCellOff] > 0)
            {
                hit = 1;
                yCellOff = 0;
                xCellOff = -1;
            }
            else if(map[mapY + yCellOff][mapX] > 0)
            {
                hit = 1;
                yCellOff = -1;
                xCellOff = 0;
            }
            else if(map[mapY + yCellOff][mapX + xCellOff] > 0)
            {
                hit = 1;
                xCellOff = -1;
                yCellOff = -1;
            }
        }
        while(mapY < 0){mapY++;}
        while(mapX < 0){mapX++;}
        if (side == 0) {
            perpWallDist = (side == 0) ? (mapX - player.x() + (1 - stepX) * 0.5) / rayDirX : (mapY - player.y() + (1 - stepY) * 0.5) / rayDirY;
            //perpWallDist = (mapX - player.x() + (1 - stepX) / 2) / rayDirX;
            return new CastInfo(new Vector2D(mapX, player.y() + perpWallDist * rayDirY), map[(mapY) + yCellOff][mapX + xCellOff], true);
        } else {
            perpWallDist = (side == 0) ? (mapX - player.x() + (1 - stepX) * 0.5) / rayDirX : (mapY - player.y() + (1 - stepY) * 0.5) / rayDirY;
            //perpWallDist = (mapY - player.y() + (1 - stepY) / 2) / rayDirY;
            return new CastInfo(new Vector2D(player.x() + perpWallDist * rayDirX, mapY), map[(mapY) + yCellOff][mapX + xCellOff], false);
        }
    }

    public static CastInfo castLodevGPT(double rayAngle, Player player, int[][] map) {
        if (rayAngle < 0) {
            rayAngle = rayAngle + 360;
        }
        if (rayAngle > 360) {
            rayAngle = rayAngle - 360;
        }
        double theta = Math.toRadians(rayAngle);
        double rayDirX = Math.cos(theta);
        double rayDirY = -1 * Math.sin(theta);
        int mapX = (int) player.x();
        int mapY = (int) player.y();
        double sideDistX;
        double sideDistY;
        double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
        double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
        double perpWallDist;
        int stepX;
        int stepY;
        int hit = 0;
        int side = 0;

        if (rayDirX < 0) {
            stepX = -1;
            sideDistX = (player.x() - mapX) * deltaDistX;
        } else {
            stepX = 1;
            sideDistX = (mapX + 1.0 - player.x()) * deltaDistX;
        }

        if (rayDirY < 0) {
            stepY = -1;
            sideDistY = (player.y() - mapY) * deltaDistY;
        } else {
            stepY = 1;
            sideDistY = (mapY + 1.0 - player.y()) * deltaDistY;
        }

        while (hit == 0) {
            if (sideDistX < sideDistY) {
                sideDistX += deltaDistX;
                mapX += stepX;
                side = 0;
            } else {
                sideDistY += deltaDistY;
                mapY += stepY;
                side = 1;
            }

            if (mapY < 0 || mapY >= map.length || mapX < 0 || mapX >= map[0].length || map[mapY][mapX] > 0) {
                hit = 1;
            }
        }

        if (side == 0) {
            perpWallDist = (mapX - player.x() + (1 - stepX) / 2) / rayDirX;
            return new CastInfo(new Vector2D(mapX, player.y() + perpWallDist * rayDirY), map[mapY][mapX], true);
        } else {
            perpWallDist = (mapY - player.y() + (1 - stepY) / 2) / rayDirY;
            return new CastInfo(new Vector2D(player.x() + perpWallDist * rayDirX, mapY), map[mapY][mapX], false);
        }
    }
}

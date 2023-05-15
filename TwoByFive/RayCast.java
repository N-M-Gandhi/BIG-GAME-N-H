
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
            else if(map[(int)realY][(int)realX].equals("1") || map[(int)realY][(int)realX + extraXCellCheck].equals("1"))
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
            else if(map[(int)realY][(int)realX].equals("1") || map[(int)realY + extraYCellCheck][(int)realX].equals("1"))
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

    public static CastInfo castClose(double rayAngle, Player player, String[][] map)
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

    public static CastInfo castWolf(double rayAngle, Player player, String[][] map)
    {
        if(rayAngle < 0){rayAngle = rayAngle + 360;}if(rayAngle > 360){rayAngle = rayAngle - 360;}
        double theta = Math.toRadians(rayAngle); //theta is ray direction
        double tanA = Math.tan(theta);
        double cotA = 1/Math.tan(theta);
        CastInfo[] intersections = new CastInfo[2]; //set up the store of vertical and horizontal collisions
        intersections[0] = new CastInfo(new Vector2D(64, 64), 1, false);
        intersections[1] = new CastInfo(new Vector2D(64, 64), 1, false);; //make sure thery filled by default
        //depending on theta
        double rayX, rayY, vx, vy;
        double xOffset, yOffset, vertHitDis, horiHitDis;
        int depth = 0;
        int maxDepth = 64;  // maxDepth is the maximum number of
        // attempt for a ray to find a wall.

        // Check for vertical hit
        depth = 0;
        vertHitDis = 0;
        if (Math.sin(theta) > 0.001) // rayA pointing rightward
        {  
            rayX = (int) player.x() + 1;
            rayY = (rayX - player.x()) * cotA + player.y();
            xOffset = 1;
            yOffset = xOffset * cotA;
        } 
        else if (Math.sin(theta) < -0.001) // rayA pointing leftward
        {  
            rayX = (int) player.x() - 0.001;
            rayY = (rayX - player.x()) * cotA + player.y();
            xOffset = -1;
            yOffset = xOffset * cotA;
        } 
        else // rayA pointing up or down
        {  
            rayX = player.x();
            rayY = player.y();
            xOffset = 0;
            yOffset = 0;
            depth = maxDepth;
        }

        while (depth < maxDepth) {
            if((int)rayX < 0 || (int)rayY < 0 || (int)rayX > map.length - 1 || (int)rayY > map[0].length - 1)
            {
                //just do nothing so I don't get an out of bonds error
                break;
            }
            else if (map[(int)rayY][(int)rayX].equals("1") || map[(int)rayY][(int)rayX - 1].equals("1") || map[(int)rayY - 1][(int)rayX].equals("1")) 
            {
                intersections[0] = new CastInfo(new Vector2D(rayX, rayY), 1, true);
                break;
            } 
            else 
            {
                rayX += xOffset;
                rayY += yOffset;
                depth += 1;
            }
        }
        vx = rayX;
        vy = rayY;

        // Check for horizontal hit
        depth = 0;
        horiHitDis = 0;
        if (Math.cos(theta) > 0.001) {  // rayA pointing upward
            rayY = (int) player.y() + 1;
            rayX = (rayY - player.y()) * tanA + player.x();
            yOffset = 1;
            xOffset = yOffset * tanA;
        } else if (Math.cos(theta) < -0.001) {  // rayA pointing downward
            rayY = (int) player.y() - 0.001;
            rayX = (rayY - player.y()) * tanA + player.x();
            yOffset = -1;
            xOffset = yOffset * tanA;
        } else {  // rayA pointing leftward or rightward
            rayX = player.x();
            rayY = player.y();
            xOffset = 0;
            yOffset = 0;
            depth = maxDepth;
        }

        while (depth < maxDepth) {
            if((int)rayX < 0 || (int)rayY < 0 || (int)rayX > map.length - 1 || (int)rayY > map[0].length - 1)
            {
                //just do nothing so I don't get an out of bonds error
                break;
            }
            else if (map[(int)rayY][(int)rayX].equals("1") || map[(int)rayY][(int)rayX - 1].equals("1") || map[(int)rayY - 1][(int)rayX].equals("1")) 
            {
                intersections[0] = new CastInfo(new Vector2D(rayX, rayY), 1, false);
                break;
            } 
            else 
            {
                rayX += xOffset;
                rayY += yOffset;
                depth += 1;
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

    public static CastInfo castLodev(double rayAngle, Player player, String[][] map) {
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
            //xCellOff = -1;
        } else {
            stepX = 1;
            sideDistX = (mapX + 1.0 - player.x()) * deltaDistX;
        }
        if (rayDirY < 0) {
            stepY = -1;
            sideDistY = (player.y() - mapY) * deltaDistY;
            //yCellOff = -1;
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
            if (map[mapY][mapX].equals("1") || map[mapY][mapX + xCellOff].equals("1") || map[mapY + yCellOff][mapX].equals("1")) {
                hit = 1;
            }
        }
        if (side == 0) {
            perpWallDist = (mapX - player.x() + (1 - stepX) / 2) / rayDirX;
            return new CastInfo(new Vector2D(mapX, player.y() + perpWallDist * rayDirY), 1, true);
        } else {
            perpWallDist = (mapY - player.y() + (1 - stepY) / 2) / rayDirY;
            return new CastInfo(new Vector2D(player.x() + perpWallDist * rayDirX, mapY), 1, false);
        }
    }

    public static CastInfo castGPT(double rayAngle, Player player, String[][] map) {
        double rayAngleRadians = Math.toRadians(rayAngle);
        double rayX = player.x();
        double rayY = player.y();
        double rayDirX = Math.cos(rayAngleRadians);
        double rayDirY = Math.sin(rayAngleRadians);

        while (true) {
            int mapX = (int) rayX;
            int mapY = (int) rayY;

            double sideDistX;
            double sideDistY;

            double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
            double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));

            double perpWallDist;

            int stepX;
            int stepY;

            boolean hit = false;
            int side = 0;

            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (rayX - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - rayX) * deltaDistX;
            }

            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (rayY - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - rayY) * deltaDistY;
            }

            while (!hit) {
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }

                if (map[mapX][mapY].equals("1")) {
                    hit = true;
                }
            }

            if (side == 0) {
                perpWallDist = (mapX - rayX + (1 - stepX) / 2) / rayDirX;
            } else {
                perpWallDist = (mapY - rayY + (1 - stepY) / 2) / rayDirY;
            }

            double wallHitX = rayX + perpWallDist * rayDirX;
            double wallHitY = rayY + perpWallDist * rayDirY;

            CastInfo castInfo = new CastInfo(new Vector2D(wallHitX, wallHitY), 1, side == 0);
            return castInfo;
        }
    }

    public static CastInfo castGPT2(double rayAngle, Player player, String[][] map) {
        double rayAngleRad = Math.toRadians(rayAngle);

        // Calculate the ray's direction vector
        double rayDirX = Math.cos(rayAngleRad);
        double rayDirY = Math.sin(rayAngleRad);

        // Get the player's position
        double playerX = player.x();
        double playerY = player.y();

        // Initialize the ray's starting position
        double rayPosX = playerX;
        double rayPosY = playerY;

        // Calculate the increment step for the ray's position
        double rayStepX = (rayDirX >= 0) ? 1 : -1;
        double rayStepY = (rayDirY >= 0) ? 1 : -1;

        // Calculate the initial distance to the next X or Y side
        double sideDistX = Math.abs((rayStepX == 1 ? Math.ceil(rayPosX) : Math.floor(rayPosX)) - rayPosX);
        double sideDistY = Math.abs((rayStepY == 1 ? Math.ceil(rayPosY) : Math.floor(rayPosY)) - rayPosY);

        // Calculate the distance the ray has to travel to reach the next X or Y side
        double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
        double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));

        // Initialize variables to store the wall number and whether it's a vertical or horizontal intersection
        int wallNumber = 0;
        boolean isVertical = false;

        // Perform DDA (Digital Differential Analysis) algorithm for raycasting
        while (true) {
            // Check if the ray has hit a wall
            if (map[(int) Math.floor(rayPosY)][(int) Math.floor(rayPosX)].equals("1")) {
                // Store the wall number and determine if it's vertical or horizontal
                wallNumber = Integer.parseInt(map[(int) Math.floor(rayPosY)][(int) Math.floor(rayPosX)]);
                isVertical = (sideDistX < sideDistY);

                // Exit the loop when a wall is hit
                break;
            }

            // Increment or decrement the ray's position based on the step
            if (sideDistX < sideDistY) {
                sideDistX += deltaDistX;
                rayPosX += rayStepX;
            } else {
                sideDistY += deltaDistY;
                rayPosY += rayStepY;
            }
        }

        // Create a Vector2D object to store the intersection point
        Vector2D intersectionPoint = new Vector2D(rayPosX, rayPosY);

        // Create a CastInfo object with the intersection point, wall number, and intersection type
        return new CastInfo(intersectionPoint, wallNumber, isVertical);
    }

    public static Vector2D cast3(double rayAngle, Player player, String[][] map)
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

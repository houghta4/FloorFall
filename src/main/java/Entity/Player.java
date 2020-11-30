package Entity;

import GameLaunch.GamePanel;
import tiles.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Player{

    private TileMap tm;
    private int playerPos[][];

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    public boolean reset;
    public boolean win;

    private int x;
    private int y;
    private int startx;
    private int starty;

    private int mapWidth;
    private int mapHeight;
    public static int lvl = 0;
    public static int lives = 3;

    private BufferedImage img;



    public Player(TileMap tm, int mapWidth, int mapHeight, int startRow, int startCol){
        this.tm = tm;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        x = startCol;
        y = startRow;
        startx = startCol;
        starty = startRow;
        playerPos = new int[mapHeight][mapWidth];
        initArray(playerPos,startRow, startCol);
        //printArray(playerPos);


        try{
            img = ImageIO.read(new File("res/penguin.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void initArray(int[][] playerPos, int row, int col){
        for (int i = 0; i < mapHeight; i++){
            for (int j = 0; j < mapWidth; j++){
                playerPos[i][j] = 0;
            }
        }
        playerPos[row][col] = TileMap.PLAYER;
    }
    public void printArray(int[][] playerPos){
        for (int i = 0; i < mapHeight; i++){
            for (int j = 0; j < mapWidth; j++){
                System.out.print(playerPos[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public boolean setLeft(boolean b){ return left = b;}
    public boolean setRight(boolean b){ return right = b;}
    public boolean setUP(boolean b){ return up = b;}
    public boolean setDown(boolean b){ return down = b;}


    public int getX() {return x;}
    public int getY(){ return y;}

    public void move(){
        if(up){
            if(tm.getTile(y-1, x) != TileMap.WALL){
                playerPos[y][x] = 0;
                fall();
                playerPos[--y][x] = TileMap.PLAYER;
                //printArray(playerPos);
            }else{
                playerPos[y][x] = TileMap.PLAYER;
            }
            setUP(false);
        }
        if(down){
            if(tm.getTile(y+1,x) != TileMap.WALL){
                playerPos[y][x] = 0;
                fall();
                playerPos[++y][x] = TileMap.PLAYER;
                //printArray(playerPos);
            }else{
                playerPos[y][x] = TileMap.PLAYER;
            }
            setDown(false);
        }
        if(left){
            if(tm.getTile(y,x-1) != TileMap.WALL){
                playerPos[y][x] = 0;
                fall();
                playerPos[y][--x] = TileMap.PLAYER;
                //printArray(playerPos);
            }else{
                playerPos[y][x] = TileMap.PLAYER;
            }
            setLeft(false);
        }
        if(right){
            if(tm.getTile(y,x+1) != TileMap.WALL){
                playerPos[y][x] = 0;
                fall();
                playerPos[y][++x] = TileMap.PLAYER;
                //printArray(playerPos);
            }else{
               playerPos[y][x] = TileMap.PLAYER;
            }
            setRight(false);
        }


        //next level/win
        if(tm.getTile(y,x) == TileMap.GOAL){
            System.out.println("YOU WIN");
            win = true;
            ++lvl;
            //System.out.println(lvl);
        }

        //lose
        if(tm.getTile(y,x) == TileMap.FALL){
            System.out.println("YOU FELL");
            --lives;
            reset = true;
        }
    }

    //melts the ice
    private void fall(){
        if(tm.getTile(y,x) == TileMap.DOUBLE_FLOOR) tm.setTile(y,x , TileMap.FLOOR);
        else tm.setTile(y,x, TileMap.FALL);
    }

    public void update(){
        move();
    }

    public void draw(Graphics2D g){
        for (int row = 0; row < mapHeight; row++){
            for (int col = 0; col < mapWidth; col++){
                int rc = playerPos[row][col];
                if(rc == TileMap.PLAYER){
                    //g.setColor(Color.RED);
                    g.drawImage(img, x * tm.getTileSize(), y * tm.getTileSize(), null);
                    //g.fillRect(x * tm.getTileSize(), y * tm.getTileSize(), tm.getTileSize(), tm.getTileSize());
                }
            }
        }
    }


}

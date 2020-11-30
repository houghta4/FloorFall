package tiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TileMap {

    // these will have to change with real tileSet
    public static final int WALL = 0;
    public static final int FLOOR = 1;
    public static final int DOUBLE_FLOOR = 3;
    public static final int TRIPLE_FLOOR = 2; // i will never use this
    public static final int FALL = 4;
    public static final int GOAL = 5;
    public static final int PLAYER = 9;


    private int x;
    private int y;

    private int tileSize;
    private int[][] map;
    private int mapWidth;
    private int mapHeight;

    private BufferedImage tileSet;
    private Tile[][] tiles;

    public TileMap(String s, int tileSize){
        this.tileSize = tileSize;

        try{
            BufferedReader br = new BufferedReader(new FileReader(s));

            mapWidth = Integer.parseInt(br.readLine());
            mapHeight = Integer.parseInt(br.readLine());
            map = new int[mapHeight][mapWidth];

            String delimiters = "\\s+";
            for (int row = 0; row < mapHeight; row++){
                String line = br.readLine();
                String[] tokens = line.split(delimiters);
                for (int col = 0; col < mapWidth; col++){
                    map[row][col] = Integer.parseInt(tokens[col]);
                }

            }
        }catch(Exception e){
            System.out.println("Reading map failure");
            e.printStackTrace();
        }
    }

    public void loadTiles(String s){
        try{
            tileSet = ImageIO.read(new File(s));
            int numTilesAcross = (tileSet.getWidth() + 1) / (tileSize + 1);
            tiles = new Tile[2][numTilesAcross + 1];

            BufferedImage subImage;
            for( int col = 0; col < numTilesAcross + 1; col++){
                subImage = tileSet.getSubimage(col * tileSize, 0, tileSize, tileSize); //x = col * tileSize + col
                tiles[0][col] = new Tile(subImage);
                subImage = tileSet.getSubimage(col * tileSize, tileSize, tileSize, tileSize); // y = tileSize + 1
                tiles[1][col] = new Tile(subImage);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getX(){ return x;}
    public int getY(){ return y;}

    public int getMapWidth() { return mapWidth;}
    public int getMapHeight() { return mapHeight;}

    public int getColTile(int i){ return i / tileSize; }
    public int getRowTile(int i) { return i / tileSize;}

    public int getTileSize(){ return tileSize;}

    public int getTile(int row, int col){
        return map[row][col];
    }
    public void setTile(int row, int col, int blockType){map[row][col] = blockType;}

    public void setX(int i){ x = i;}
    public void setY(int i){ y = i;}

    public void update(){
    }

    public void draw(Graphics2D g){
        for (int row = 0; row < mapHeight; row++){
            for (int col = 0; col < mapWidth; col++){
                int rc = map[row][col];

                int r = rc / tiles[0].length;
                int c = rc % tiles[0].length;

                g.drawImage(tiles[r][c].getImage(), x + col * tileSize, y + row * tileSize, null);

//                if(rc == WALL){
//                    g.setColor(Color.BLACK);
//                }
//                if(rc == FLOOR){
//                    g.setColor(Color.WHITE);
//                }
//                if(rc == GOAL){
//                    g.setColor(Color.GREEN);
//                }
//                if(rc == FALL){
//                    g.setColor(new Color(73, 63, 238));
//                }
//                g.fillRect(x + col * tileSize, y + row * tileSize, tileSize, tileSize);
            }
        }
    }
}

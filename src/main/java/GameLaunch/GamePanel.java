package GameLaunch;

import Entity.Fonts;
import Entity.Player;
import Entity.Sprite;
import tiles.TileMap;
import util.Vector2f;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

//TODO maybe put all text under game and keep same amt of rows each level

public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 900;
    public static final int HEIGHT = 720;

    private Thread thread;
    private boolean running;
    private boolean gameOver;
    private boolean gameWin;

    private BufferedImage image;
    private Graphics2D g;

    private int FPS = 30;
    private int targetTime = 1000 / FPS;

    private TileMap tileMap;
    private Player player;
    private Fonts font;
    private BufferedImage img;
    private ArrayList<BufferedImage> lifeList = new ArrayList<>();


    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        font = new Fonts("res/ZeldaFont.png",  16, 16);
        try{
            img = ImageIO.read(new File("res/penguin.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
        for(int i = 0; i < Player.lives; i++){
            lifeList.add(img);
        }
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    public void run() {
        init();

        long startTime;
        long urdTime;
        long waitTime;

        while (running) {
            //removes life sprite
            if(player.reset && lifeList.size() > 0){
                    lifeList.remove(lifeList.size() - 1);
            }
            //resets current level if lost
            if(player.reset && player.lives > 0 && player.lvl == 0){
                init();
            }else if(player.reset && player.lives <= 0){ //3 lives
                System.out.println("GAME OVER");
                gameOver = true;
                running = false;
            }
            if(player.win && player.lvl == 5) {
                gameWin = true;
                running = false;
            }
            if(player.win && player.lvl == 1 || (player.reset && player.lives > 0 && player.lvl == 1 )){
                init2();
            }
            if(player.win && player.lvl == 2 || player.reset && player.lives > 0 && player.lvl == 2){
                init3();
            }
            if(player.win && player.lvl == 3 || (player.reset && player.lives > 0 && player.lvl == 3)){
                init4();
            }
            if(player.win && player.lvl == 4 || (player.reset && player.lives > 0 && player.lvl == 4)){
                init5();
            }

            startTime = System.nanoTime();

            update();

            render();
            draw();

            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;

            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    //TODO there is definitely a better way to do this
    private void init() {
        running = true;

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) image.getGraphics();

        tileMap = new TileMap("res/level1.txt", 32);
        tileMap.loadTiles("res/floorfallTileSet.png");
        player = new Player(tileMap, tileMap.getMapWidth(), tileMap.getMapHeight(), 10, 5);
    }

    private void init2(){
        running = true;

        tileMap = new TileMap("res/level2.txt", 32);
        tileMap.loadTiles("res/floorfallTileSet.png");
        player = new Player(tileMap, tileMap.getMapWidth(), tileMap.getMapHeight(), 9, 1);
    }

    private void init3(){
        running = true;

        tileMap = new TileMap("res/level3.txt", 32);
        tileMap.loadTiles("res/floorfallTileSet.png");
        player = new Player(tileMap, tileMap.getMapWidth(), tileMap.getMapHeight(), 3, 14);
    }

    private void init4(){
        running = true;

        tileMap = new TileMap("res/level4.txt", 32);
        tileMap.loadTiles("res/floorfallTileSet.png");
        player = new Player(tileMap, tileMap.getMapWidth(), tileMap.getMapHeight(), 3, 21);
    }

    private void init5(){
        running = true;

        tileMap = new TileMap("res/level5.txt", 32);
        tileMap.loadTiles("res/floorfallTileSet.png");
        player = new Player(tileMap, tileMap.getMapWidth(), tileMap.getMapHeight(), 11, 22);

    }

    /////////////////

    //ONLY WORKS WITH SETTING RECTANGLE TO A COLOR?????
    private void update() {
        g.setColor(new Color(67, 120, 196));
        g.fillRect(0,0,WIDTH,HEIGHT);
        tileMap.update();
        player.update();
    }

    private void render() {
        tileMap.draw(g);
        player.draw(g);
        Sprite.drawArray(g, font, "FloorFall", new Vector2f(30,500), 64,64,22, 0);
        Sprite.drawArray(g, font, "Use the arrow keys to reach the", new Vector2f(30,570), 32,32,12, 0);
        Sprite.drawArray(g, font, "end after melting every tile", new Vector2f(30,612), 32,32,12, 0);
        Sprite.drawArray(g, font, "Lives", new Vector2f(600,515), 32,32,12, 0);
        Sprite.drawArray(g, font, "Ice can be walked on two times", new Vector2f(28,674), 32,32,12, 0);
        if(Player.lvl == 0){
            Sprite.drawArray(g, font, "Level one", new Vector2f(260,515), 32,32,12, 0);
        }
        if(Player.lvl == 1){
            Sprite.drawArray(g, font, "Level two", new Vector2f(260,515), 32,32,12, 0);
        }
        if(Player.lvl == 2){
            Sprite.drawArray(g, font, "Level three", new Vector2f(260,515), 32,32,12, 0);
        }
        if(Player.lvl == 3){
            Sprite.drawArray(g, font, "Level four", new Vector2f(260,515), 32,32,12, 0);
        }
        if(Player.lvl == 4){
            Sprite.drawArray(g, font, "Level five", new Vector2f(260,515), 32,32,12, 0);
        }
        if(gameOver){
            Sprite.drawArray(g, font, "GAME OVER", new Vector2f(190 ,250), 128,128,50, 0);
        }if(gameWin){
            Sprite.drawArray(g, font, "WINNER WINNER", new Vector2f(90 ,250), 128,128,50, 0);
            Sprite.drawArray(g, font, "CHICKEN DINNER", new Vector2f(80 ,360), 128,128,50, 0);
        }
        if(gameOver || gameWin){

        }
        Sprite.drawArray(g , lifeList, new Vector2f(570,550), 50,50,50,0);
    }


    private void draw() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) player.setLeft(true);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.setRight(true);
        if (e.getKeyCode() == KeyEvent.VK_UP) player.setUP(true);
        if (e.getKeyCode() == KeyEvent.VK_DOWN) player.setDown(true);

    }


}

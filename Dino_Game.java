import com.badlogic.gdx.ApplicationAdapter; 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer; 
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle; 
import com.badlogic.gdx.math.Circle; 
import com.badlogic.gdx.Input.Keys; 
import com.badlogic.gdx.math.Vector2; 
import com.badlogic.gdx.math.MathUtils; 
import com.badlogic.gdx.math.Intersector; 
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Texture; 
import com.badlogic.gdx.InputProcessor; 
import com.badlogic.gdx.*; 
import com.badlogic.gdx.utils.Array; 

public class Dino_Game extends ApplicationAdapter
{
    //instance variables

    private int timer;
    private int highScore;

    private Rectangle catus;
    private Rectangle bigCactus;
    private Rectangle bird;
    private Rectangle dino;
    
    private Rectangle startButton;
    private Texture start;
    private Texture startHighlight;
    
    private Texture image;
    private Texture leftFootDino;
    private Texture rightFootDino;
    private Texture deadDino;
    
    private final int GRAVITY = 1;
    private final int DINOSPEED = 10;

    private GameState gamestate;

    private OrthographicCamera camera; //the camera to our world
    private Viewport viewport; //maintains the ratios of your world
    private ShapeRenderer renderer; //used to draw textures and fonts 
    private BitmapFont font; //used to draw fonts (text)
    private SpriteBatch batch; //also needed to draw fonts (text)
    private static int jumpFactor = 20;
    private final int LEFT_FOOT = 1, RIGHT_FOOT = 2, NO_FOOT = 3;
    private static boolean topPointReached;
    private static int state; //example: dead, running, jumping



    public static final float WORLD_WIDTH = 400; 
    public static final float WORLD_HEIGHT = 400;
    public static final int STAND_STILL = 1, RUNNING = 2, JUMPING = 3, DIE = 4;
  


    //constructor
    
    public void create(){

        //find images put in same folder
        
        
        topPointReached = false;
        image = new Texture(Gdx.files.internal("Dino-stand.png"));
        leftFootDino = new Texture(Gdx.files.internal("Dino-left-up.png"));
        rightFootDino = new Texture(Gdx.files.internal("Dino-right-up.png"));
        deadDino = new Texture(Gdx.files.internal("Dino-big-eyes.png"));
        cactus1 = new Texture(Gdx.files.internal("Cactus-1"));
        cactus2 = new Texture(Gdx.files.internal("Cactus-2"));
        cactus3 = new Texture(Gdx.files.internal("Cactus-3"));
        cactus4 = new Texture(Gdx.files.internal("Cactus-4"));
        cactus5 = new Texture(Gdx.files.internal("Cactus-5"));
        ground = new Texture(Gdx.files.internal("Ground"));
        sun = new Texture(Gdx.files.internal("Sun"));
        
        
        
        camera = new OrthographicCamera(); //camera for our world, it is not moving
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera); //maintains world units from screen units
        renderer = new ShapeRenderer(); 
        font = new BitmapFont(); 
        batch = new SpriteBatch(); 
        gamestate = new GameState.MENU;
        timer = 0;
        highScore = 0;
        
        start = new Texture(Gdx.files.internal("start.png"));//make and upload picture 
        startHighlight = new Texture(Gdx.files.internal("startHighlight.png"));//make and upload picture
        startButton = new Rectangle(WORLD_WIDTH / 2 - 64, WORLD_HEIGHT / 2 - 64, 128, 128);//make and upload picture

        //all the inputed widths and heights for the objects might need to be changed later to fit the proportions
        bird = new Rectangle(50,15);
        bigCactus = new Rectangle(20,60);
        cactus = new Rectangle(20, 40);
        dino = new Rectangle(30, 50);
        gravity = 2;
        dinoSpeed = 2;
    
        
        
        
        
        
    }

    //renderer

    public void renderer(){
        if(gamestate == GameState.MENU){
            Vector2 clickLoc = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())); 
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin(); 
            if(!startButton.contains(clickLoc))
                batch.draw(start, 
                    startButton.x, 
                    startButton.y, 
                    startButton.width, 
                    startButton.height);
            else{
                batch.draw(startHighlight, 
                    startButton.x, 
                    startButton.y, 
                    startButton.width, 
                    startButton.height);
            }

            batch.end(); 
        }
        if(gamestate == GameState.GAME)
        {
            if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
                jump();
            }
        }
    }

    //logic for game

    public void jump(){
        //Add logic here
    }

    public void death(){
        if(dino.hasCollided())
        {
            state = DIE;
            endGame();
        }
    }

    public void startGame(){
        timer++;

        //Add more logic
    }

    public void endGame(){
        if(timer > highScore){
            highScore = timer;
        }
        //Add more logic
    }

    public boolean hasCollided(){
        if(dino.x == cacti.x && dino.y == cacti.y)
            return true;
        else
            return false;
    }

    public void reset(){
        timer = 0;
        //Add more logic
    }

}

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
    private int gravity;
    private int dinoSpeed;

    private OrthographicCamera camera; //the camera to our world
    private Viewport viewport; //maintains the ratios of your world
    private ShapeRenderer renderer; //used to draw textures and fonts 
    private BitmapFont font; //used to draw fonts (text)
    private SpriteBatch batch; //also needed to draw fonts (text)

    public static final float WORLD_WIDTH = 400; 
    public static final float WORLD_HEIGHT = 400;


    //constructor

    @Override
    public void create(){

    
        camera = new OrthographicCamera(); //camera for our world, it is not moving
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera); //maintains world units from screen units
        renderer = new ShapeRenderer(); 
        font = new BitmapFont(); 
        batch = new SpriteBatch(); 

        timer = 0;
        highScore = 0;

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
        if(keysJustPressed.SPACE){
            startGame();
            jump();
        }
        //add more logic
    }

    //logic for game

    public void jump(){
        //Add logic here
    }

    public void death(){
        if(dino.hasCollided)
            endGame();
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
        if(dino.getX == cacti.getX && dino.getY == cacti.getY)//fix this if needed, i think it needs it
            return true;
        else
            return false;
    }

    public void getX(){
        //add logic here
    }

    public void getY(){
        //add logic here
    }

    public void reset(){
        timer = 0;
        //Add more logic
    }

}

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
    private int state; //example: dead, running, jumping
    private int jumpFactor;

    private Rectangle rCactus1;
    private Rectangle rCactus2;
    private Rectangle rCactus3;
    private Rectangle rCactus4;
    private Rectangle rCactus5;
    private Rectangle bird;
    private Rectangle dino;
    private Rectangle startButton;

    // private Texture start;
    // private Texture startHighlight;
    private Texture dinoNorm;
    private Texture leftFootDino;
    private Texture rightFootDino;
    private Texture deadDino;
    private Texture tCactus1;
    private Texture tCactus2;
    private Texture tCactus3;
    private Texture tCactus4;
    private Texture tCactus5;
    private Texture ground;
    private Texture sun;
    private Texture background;

    private final int GRAVITY = 1;
    private final int DINOSPEED = 10;

    private GameState gamestate;

    private OrthographicCamera camera; //the camera to our world
    private Viewport viewport; //maintains the ratios of your world
    private ShapeRenderer renderer; //used to draw textures and fonts 
    private BitmapFont font; //used to draw fonts (text)
    private SpriteBatch batch; //also needed to draw fonts (text)
    private GlyphLayout layout;

    private boolean topPointReached;

    private final int LEFT_FOOT = 1;
    private final int RIGHT_FOOT = 2;
    private final int NO_FOOT = 3;
    private final int WORLD_WIDTH = 650; 
    private final int WORLD_HEIGHT = 350;
    private final int STAND_STILL = 1;
    private final int RUNNING = 2;
    private final int JUMPING = 3;
    private final int DIE = 4;

    //constructor    
    public void create(){

        //find images put in same folder

        topPointReached = false;

        // start = new Texture(Gdx.files.internal("start.png"));//make and upload picture 
        // startHighlight = new Texture(Gdx.files.internal("startHighlight.png"));//make and upload picture
        // startButton = new Rectangle(WORLD_WIDTH / 2 - 64, WORLD_HEIGHT / 2 - 64, 128, 128);//make and upload picture
        dinoNorm = new Texture(Gdx.files.internal("Dino-stand.png"));
        leftFootDino = new Texture(Gdx.files.internal("Dino-left-up.png"));
        rightFootDino = new Texture(Gdx.files.internal("Dino-right-up.png"));
        deadDino = new Texture(Gdx.files.internal("Dino-big-eyes.png"));
        tCactus1 = new Texture(Gdx.files.internal("Cactus-1.png"));
        tCactus2 = new Texture(Gdx.files.internal("Cactus-2.png"));
        tCactus3 = new Texture(Gdx.files.internal("Cactus-3.png"));
        tCactus4 = new Texture(Gdx.files.internal("Cactus-4.png"));
        tCactus5 = new Texture(Gdx.files.internal("Cactus-5.png"));
        ground = new Texture(Gdx.files.internal("Ground.png"));
        sun = new Texture(Gdx.files.internal("Sun.png"));
        // start = new Texture(Gdx.files.internal("startButton.png"));
        // startHighlight = new Texture(Gdx.files.internal("startHighlight.png"));
        background = new Texture(Gdx.files.internal("background.png"));

        camera = new OrthographicCamera(); //camera for our world, it is not moving
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera); //maintains world units from screen units
        renderer = new ShapeRenderer(); 
        font = new BitmapFont(); 
        batch = new SpriteBatch();
        layout = new GlyphLayout();

        gamestate = GameState.MENU;

        timer = 0;
        highScore = 0;
        jumpFactor = 20;

        //all the inputed widths and heights for the objects might need to be changed later to fit the proportions
        startButton = new Rectangle(WORLD_WIDTH/2, WORLD_HEIGHT/2, 50, 15);
        bird = new Rectangle(WORLD_WIDTH, WORLD_HEIGHT, 50, 15);
        rCactus1 = new Rectangle(WORLD_WIDTH, WORLD_HEIGHT, 20, 40);
        rCactus2 = new Rectangle(WORLD_WIDTH, WORLD_HEIGHT, 20, 40);
        rCactus3 = new Rectangle(WORLD_WIDTH, WORLD_HEIGHT, 20, 40);
        rCactus4 = new Rectangle(WORLD_WIDTH, WORLD_HEIGHT, 20, 40);
        rCactus5 = new Rectangle(WORLD_WIDTH, WORLD_HEIGHT, 20, 40);
        dino = new Rectangle(WORLD_WIDTH-200, WORLD_HEIGHT-200, 30, 50);

    }
    //renderer
    public void render(){
        viewport.apply();
        if(gamestate == GameState.MENU){
            // Vector2 clickLoc = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())); 
            // batch.setProjectionMatrix(viewport.getCamera().combined);
            // System.out.println(clickLoc.x + " " + clickLoc.y);
            layout.setText(font, "Press SPACE to Begin Game");
            batch.begin(); 
            
            
            
            batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);//background
            
            batch.draw(ground, 0, 135, WORLD_WIDTH, WORLD_HEIGHT/9);//ground
            
            batch.draw(dinoNorm, 0, 140, WORLD_WIDTH/12, WORLD_HEIGHT/7);//dino
            
            
            font.draw(batch, layout, WORLD_WIDTH/2 - layout.width/2, WORLD_HEIGHT/2 + layout.height/2);
            
            
            
            batch.end(); 
            
            // if(!startButton.contains(clickLoc))
            // batch.draw(start, 
            // startButton.x, 
            // startButton.y, 
            // startButton.width, 
            // startButton.height);
            // else{
            // batch.draw(startHighlight, 
            // startButton.x, 
            // startButton.y, 
            // startButton.width, 
            // startButton.height);

            if(Gdx.input.isKeyJustPressed(Keys.SPACE))
                gamestate = GameState.GAME;

        }
        if(gamestate == GameState.GAME)
        {
            jump();
        }
    }

    //logic for game

    public void jump(){
        if(Gdx.input.isKeyJustPressed(Keys.SPACE))
            jumpFactor += 5;
        else
            jumpFactor -= 0.6;

        dino.x += jumpFactor;
    }

    public void death(){
        if(hasCollided())
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
        reset();
        //Add more logic
    }

    public boolean hasCollided(){
        if(dino.x == rCactus1.x && dino.y == rCactus1.y
        && dino.x == rCactus2.x && dino.y == rCactus2.y
        && dino.x == rCactus3.x && dino.y == rCactus3.y
        && dino.x == rCactus4.x && dino.y == rCactus4.y
        && dino.x == rCactus5.x && dino.y == rCactus5.y)
            return true;
        else
            return false;
    }

    public void reset(){
        timer = 0;
        //Add more logic
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height, true); 
    }

    @Override
    public void dispose(){
        renderer.dispose(); 
        batch.dispose(); 
    }

}


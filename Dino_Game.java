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
    private int jumpFactor;
    private int startLocation;
    private int yVelocity;

    private Rectangle rCactus1;
    private Rectangle rCactus2;
    private Rectangle rCactus3;
    private Rectangle rCactus4;
    private Rectangle rCactus5;
    private Rectangle bird;
    private Rectangle dino;
    //private Rectangle startButton;
    private Rectangle ground;

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
    private Texture tGround;
    private Texture sun;
    private Texture background;

    private GameState gamestate;

    private OrthographicCamera camera; //the camera to our world
    private Viewport viewport; //maintains the ratios of your world
    private ShapeRenderer renderer; //used to draw textures and fonts 
    private BitmapFont font; //used to draw fonts (text)
    private SpriteBatch batch; //also needed to draw fonts (text)
    private GlyphLayout layout;

    private final int WORLD_WIDTH = 650; 
    private final int WORLD_HEIGHT = 350;

    private final int DINOSPEED = 100;

    //constructor    
    public void create(){

        //find images put in same folder

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
        tGround = new Texture(Gdx.files.internal("Ground.png"));
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

        startLocation = 140;
        timer = 0;
        highScore = 0;
        yVelocity = 0;

        //all the inputed widths and heights for the objects might need to be changed later to fit the proportions
        //startButton = new Rectangle(WORLD_WIDTH/2, WORLD_HEIGHT/2, 50, 15);
        bird = new Rectangle(WORLD_WIDTH, WORLD_HEIGHT, 50, 15);
        rCactus1 = new Rectangle(WORLD_WIDTH, WORLD_HEIGHT-210, WORLD_WIDTH/12, WORLD_HEIGHT/7);
        rCactus2 = new Rectangle(WORLD_WIDTH+1000, WORLD_HEIGHT-210, WORLD_WIDTH/12, WORLD_HEIGHT/7);
        rCactus3 = new Rectangle(WORLD_WIDTH+2000, WORLD_HEIGHT-210, WORLD_WIDTH/12, WORLD_HEIGHT/7);
        rCactus4 = new Rectangle(WORLD_WIDTH+3000, WORLD_HEIGHT-210, WORLD_WIDTH/12, WORLD_HEIGHT/7);
        rCactus5 = new Rectangle(WORLD_WIDTH+4000, WORLD_HEIGHT-210, WORLD_WIDTH/12, WORLD_HEIGHT/7);
        dino = new Rectangle(WORLD_WIDTH-600, WORLD_HEIGHT-210, WORLD_WIDTH/12, WORLD_HEIGHT/7);
        ground = new Rectangle(0, 135, WORLD_WIDTH, WORLD_HEIGHT/9);
    }
    //renderer
    public void render(){
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        if(gamestate == GameState.MENU){
            // Vector2 clickLoc = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())); 
            // batch.setProjectionMatrix(viewport.getCamera().combined);
            // System.out.println(clickLoc.x + " " + clickLoc.y);
            layout.setText(font, "Press ENTER to Begin Game");
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin(); 

            batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);//background
            batch.draw(tGround, 0, 135, WORLD_WIDTH, WORLD_HEIGHT/9);//ground
            batch.draw(dinoNorm, 50, 140, WORLD_WIDTH/12, WORLD_HEIGHT/7);//dino
            font.draw(batch, layout, WORLD_WIDTH/2 - layout.width/2, WORLD_HEIGHT/2 + layout.height/2);//font to the screen
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

            if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
                gamestate = GameState.GAME;
            }
        }
        if(gamestate == GameState.GAME)
        {
            timer += 0.01;  
            if(Gdx.input.isKeyJustPressed(Keys.SPACE))
            {
                //System.out.print(gamestate);
                dino.y += DINOSPEED;
            }
            dino.y -= 1.75;
            if(dino.y == startLocation){
                dino.y = 0;
            }
            if(dino.y < startLocation){
                dino.y = startLocation;
            }
            batch.begin();
            batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);//background
            batch.draw(tGround, 0, 135, WORLD_WIDTH, WORLD_HEIGHT/9);//ground
            batch.draw(dinoNorm, dino.x, dino.y, dino.width, dino.height);//dino
            batch.end();
            //if((int)(Math.random()*60+1) == 30){
            enemy();
            rCactus1.x -= 5;
            rCactus2.x -= 5;
            rCactus3.x -= 5;
            rCactus4.x -= 5;
            rCactus5.x -= 5;
            if(rCactus1.x < 0)
                rCactus1.x = WORLD_WIDTH;
            if(rCactus2.x < 0)
                rCactus2.x = WORLD_WIDTH;
            if(rCactus3.x < 0)
                rCactus3.x = WORLD_WIDTH;
            if(rCactus4.x < 0)
                rCactus4.x = WORLD_WIDTH;
            if(rCactus5.x < 0)
                rCactus5.x = WORLD_WIDTH;
            //}
            if(hasCollided()){
                endGame();
            }
        }
    }
    //logic for game
    public void enemy(){
        int rand = (int)(Math.random()*5+1);
        if(rand == 1){
            batch.begin();
            batch.draw(tCactus1, rCactus1.x, rCactus1.y, rCactus1.width, rCactus1.height);
            batch.end();
        }
        else if(rand == 2){
            batch.begin();
            batch.draw(tCactus2, rCactus2.x, rCactus2.y, rCactus2.width, rCactus2.height);
            batch.end();
        }
        else if(rand == 3){
            batch.begin();
            batch.draw(tCactus3, rCactus3.x, rCactus3.y, rCactus3.width, rCactus3.height);
            batch.end();
        }
        else if(rand == 4){
            batch.begin();
            batch.draw(tCactus4, rCactus4.x, rCactus4.y, rCactus4.width, rCactus4.height);
            batch.end();
        }
        else{
            batch.begin();
            batch.draw(tCactus5, rCactus5.x, rCactus5.y, rCactus5.width, rCactus5.height);
            batch.end();
        }
    }

    public void endGame(){
        if(timer > highScore){
            highScore = timer;
        }
        reset();
    }

    public boolean hasCollided(){
        if(dino.x == rCactus1.x || dino.x == rCactus2.x || dino.x == rCactus3.x || dino.x == rCactus4.x || dino.x == rCactus5.x){
            return true;
        }
        else{
            return false;
        }
    }

    public void reset(){
        timer = 0;
        gamestate = GameState.MENU;
        rCactus1.x = WORLD_WIDTH;
        rCactus2.x = WORLD_WIDTH;
        rCactus3.x = WORLD_WIDTH;
        rCactus4.x = WORLD_WIDTH;
        rCactus5.x = WORLD_WIDTH;
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

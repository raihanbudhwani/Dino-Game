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
    private float yVel; 
    private float timer;
    private float highScore;
    private int maxHeight;
    private int cactusSpeed;

    private Rectangle rCactus1;
    private Rectangle rCactus2;
    private Rectangle rCactus3;
    private Rectangle rCactus4;
    private Rectangle rCactus5;
    private Rectangle rBird;
    private Rectangle dino;
    private Rectangle ground;
    private Rectangle hitBox;

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
    private Texture tBird;
    private Texture background;

    private GameState gamestate;

    private OrthographicCamera camera; //the camera to our world
    private Viewport viewport; //maintains the ratios of your world
    private ShapeRenderer renderer; //used to draw textures and fonts 
    private BitmapFont font; //used to draw fonts (text)
    private SpriteBatch batch; //also needed to draw fonts (text)
    private GlyphLayout layout;
    private GlyphLayout layout2;

    private final int WORLD_WIDTH = 650; 
    private final int WORLD_HEIGHT = 350;
    private final float GRAVITY = .5f; 

    private final int DINOSPEED = 200;
    //constructor    
    public void create(){
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
        background = new Texture(Gdx.files.internal("background.png"));
        tBird = new Texture(Gdx.files.internal("bird.png"));

        camera = new OrthographicCamera(); //camera for our world, it is not moving
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera); //maintains world units from screen units
        renderer = new ShapeRenderer(); 
        font = new BitmapFont(); 
        batch = new SpriteBatch();
        layout = new GlyphLayout();
        layout2 = new GlyphLayout();

        gamestate = GameState.MENU;

        timer = 0;
        highScore = 0;
        maxHeight = 140;
        cactusSpeed = 5;
        yVel = 0; 

        rBird = new Rectangle(WORLD_WIDTH+5000, WORLD_HEIGHT-150, WORLD_WIDTH/10, WORLD_HEIGHT/7);
        rCactus1 = new Rectangle(WORLD_WIDTH+500, WORLD_HEIGHT-210, WORLD_WIDTH/12-10, WORLD_HEIGHT/7);
        rCactus2 = new Rectangle(WORLD_WIDTH+1000, WORLD_HEIGHT-210, WORLD_WIDTH/12-10, WORLD_HEIGHT/7);
        rCactus3 = new Rectangle(WORLD_WIDTH+1500, WORLD_HEIGHT-210, WORLD_WIDTH/12-10, WORLD_HEIGHT/7);
        rCactus4 = new Rectangle(WORLD_WIDTH+2000, WORLD_HEIGHT-210, WORLD_WIDTH/12-10, WORLD_HEIGHT/7);
        rCactus5 = new Rectangle(WORLD_WIDTH+2500, WORLD_HEIGHT-210, WORLD_WIDTH/12-10, WORLD_HEIGHT/7);
        dino = new Rectangle(WORLD_WIDTH-600, WORLD_HEIGHT-210, WORLD_WIDTH/12-10, WORLD_HEIGHT/7);
        ground = new Rectangle(0, 135, WORLD_WIDTH, WORLD_HEIGHT/9);
        hitBox = new Rectangle(WORLD_WIDTH-600, WORLD_HEIGHT-210, WORLD_WIDTH/12, WORLD_HEIGHT/7);
    }

    //renderer
    public void render(){
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        if(gamestate == GameState.MENU){
            layout.setText(font, "Press ENTER to Begin Game");
            layout2.setText(font, "Press ESC for Instructions");
            batch.setProjectionMatrix(viewport.getCamera().combined);

            batch.begin();
            batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);//background
            batch.draw(tGround, 0, 135, WORLD_WIDTH, WORLD_HEIGHT/9);//ground
            batch.draw(sun, 570, 270, WORLD_WIDTH/5, WORLD_HEIGHT/3); //sun
            batch.draw(dinoNorm, dino.x, dino.y, dino.width, dino.height);//dino
            font.draw(batch, layout, WORLD_WIDTH/2 - layout.width/2, WORLD_HEIGHT/2 + layout.height/2);//font to the screen
            font.draw(batch, layout2, WORLD_WIDTH/2 - layout2.width/2, ((WORLD_HEIGHT/3)+10) + layout.height/2);
            batch.end();

            if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
                gamestate = GameState.GAME;
            }
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
                gamestate = GameState.INSTRUCTIONS;
            }
        }

        if(gamestate == GameState.GAMEOVER){
            dino.x = WORLD_WIDTH-600;
            dino.y = WORLD_HEIGHT-210;
            layout.setText(font, "Press ENTER to Play Again or ESCAPE to go to the Menu");
            batch.begin();
            batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);//background
            batch.draw(tGround, 0, 135, WORLD_WIDTH, WORLD_HEIGHT/9);//ground
            batch.draw(sun, 570, 270, WORLD_WIDTH/5, WORLD_HEIGHT/3); //sun
            batch.draw(deadDino, dino.x, dino.y, dino.width, dino.height);
            font.draw(batch, layout, WORLD_WIDTH/2 - layout.width/2, WORLD_HEIGHT/2 + layout.height/2);
            batch.end();
            if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
                gamestate = GameState.GAME;
            }
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
                gamestate = GameState.MENU;
            }
        }

        if(gamestate == GameState.INSTRUCTIONS){
            layout.setText(font, "Press SPACE to Jump Over the Cacti and Don't Get Hit!!");
            layout2.setText(font, "Press SPACE to go Back to Menu");
            batch.begin();
            font.draw(batch, layout, WORLD_WIDTH/2 - layout.width/2, WORLD_HEIGHT/2 + layout.height/2);
            font.draw(batch, layout2, WORLD_WIDTH/2 - layout2.width/2, ((WORLD_HEIGHT/3)+10) + layout.height/2);
            batch.end();
            if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
                gamestate = GameState.MENU;
            }    
        }

        if(gamestate == GameState.GAME)
        {     
            timer += 0.017;
            layout.setText(font, "HI    " + highScore + "    " + timer);
            font.draw(batch, layout, WORLD_WIDTH/2 - layout.width/2, WORLD_HEIGHT/2 + layout.height/2); //change this later
            if(Gdx.input.isKeyJustPressed(Keys.SPACE))
            {
                batch.begin();
                batch.draw(dinoNorm, dino.x, dino.y, dino.width, dino.height);
                batch.end();
                if(dino.y == maxHeight){
                    yVel = 10;
                }
            } 
            yVel -= GRAVITY; 
            dino.y += yVel;        
            if(dino.y < maxHeight){//don't fall through the ground
                dino.y = maxHeight;
            }

            batch.begin();
            batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);//background
            batch.draw(tGround, 0, 135, WORLD_WIDTH, WORLD_HEIGHT/9);//ground
            batch.draw(sun, 570, 270, WORLD_WIDTH/5, WORLD_HEIGHT/3); //sun
            batch.draw(dinoNorm, dino.x, dino.y, dino.width, dino.height);

            //batch.draw(leftFootDino, dino.x, dino.y, dino.width, dino.height);
            //batch.draw(rightFootDino, dino.x, dino.y, dino.width, dino.height);

            batch.draw(tCactus1, rCactus1.x, rCactus1.y, rCactus1.width, rCactus1.height);
            batch.draw(tCactus2, rCactus2.x, rCactus2.y, rCactus2.width, rCactus2.height);
            batch.draw(tCactus3, rCactus3.x, rCactus3.y, rCactus3.width, rCactus3.height);
            batch.draw(tCactus4, rCactus4.x, rCactus4.y, rCactus4.width, rCactus4.height);
            batch.draw(tCactus5, rCactus5.x, rCactus5.y, rCactus5.width, rCactus5.height);
            batch.draw(tBird, rBird.x, rBird.y, rBird.width, rBird.height);

            batch.end();

            rCactus1.x -= cactusSpeed;
            rCactus2.x -= cactusSpeed;
            rCactus3.x -= cactusSpeed;
            rCactus4.x -= cactusSpeed;
            rCactus5.x -= cactusSpeed;

            rBird.x -= 5;

            if(rCactus1.x < 0){
                int rand = (int)(Math.random()*4000+200);
                rCactus1.x = WORLD_WIDTH+rand;
            }

            if(rCactus2.x < 0){
                int rand = (int)(Math.random()*4000+200);
                rCactus2.x = WORLD_WIDTH+rand;
            }

            if(rCactus3.x < 0){
                int rand = (int)(Math.random()*4000+200);              
                rCactus3.x = WORLD_WIDTH+rand;
            }

            if(rCactus4.x < 0){
                int rand = (int)(Math.random()*4000+200);
                rCactus4.x = WORLD_WIDTH+rand;
            }

            if(rCactus5.x < 0){
                int rand = (int)(Math.random()*4000+200);
                rCactus5.x = WORLD_WIDTH+rand;
            }

            if(rBird.x < 0){
                int rand = (int)(Math.random()*5000+200);              
                rBird.x = WORLD_WIDTH+rand;
            }

            if(rCactus1.x + 75 >= rCactus2.x || rCactus1.x + 75 >= rCactus3.x || rCactus1.x + 75 >= rCactus4.x || rCactus1.x + 75 >= rCactus5.x
            || rCactus1.x - 75 >= rCactus2.x || rCactus1.x - 75 >= rCactus3.x || rCactus1.x - 75 >= rCactus4.x || rCactus1.x - 75 >= rCactus5.x)
            {
                int rand = (int)(Math.random()*4000+200);
                rCactus1.x = WORLD_WIDTH+rand;
            }

            if(rCactus2.x + 75 >= rCactus1.x || rCactus2.x + 75 >= rCactus3.x || rCactus2.x + 75 >= rCactus4.x || rCactus2.x + 75 >= rCactus5.x
            || rCactus2.x - 75 >= rCactus1.x || rCactus2.x - 75 >= rCactus3.x || rCactus2.x - 75 >= rCactus4.x || rCactus2.x - 75 >= rCactus5.x)
            {
                int rand = (int)(Math.random()*4000+200);
                rCactus2.x = WORLD_WIDTH+rand;
            }

            if(rCactus3.x + 75 >= rCactus2.x || rCactus3.x + 75 >= rCactus1.x || rCactus3.x + 75 >= rCactus4.x || rCactus3.x + 75 >= rCactus5.x
            || rCactus3.x - 75 >= rCactus2.x || rCactus3.x - 75 >= rCactus1.x || rCactus3.x - 75 >= rCactus4.x || rCactus3.x - 75 >= rCactus5.x)
            {
                int rand = (int)(Math.random()*4000+200);
                rCactus3.x = WORLD_WIDTH+rand;
            }

            if(rCactus4.x + 75 >= rCactus2.x || rCactus4.x + 75 >= rCactus3.x || rCactus4.x + 75 >= rCactus1.x || rCactus4.x + 75 >= rCactus5.x
            || rCactus4.x - 75 >= rCactus2.x || rCactus4.x - 75 >= rCactus3.x || rCactus4.x - 75 >= rCactus1.x || rCactus4.x - 75 >= rCactus5.x)
            {
                int rand = (int)(Math.random()*4000+200);
                rCactus4.x = WORLD_WIDTH+rand;
            }

            if(rCactus5.x + 75 >= rCactus2.x || rCactus5.x + 75 >= rCactus3.x || rCactus5.x + 75 >= rCactus4.x || rCactus5.x + 75 >= rCactus1.x
            || rCactus5.x - 75 >= rCactus2.x || rCactus5.x - 75 >= rCactus3.x || rCactus5.x - 75 >= rCactus4.x || rCactus5.x - 75 >= rCactus1.x)
            {
                int rand = (int)(Math.random()*4000+200);
                rCactus5.x = WORLD_WIDTH+rand;
            }

            if(hasCollided()){
                gamestate = GameState.GAMEOVER;
                endGame();                
            }
        }
    }

    //logic for game
    public void endGame(){
        if(timer > highScore){
            highScore = timer;
        }
        reset();
    }

    //Change the y and x of the rectangles to the top right instead of top left
    public boolean hasCollided(){
        if(dino.overlaps(rCactus1) || dino.overlaps(rCactus2) || dino.overlaps(rCactus3) || dino.overlaps(rCactus4) || dino.overlaps(rCactus5)
        || dino.overlaps(rBird)){
            return true;
        }
        else{
            return false;
        }
    }

    public void reset(){
        timer = 0;
        rBird.x = WORLD_WIDTH+5000;
        rCactus1.x = WORLD_WIDTH+500;
        rCactus2.x = WORLD_WIDTH+1000;
        rCactus3.x = WORLD_WIDTH+1500;
        rCactus4.x = WORLD_WIDTH+2000;
        rCactus5.x = WORLD_WIDTH+2500;
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

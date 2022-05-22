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
import java.util.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.scenes.scene2d.*;
import java.awt.Image;

//import java.awt

//FIX THESE THINGS!!!!!!
//dino step
//ground gaps
//clouds = done
//explosion easter egg
//sounds --> explosion and jump and dead
//jump = done, dead = done, explosion = wierd sound keeps repeating
//bird generation

public class Dino_Game extends ApplicationAdapter
{
    //instance variables
    private float yVel; 
    private float timer;
    private float highScore;
    private int maxHeight;
    private int cactusSpeed;

    private Sound jumpSound;
    private Sound deadSound;
    private Sound hundoScore;
    private Sound explosionSound;
    private Music music;

    private Rectangle rCactus1;
    private Rectangle rCactus2;
    private Rectangle rCactus3;
    private Rectangle rCactus4;
    private Rectangle rCactus5;
    private Rectangle rBird;
    private Rectangle dino;
    private Rectangle ground;
    private Rectangle ground2;
    private Rectangle ground3;
    private Rectangle hitBox;
    private Rectangle rMeteor;
    private Rectangle rCloud1;
    private Rectangle rCloud2;
    private Rectangle rCloud3;

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
    private Texture tGround2;
    private Texture tGround3;
    private Texture sun;
    private Texture tBird;
    private Texture background;
    private Texture background2;
    private Texture background3;
    private Texture tMeteor;
    private Texture explosion;
    private Texture tCloud1;
    private Texture tCloud2;
    private Texture tCloud3;

    private Texture dinosaur;

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
        dinosaur = new Texture(Gdx.files.internal("Dino-stand.png"));

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
        tGround2 = new Texture(Gdx.files.internal("Ground.png"));
        tGround3 = new Texture(Gdx.files.internal("Ground.png"));
        sun = new Texture(Gdx.files.internal("Sun.png"));
        background = new Texture(Gdx.files.internal("background.png"));
        tBird = new Texture(Gdx.files.internal("bird.png"));
        tMeteor = new Texture(Gdx.files.internal("meteor.png"));
        explosion = new Texture(Gdx.files.internal("explosion.png"));
        tCloud1 = new Texture(Gdx.files.internal("cloud.png"));
        tCloud2 = new Texture(Gdx.files.internal("cloud.png"));
        tCloud3 = new Texture(Gdx.files.internal("cloud.png"));

        //sounds
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        deadSound = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        hundoScore = Gdx.audio.newSound(Gdx.files.internal("hundoScore.wav"));

        camera = new OrthographicCamera(); //camera for our world, it is not moving
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera); //maintains world units from screen units
        renderer = new ShapeRenderer(); 
        font = new BitmapFont(Gdx.files.internal("score.fnt")); 
        batch = new SpriteBatch();
        layout = new GlyphLayout();
        layout2 = new GlyphLayout();

        gamestate = GameState.MENU;

        timer = 0;
        highScore = 0;
        maxHeight = 140;
        cactusSpeed = 0;
        yVel = 0; 

        rMeteor = new Rectangle(WORLD_WIDTH/2+100, WORLD_HEIGHT/2+300, WORLD_WIDTH/5, WORLD_HEIGHT/3);
        rCactus1 = new Rectangle(WORLD_WIDTH+500, WORLD_HEIGHT-210, WORLD_WIDTH/12-10, WORLD_HEIGHT/7);
        rCactus2 = new Rectangle(WORLD_WIDTH+1000, WORLD_HEIGHT-210, WORLD_WIDTH/12-10, WORLD_HEIGHT/7);
        rCactus3 = new Rectangle(WORLD_WIDTH+1500, WORLD_HEIGHT-210, WORLD_WIDTH/12-10, WORLD_HEIGHT/7);
        rCactus4 = new Rectangle(WORLD_WIDTH+2000, WORLD_HEIGHT-210, WORLD_WIDTH/12-10, WORLD_HEIGHT/7);
        rCactus5 = new Rectangle(WORLD_WIDTH+2500, WORLD_HEIGHT-210, WORLD_WIDTH/12-10, WORLD_HEIGHT/7);
        rBird = new Rectangle(WORLD_WIDTH+3000, WORLD_HEIGHT-150, WORLD_WIDTH/10, WORLD_HEIGHT/7);
        dino = new Rectangle(WORLD_WIDTH-600, WORLD_HEIGHT-210, WORLD_WIDTH/12-10, WORLD_HEIGHT/7);
        ground = new Rectangle(0, 135, WORLD_WIDTH, WORLD_HEIGHT/9);
        ground2 = new Rectangle(WORLD_WIDTH, 135, WORLD_WIDTH+30, WORLD_HEIGHT/9);
        ground3 = new Rectangle(WORLD_WIDTH*2, 135, WORLD_WIDTH+30, WORLD_HEIGHT/9);
        rCloud1 = new Rectangle(385, 220, WORLD_WIDTH/5, WORLD_HEIGHT/5); //cloud
        rCloud2 = new Rectangle(250, 275, WORLD_WIDTH/5, WORLD_HEIGHT/5); //cloud
        rCloud3 = new Rectangle(100, 230, WORLD_WIDTH/5, WORLD_HEIGHT/5); //cloud

    }

    //renderer
    public void render(){
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        if(gamestate == GameState.MENU){

            GlyphLayout scoreLayout = new GlyphLayout(font, "" + timer);

            font.getData().setScale(0.5f, 0.5f); //size of font

            layout.setText(font, "Press ENTER to Begin Game");
            layout2.setText(font, "Press ESC for Instructions");
            batch.setProjectionMatrix(viewport.getCamera().combined);

            batch.begin();
            batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);//background
            batch.draw(tGround, 0, 135, WORLD_WIDTH, WORLD_HEIGHT/9);//ground
            batch.draw(sun, 570, 270, WORLD_WIDTH/5, WORLD_HEIGHT/3); //sun
            batch.draw(dinoNorm, dino.x, dino.y, dino.width, dino.height);//dino
            batch.draw(tCloud1, 385, 220, WORLD_WIDTH/5, WORLD_HEIGHT/5);//cloud
            batch.draw(tCloud2,250, 275, WORLD_WIDTH/5, WORLD_HEIGHT/5);//cloud
            batch.draw(tCloud3,100, 230, WORLD_WIDTH/5, WORLD_HEIGHT/5);//cloud
            font.draw(batch, layout,115, 185);//font to the screen
            font.draw(batch, layout2, 115, 135);//font to the screen
            batch.end();

            if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
                gamestate = GameState.GAME;
            }
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
                gamestate = GameState.INSTRUCTIONS;
            }
        }

        if(gamestate == GameState.GAMEOVER){
            if(timer >= 1000){
                gamestate = GameState.EASTEREGG;
            }
            font.getData().setScale(0.5f, 0.5f);

            layout2.setText(font, "HI " + (int)highScore + "  " + (int)timer);

            dino.x = WORLD_WIDTH-600;
            dino.y = WORLD_HEIGHT-210;
            layout.setText(font, "Press ENTER to Play Again or\n \n ESCAPE to go to the Menu");
            batch.begin();
            batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);//background
            batch.draw(tGround, 0, 135, WORLD_WIDTH, WORLD_HEIGHT/9);//ground
            batch.draw(tGround2, WORLD_WIDTH, 135, WORLD_WIDTH, WORLD_HEIGHT/9);
            batch.draw(tGround3, WORLD_WIDTH*2, 135, WORLD_WIDTH, WORLD_HEIGHT/9);
            batch.draw(sun, 570, 270, WORLD_WIDTH/5, WORLD_HEIGHT/3); //sun
            batch.draw(tCloud1, rCloud1.x, rCloud1.y, rCloud1.width, rCloud1.height);//cloud
            batch.draw(tCloud2, rCloud2.x, rCloud2.y, rCloud2.width, rCloud2.height);//cloud
            batch.draw(tCloud3, rCloud3.x, rCloud3.y, rCloud3.width, rCloud3.height);//cloud
            batch.draw(deadDino, dino.x, dino.y, dino.width, dino.height);
            font.draw(batch, layout,100,185);
            font.draw(batch, layout2, 393, WORLD_HEIGHT-67 + layout.height/2);

            batch.end();

            timer = 0;
            if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
                gamestate = GameState.GAME;
            }
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
                gamestate = GameState.MENU;
            }
        }

        if(gamestate == GameState.INSTRUCTIONS){
            font.getData().setScale(0.5f, 0.5f);
            layout.setText(font, "Press SPACE to Jump Over the Cacti \n       and Don't Get Hit!!");
            layout2.setText(font, "Press SPACE to go Back to Menu");
            batch.begin();
            font.draw(batch, layout,50,235);
            font.draw(batch, layout2,50,135);
            batch.end();
            if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
                gamestate = GameState.MENU;
            }    
        }

        if(gamestate == GameState.EASTEREGG){
            layout.setText(font, "HI " + (int)highScore + "  " + (int)timer);
            layout2.setText(font, "Press ENTER to Play Again or\n \n ESCAPE to go to the Menu");
            if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
                gamestate = GameState.GAME;
            }
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
                gamestate = GameState.MENU;
            }

            batch.begin();
            batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);//background
            batch.draw(tGround, 0, 135, WORLD_WIDTH, WORLD_HEIGHT/9);//ground
            batch.draw(tGround2, WORLD_WIDTH, 135, WORLD_WIDTH, WORLD_HEIGHT/9);
            batch.draw(tGround3, WORLD_WIDTH*2, 135, WORLD_WIDTH, WORLD_HEIGHT/9);
            batch.draw(sun, 570, 270, WORLD_WIDTH/5, WORLD_HEIGHT/3); //sun
            batch.draw(deadDino, dino.x, dino.y, dino.width, dino.height);
            batch.draw(tMeteor, rMeteor.x, rMeteor.y, rMeteor.width, rMeteor.height);
            batch.end();
            rMeteor.x -= 10;
            rMeteor.y -= 10;

            long id = explosionSound.play(1.0f);
            explosionSound.setLooping(id, false);

            if(rMeteor.overlaps(dino)){
                rMeteor.x = WORLD_WIDTH-600;
                rMeteor.y = WORLD_HEIGHT-210;

                batch.begin();
                batch.draw(explosion, 230, 135, 200, 200);
                batch.end();

            }
            batch.begin();
            font.draw(batch, layout, 365, 306);
            font.draw(batch, layout2,100,185);
            batch.end();
        }

        if(gamestate == GameState.GAME)
        {     

            timer += 0.01;
            layout.setText(font, "HI " + (int)highScore + "  " + (int)timer);

            if(Gdx.input.isKeyJustPressed(Keys.SPACE))
            {
                long id = jumpSound.play(1.0f);
                //sound.setPitch(id, 2);
                jumpSound.setLooping(id, false);
                // batch.begin();
                // batch.draw(dinoNorm, dino.x, dino.y, dino.width, dino.height);
                // batch.end();
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
            batch.draw(tGround, ground.x, ground.y, ground.width, ground.height);//ground
            batch.draw(tGround2, ground2.x, ground2.y, ground2.width, ground2.height);
            batch.draw(tGround3, ground3.x, ground3.y, ground3.width, ground3.height);
            batch.draw(tCloud1, rCloud1.x, rCloud1.y, rCloud1.width, rCloud1.height);//cloud
            batch.draw(tCloud2, rCloud2.x, rCloud2.y, rCloud2.width, rCloud2.height);//cloud
            batch.draw(tCloud3, rCloud3.x, rCloud3.y, rCloud3.width, rCloud3.height);//cloud
            //batch.draw(dinoNorm, dino.x, dino.y, dino.width, dino.height);//dino
            batch.draw(sun, 570, 270, WORLD_WIDTH/5, WORLD_HEIGHT/3); //sun

            font.draw(batch, layout, WORLD_WIDTH-200 - layout.width/2, WORLD_HEIGHT-50 + layout.height/2); //change this later

            batch.draw(dinosaur, dino.x, dino.y, dino.width, dino.height); //////// DINO RUNNNNNN, SWAP LEFT RIGHT FOOT

            if(Gdx.input.isKeyJustPressed(Keys.R))
            {
                //System.out.println("right");
                dinosaur = new Texture(Gdx.files.internal("Dino-right-up.png"));

            }
            else if(Gdx.input.isKeyJustPressed(Keys.L))
            {
                //System.out.println("left");
                dinosaur = new Texture(Gdx.files.internal("Dino-left-up.png"));

            }

            //batch.draw(leftFootDino, dino.x, dino.y, dino.width, dino.height);
            // batch.draw(rightFootDino, dino.x, dino.y, dino.width, dino.height);

            batch.draw(tCactus1, rCactus1.x, rCactus1.y, rCactus1.width, rCactus1.height);
            batch.draw(tCactus2, rCactus2.x, rCactus2.y, rCactus2.width, rCactus2.height);
            batch.draw(tCactus3, rCactus3.x, rCactus3.y, rCactus3.width, rCactus3.height);
            batch.draw(tCactus4, rCactus4.x, rCactus4.y, rCactus4.width, rCactus4.height);
            batch.draw(tCactus5, rCactus5.x, rCactus5.y, rCactus5.width, rCactus5.height);
            batch.draw(tBird, rBird.x, rBird.y, rBird.width, rBird.height);

            batch.end();
            rCloud1.x -= 1;
            rCloud2.x -= 1;
            rCloud3.x -= 1;

            if(timer < 10){

                cactusSpeed = 6;
                rCactus1.x -= cactusSpeed;
                rCactus2.x -= cactusSpeed;
                rCactus3.x -= cactusSpeed;
                rCactus4.x -= cactusSpeed;
                rCactus5.x -= cactusSpeed;
                rBird.x -= cactusSpeed;
                ground.x -= cactusSpeed;
                ground2.x -= cactusSpeed;
                ground3.x -= cactusSpeed;
                if(ground.x+(WORLD_WIDTH-10) <= 0)
                    ground.x = WORLD_WIDTH*2;
                if(ground2.x+(WORLD_WIDTH-10) < 0)
                    ground2.x = WORLD_WIDTH*2;
                if(ground3.x+(WORLD_WIDTH-10) < 0)
                    ground3.x = WORLD_WIDTH*2;
            }

            if(timer >= 10 && timer < 20){
                cactusSpeed = 7;
                rCactus1.x -= cactusSpeed;
                rCactus2.x -= cactusSpeed;
                rCactus3.x -= cactusSpeed;
                rCactus4.x -= cactusSpeed;
                rCactus5.x -= cactusSpeed;
                rBird.x -= cactusSpeed;
                ground.x -= cactusSpeed;
                ground2.x -= cactusSpeed;
                ground3.x -= cactusSpeed;
                if(ground.x+(WORLD_WIDTH-10) <= 0)
                    ground.x = WORLD_WIDTH*2;
                if(ground2.x+(WORLD_WIDTH-10) < 0)
                    ground2.x = WORLD_WIDTH*2;
                if(ground3.x+(WORLD_WIDTH-10) < 0)
                    ground3.x = WORLD_WIDTH*2;
            }

            if(timer >= 20 && timer < 30){
                cactusSpeed = 8;
                rCactus1.x -= cactusSpeed;
                rCactus2.x -= cactusSpeed;
                rCactus3.x -= cactusSpeed;
                rCactus4.x -= cactusSpeed;
                rCactus5.x -= cactusSpeed;
                rBird.x -= cactusSpeed;
                ground.x -= cactusSpeed;
                ground2.x -= cactusSpeed;
                ground3.x -= cactusSpeed;
                if(ground.x+(WORLD_WIDTH-10) <= 0)
                    ground.x = WORLD_WIDTH*2;
                if(ground2.x+(WORLD_WIDTH-10) < 0)
                    ground2.x = WORLD_WIDTH*2;
                if(ground3.x+(WORLD_WIDTH-10) < 0)
                    ground3.x = WORLD_WIDTH*2;
            }

            if(timer >= 30 && timer < 40){
                cactusSpeed = 9;
                rCactus1.x -= cactusSpeed;
                rCactus2.x -= cactusSpeed;
                rCactus3.x -= cactusSpeed;
                rCactus4.x -= cactusSpeed;
                rCactus5.x -= cactusSpeed;
                rBird.x -= cactusSpeed;
                ground.x -= cactusSpeed;
                ground2.x -= cactusSpeed;
                ground3.x -= cactusSpeed;
                if(ground.x+(WORLD_WIDTH-10) <= 0)
                    ground.x = WORLD_WIDTH*2;
                if(ground2.x+(WORLD_WIDTH-10) < 0)
                    ground2.x = WORLD_WIDTH*2;
                if(ground3.x+(WORLD_WIDTH-10) < 0)
                    ground3.x = WORLD_WIDTH*2;
            }

            if(timer >= 40 && timer < 50){
                cactusSpeed = 10;
                rCactus1.x -= cactusSpeed;
                rCactus2.x -= cactusSpeed;
                rCactus3.x -= cactusSpeed;
                rCactus4.x -= cactusSpeed;
                rCactus5.x -= cactusSpeed;
                rBird.x -= cactusSpeed;
                ground.x -= cactusSpeed;
                ground2.x -= cactusSpeed;
                ground3.x -= cactusSpeed;
                if(ground.x+(WORLD_WIDTH-10) <= 0)
                    ground.x = WORLD_WIDTH*2;
                if(ground2.x+(WORLD_WIDTH-10) < 0)
                    ground2.x = WORLD_WIDTH*2;
                if(ground3.x+(WORLD_WIDTH-10) < 0)
                    ground3.x = WORLD_WIDTH*2;
            }

            if(timer >= 50 && timer < 60){
                cactusSpeed = 11;
                rCactus1.x -= cactusSpeed;
                rCactus2.x -= cactusSpeed;
                rCactus3.x -= cactusSpeed;
                rCactus4.x -= cactusSpeed;
                rCactus5.x -= cactusSpeed;
                rBird.x -= cactusSpeed;
                ground.x -= cactusSpeed;
                ground2.x -= cactusSpeed;
                ground3.x -= cactusSpeed;
                if(ground.x+(WORLD_WIDTH-10) <= 0)
                    ground.x = WORLD_WIDTH*2;
                if(ground2.x+(WORLD_WIDTH-10) < 0)
                    ground2.x = WORLD_WIDTH*2;
                if(ground3.x+(WORLD_WIDTH-10) < 0)
                    ground3.x = WORLD_WIDTH*2;
            }

            if(timer >= 60){
                cactusSpeed = 12;
                rCactus1.x -= cactusSpeed;
                rCactus2.x -= cactusSpeed;
                rCactus3.x -= cactusSpeed;
                rCactus4.x -= cactusSpeed;
                rCactus5.x -= cactusSpeed;
                rBird.x -= cactusSpeed;
                ground.x -= cactusSpeed;
                ground2.x -= cactusSpeed;
                ground3.x -= cactusSpeed;
                if(ground.x+(WORLD_WIDTH-10) <= 0)
                    ground.x = WORLD_WIDTH*2;
                if(ground2.x+(WORLD_WIDTH-10) < 0)
                    ground2.x = WORLD_WIDTH*2;
                if(ground3.x+(WORLD_WIDTH-10) < 0)
                    ground3.x = WORLD_WIDTH*2;
            }

            if(rCloud1.x < 0){
                int rand = (int)(Math.random()*1000+100);
                rCloud1.x = WORLD_WIDTH+rand;
            }

            if(rCloud2.x < 0){
                int rand = (int)(Math.random()*1000+100);
                rCloud2.x = WORLD_WIDTH+rand;
            }

            if(rCloud3.x < 0){
                int rand = (int)(Math.random()*1000+100);
                rCloud3.x = WORLD_WIDTH+rand;
            }

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
                int rand = (int)(Math.random()*4000+200);              
                rBird.x = WORLD_WIDTH+rand;
            }

            if(rCactus1.x + 50 >= rCactus2.x || rCactus1.x + 50 >= rCactus3.x || rCactus1.x + 50 >= rCactus4.x || rCactus1.x + 50 >= rCactus5.x || rCactus1.x + 50 >= rBird.x
            || rCactus1.x - 50 >= rCactus2.x || rCactus1.x - 50 >= rCactus3.x || rCactus1.x - 50 >= rCactus4.x || rCactus1.x - 50 >= rCactus5.x || rCactus1.x - 50 >= rBird.x)
            {
                int rand = (int)(Math.random()*4000+200);
                rCactus1.x = WORLD_WIDTH+rand;

            }

            if(rCactus2.x + 50 >= rCactus1.x || rCactus2.x + 50 >= rCactus3.x || rCactus2.x + 50 >= rCactus4.x || rCactus2.x + 50 >= rCactus5.x || rCactus2.x + 50 >= rBird.x
            || rCactus2.x - 50 >= rCactus1.x || rCactus2.x - 50 >= rCactus3.x || rCactus2.x - 50 >= rCactus4.x || rCactus2.x - 50 >= rCactus5.x || rCactus2.x - 50 >= rBird.x)
            {
                int rand = (int)(Math.random()*4000+200);
                rCactus2.x = WORLD_WIDTH+rand;

            }

            if(rCactus3.x + 50 >= rCactus2.x || rCactus3.x + 50 >= rCactus1.x || rCactus3.x + 50 >= rCactus4.x || rCactus3.x + 50 >= rCactus5.x || rCactus3.x + 50 >= rBird.x
            || rCactus3.x - 50 >= rCactus2.x || rCactus3.x - 50 >= rCactus1.x || rCactus3.x - 50 >= rCactus4.x || rCactus3.x - 50 >= rCactus5.x || rCactus3.x - 50 >= rBird.x)
            {
                int rand = (int)(Math.random()*4000+200);
                rCactus3.x = WORLD_WIDTH+rand;

            }

            if(rCactus4.x + 50 >= rCactus2.x || rCactus4.x + 50 >= rCactus3.x || rCactus4.x + 50 >= rCactus1.x || rCactus4.x + 50 >= rCactus5.x || rCactus4.x + 50 >= rBird.x
            || rCactus4.x - 50 >= rCactus2.x || rCactus4.x - 50 >= rCactus3.x || rCactus4.x - 50 >= rCactus1.x || rCactus4.x - 50 >= rCactus5.x || rCactus4.x - 50 >= rBird.x)
            {
                int rand = (int)(Math.random()*4000+200);
                rCactus4.x = WORLD_WIDTH+rand;

            }

            if(rCactus5.x + 50 >= rCactus2.x || rCactus5.x + 50 >= rCactus3.x || rCactus5.x + 50 >= rCactus4.x || rCactus5.x + 50 >= rCactus1.x || rCactus5.x + 50 >= rBird.x
            || rCactus5.x - 50 >= rCactus2.x || rCactus5.x - 50 >= rCactus3.x || rCactus5.x - 50 >= rCactus4.x || rCactus5.x - 50 >= rCactus1.x || rCactus5.x - 50 >= rBird.x)
            {
                int rand = (int)(Math.random()*4000+200);
                rCactus5.x = WORLD_WIDTH+rand;

            }

            if(hasCollided()){
                gamestate = GameState.GAMEOVER;
                long id2 = deadSound.play(1.0f);
                deadSound.setLooping(id2, false);
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
        rMeteor.x = WORLD_WIDTH/2+100;
        rMeteor.y = WORLD_HEIGHT/2+300;
        rBird.x = WORLD_WIDTH+5000;
        rCactus1.x = WORLD_WIDTH+500;
        rCactus2.x = WORLD_WIDTH+1000;
        rCactus3.x = WORLD_WIDTH+1500;
        rCactus4.x = WORLD_WIDTH+2000;
        rCactus5.x = WORLD_WIDTH+2500;
        rCloud1.x = 385;
        rCloud2.x = 250;
        rCloud3.x = 100;
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

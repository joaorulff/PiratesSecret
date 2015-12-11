package com.monmouth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import com.monmouth.box2Dtool.Box2DCreator;
import com.monmouth.box2Dtool.WorldContactListener;
import com.monmouth.game.PirateGame;
import com.monmouth.scenes.HUD;
import com.monmouth.sprites.Life;
import com.monmouth.sprites.Ninja;
import com.monmouth.sprites.Pirate;
import com.monmouth.sprites.Star;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class PlayScreen implements Screen {

    private enum gameState {RUNNING, PAUSED, JUMPING, GAME_OVER};
    private gameState currentGameState;
    private boolean ninjaFell = false;


    private PirateGame pirateGame;

    private Texture texture;

    //Loading the ninja and pirate Sprite
    private TextureAtlas atlas;
    private TextureAtlas pirateAtlas;

    public OrthographicCamera getGamecamera() {
        return gamecamera;
    }

    public void setGamecamera(OrthographicCamera gamecamera) {
        this.gamecamera = gamecamera;
    }

    //Box2D Variables
    private World world;
    private Box2DDebugRenderer box2DDR;


    //Camera
    private OrthographicCamera gamecamera;
    private Viewport gameViewPort;
    private WorldContactListener contactListener;

    private HUD hud;

    //Map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;


    //Temp Variables

    //Ninja
    private Ninja ninja;

    //Temporary pirate
    private ArrayList<Pirate> pirates;

    //Music
    private Music gameMusic;



    //Star
    private ArrayList<Star> stars;
    private Life life1;
    //Pirate
    //-private ArrayList<Pirate> pirates;
    public final short CATEGORY_NINJA = 0x0001;  // 0000000000000001 in binary
    public final short CATEGORY_PIRATE = 0x0002; // 0000000000000010 in binary
    public final short CATEGORY_STAR = 0x0004; // 0000000000000100 in binary
    public final short CATEGORY_WORLD = 0x0008; // 0000000000000100 in binary

    public PlayScreen(PirateGame pirateGame) {

        this.currentGameState = gameState.RUNNING;

        atlas = new TextureAtlas("coolNinja.txt");
        pirateAtlas = new TextureAtlas("enemyPirate.txt");

        this.pirateGame = pirateGame;

        //Camera and viewport initialization
        this.gamecamera = new OrthographicCamera();
        this.gameViewPort = new FitViewport(PirateGame.V_WIDTH / PirateGame.PPM*2, PirateGame.V_HEIGHT / PirateGame.PPM*2, gamecamera);
        gamecamera.position.set(gameViewPort.getWorldWidth()/2, gameViewPort.getWorldHeight()/2, 0);

        hud = new HUD(pirateGame.batch, this);

        //Map initialization
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("tileset.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/ PirateGame.PPM);


        //Box2D Variables initialization
        this.world = new World(new Vector2(0,-10), true); //The vector stands for gravity.

        this.box2DDR = new Box2DDebugRenderer();

        //Box2DCreator
        new Box2DCreator(this);

        //Ninja
        this.ninja = new Ninja(this);

        this.pirates = new ArrayList<Pirate>();
        this.stars = new ArrayList<Star>();


        //Colision Handle
        contactListener= new WorldContactListener(this);
        this.world.setContactListener(contactListener);

        //Game Music
        gameMusic = PirateGame.assetManager.get("audio/music/pirateMusic.mp3", Music.class);
        gameMusic.setLooping(true);
        gameMusic.play();

        //pirate1 = new Pirate(this, this.ninja.getNinjaBodyX()+5f, this.ninja.getNinjaBodyY()+3f);
        for(MapObject object : map.getLayers().get(3).getObjects()) {
            float x = Float.parseFloat(object.getProperties().get("x").toString());
            float y = Float.parseFloat(object.getProperties().get("y").toString());
            pirates.add(new Pirate(this, x/PirateGame.PPM, y/PirateGame.PPM));
        }

    }

    @Override
    public void show() {

    }

    public TextureAtlas getAtlas(){
        return this.atlas;
    }

    public TextureAtlas getPirateAtlas(){
        return this.pirateAtlas;
    }

    public void update(float deltaTime){



        this.world.step(1/60f, 6, 2);
        //life1.update();
        ninja.update(deltaTime);
        for(Pirate aPirate : pirates) {
            if(!aPirate.isToBeDeleted())
                aPirate.update(deltaTime);
        }
        for(Star aStar : stars) {
            if(!aStar.isToBeDeleted())
                aStar.update(deltaTime);
        }



        this.gamecamera.position.x = this.ninja.ninjaBody.getPosition().x;
        //pirates.add(new Pirate(this.world, this, new Vector2(this.gamecamera.position.x, this.ninja.getNinjaBodyY())));

        gamecamera.update();
        mapRenderer.setView(gamecamera);

        for (Body body : contactListener.getStarsToBeDeleted())
        {
            world.destroyBody(body);
            Star aStar = (Star)body.getUserData();
            stars.remove(aStar);
        }
        for (Body body : contactListener.getPiratesToBeDeleted())
        {
            world.destroyBody(body);
            Pirate aPirate = (Pirate)body.getUserData();
            pirates.remove(aPirate);
        }
        contactListener.getPiratesToBeDeleted().clear();
        contactListener.getStarsToBeDeleted().clear();
        if(hud.lives.size == 0) {
            this.currentGameState = gameState.GAME_OVER;
        }
        //Checking if ninja fell
        if(this.getNinja().getNinjaBodyY() < 0) {
            this.ninjaFell = true;
        }
       //stem.out.println(ninja.ninjaBody.getPosition().y);
        if(hud.lives.size != 0)
            hud.updateLife();
        HUD.updateScore(1);

    }

    public void handleInput(float deltaTime){



        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {

            if(this.ninja.getState() != Ninja.State.JUMPING) {
                this.ninja.ninjaBody.applyLinearImpulse(new Vector2(0, 4f), this.ninja.ninjaBody.getWorldCenter(), true);
                PirateGame.assetManager.get("audio/sounds/pirateJump.wav", Sound.class).play();
            }

        }

        //Ninja forward movement
        if(this.ninja.ninjaBody.getLinearVelocity().x <= 1) {
            this.ninja.ninjaBody.applyLinearImpulse(new Vector2(0.3125f, 0), this.ninja.ninjaBody.getWorldCenter(), true);
        }

        //Throwing stars
        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){

            stars.add(new Star(this.world, this, this.ninja.getX(), this.ninja));
            //pirates.add(new Pirate(this, this.ninja.getNinjaBodyX()+5f, this.ninja.getNinjaBodyY()+3f));

           // this.stars.starBody.applyLinearImpulse(new Vector2(0.5f, 0), this.ninja.ninjaBody.getWorldPoint(new Vector2(0,32)),true);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){

            this.currentGameState = (currentGameState == gameState.RUNNING) ? gameState.PAUSED : gameState.RUNNING;
            System.out.println(this.currentGameState);
        }

    }



    @Override
    public void render(float delta) {

        this.handleInput(delta);
        if(currentGameState == gameState.GAME_OVER) {
           pirateGame.setScreen(new StartScreen(pirateGame));
        }
        if(currentGameState != gameState.PAUSED){
            this.update(delta);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        //Render box2d
        box2DDR.render(this.world, this.gamecamera.combined);

        pirateGame.batch.setProjectionMatrix(this.gamecamera.combined);
        pirateGame.batch.begin();

        //life1.draw(pirateGame.batch);
        //System.out.println(this.gamecamera.position.x + "," + this.gamecamera.position.y);
        for(Pirate aPirate : pirates) {
            if(!aPirate.isToBeDeleted())
                aPirate.draw(pirateGame.batch);
        }
        for(Star aStar : stars) {
            if(!aStar.isToBeDeleted())
                aStar.draw(pirateGame.batch);
        }
        ninja.draw(pirateGame.batch);
        pirateGame.batch.end();


        pirateGame.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();



    }

    @Override
    public void resize(int width, int height) {

        gameViewPort.update(width, height);

    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public Ninja getNinja() {
        return ninja;
    }

    public void setNinja(Ninja ninja) {
        this.ninja = ninja;
    }

    @Override
    public void dispose() {

        this.map.dispose();
        this.mapRenderer.dispose();
        this.world.dispose();
        //this.box2DDR.dispose();
        this.hud.dispose();


    }

    public ArrayList<Star> getStars() {
        return stars;
    }
    public WorldContactListener getContactListener() {
        return contactListener;
    }
    public boolean isNinjaFell() {
        return ninjaFell;
    }
    public void setNinjaFell(boolean ninjaFell) {
        this.ninjaFell = ninjaFell;
    }
    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return this.world;
    }
}

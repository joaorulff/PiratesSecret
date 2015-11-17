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
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.*;
import com.monmouth.box2Dtool.Box2DCreator;
import com.monmouth.box2Dtool.WorldContactListener;
import com.monmouth.game.PirateGame;
import com.monmouth.scenes.HUD;
import com.monmouth.sprites.Ninja;
import com.monmouth.sprites.Pirate;
import com.monmouth.sprites.Star;

import java.util.ArrayList;


public class PlayScreen implements Screen {

    private PirateGame pirateGame;

    private Texture texture;

    //Loading the ninja Sprite
    private TextureAtlas atlas;

    //Box2D Variables
    private World world;
    private Box2DDebugRenderer box2DDR;


    //Camera
    private OrthographicCamera gamecamera;
    private Viewport gameViewPort;


    private HUD hud;

    //Map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;


    //Temp Variables

    //Ninja
    private Ninja ninja;

    //Music
    private Music gameMusic;

    //Star
    private ArrayList<Star> stars;
    private ArrayList<Pirate> pirates;


    public PlayScreen(PirateGame pirateGame) {

        atlas = new TextureAtlas("coolNinja.txt");

        this.pirateGame = pirateGame;

        //Camera and viewport initialization
        this.gamecamera = new OrthographicCamera();
        this.gameViewPort = new FitViewport(PirateGame.V_WIDTH / PirateGame.PPM, PirateGame.V_HEIGHT / PirateGame.PPM, gamecamera);
        gamecamera.position.set(gameViewPort.getWorldWidth()/2, gameViewPort.getWorldHeight()/2, 0);

        hud = new HUD(pirateGame.batch);

        //Map initialization
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("tileset.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/ PirateGame.PPM);


        //Box2D Variables initialization
        this.world = new World(new Vector2(0,-10), true); //The vector stands for gravity.

        this.box2DDR = new Box2DDebugRenderer();

        //Box2DCreator
        new Box2DCreator(this.world, this.map);

        //Ninja
        this.ninja = new Ninja(world, this);

        this.pirates = new ArrayList<Pirate>();
        this.stars = new ArrayList<Star>();

        //Colision Handle
        this.world.setContactListener(new WorldContactListener());

        //Game Music
        gameMusic = PirateGame.assetManager.get("audio/music/pirateMusic.mp3", Music.class);
        gameMusic.setLooping(true);
        gameMusic.play();


    }

    @Override
    public void show() {

    }

    public TextureAtlas getAtlas(){
        return this.atlas;
    }

    public void update(float deltaTime){
        this.handleInput(deltaTime);

        this.world.step(1/60f, 6, 2);

        ninja.update(deltaTime);

        hud.updateTime(deltaTime);
        HUD.updateScore(1);

        this.gamecamera.position.x = this.ninja.ninjaBody.getPosition().x;
        pirates.add(new Pirate(this.world, this, new Vector2(this.gamecamera.position.x, this.ninja.getNinjaBodyY())));
        gamecamera.update();
        mapRenderer.setView(gamecamera);

        /*if(!stars.isEmpty()) {
            System.out.println(this.stars.get(0).starBody.is);

        }*/
        //if(!stars.isEmpty())
        //System.out.println(stars.get(0).starBody.getWorldCenter().x);
        for(int j=0; j<stars.size(); j++) {
            System.out.println(stars.get(j).getStarBodyX());

            if (stars.get(j).getStarBodyX() > 6) {

                stars.get(j).destroy();
                stars.remove(j);
            }
        }


    }

    public void handleInput(float deltaTime){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {

            this.ninja.ninjaBody.applyLinearImpulse(new Vector2(0, 4f), this.ninja.ninjaBody.getWorldCenter(), true);
            PirateGame.assetManager.get("audio/sounds/pirateJump.wav", Sound.class).play();


        }

        if(this.ninja.ninjaBody.getLinearVelocity().x <= 1) {
            this.ninja.ninjaBody.applyLinearImpulse(new Vector2(0.3125f, 0), this.ninja.ninjaBody.getWorldCenter(), true);
        }

        //Throwing stars
        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){

            stars.add(new Star(this.world, this, this.ninja.getX(), this.ninja));


           // this.stars.starBody.applyLinearImpulse(new Vector2(0.5f, 0), this.ninja.ninjaBody.getWorldPoint(new Vector2(0,32)),true);
        }

    }



    @Override
    public void render(float delta) {

        this.update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        //Render box2d
        box2DDR.render(this.world, this.gamecamera.combined);

        pirateGame.batch.setProjectionMatrix(this.gamecamera.combined);
        pirateGame.batch.begin();
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

    @Override
    public void dispose() {

        this.map.dispose();
        this.mapRenderer.dispose();
        this.world.dispose();
        this.box2DDR.dispose();
        this.hud.dispose();

    }


}

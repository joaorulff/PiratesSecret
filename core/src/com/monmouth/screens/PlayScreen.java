package com.monmouth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.monmouth.box2Dtool.Box2DCreator;
import com.monmouth.box2Dtool.WorldContactListener;
import com.monmouth.game.PirateGame;
import com.monmouth.scenes.HUD;
import com.monmouth.sprites.Ninja;
import com.monmouth.sprites.Star;


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

    //Star
    private Star star;





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

        this.ninja = new Ninja(world, this);

        //Colision Handle
        this.world.setContactListener(new WorldContactListener());
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

        this.gamecamera.position.x = this.ninja.ninjaBody.getPosition().x;

        gamecamera.update();
        mapRenderer.setView(gamecamera);

    }

    public void handleInput(float deltaTime){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {

            this.ninja.ninjaBody.applyLinearImpulse(new Vector2(0, 4f), this.ninja.ninjaBody.getWorldCenter(), true);
        }

        if(this.ninja.ninjaBody.getLinearVelocity().x <= 1) {
            this.ninja.ninjaBody.applyLinearImpulse(new Vector2(0.05f, 0), this.ninja.ninjaBody.getWorldCenter(), true);
        }

        //Throwing stars
        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            this.star = new Star(this.world, this, this.ninja.getX());
            this.star.starBody.applyLinearImpulse(new Vector2(0.5f, 0), this.ninja.ninjaBody.getWorldCenter(), true);
        }

    }



    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        //Render box2d
        box2DDR.render(this.world, this.gamecamera.combined);

        pirateGame.batch.setProjectionMatrix(this.gamecamera.combined);
        pirateGame.batch.begin();
        ninja.draw(pirateGame.batch);
        //star.draw(pirateGame.batch);
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

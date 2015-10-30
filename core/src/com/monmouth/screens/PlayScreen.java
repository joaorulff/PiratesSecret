package com.monmouth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.monmouth.game.PirateGame;
import com.monmouth.scenes.HUD;
import com.monmouth.sprites.Ninja;


public class PlayScreen implements Screen {

    private PirateGame pirateGame;

    private Texture texture;

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
    private OrthogonalTiledMapRenderer renderer;


    //Temp Variables

    //Ninja
    private Ninja ninja;





    public PlayScreen(PirateGame pirateGame) {

        this.pirateGame = pirateGame;

        //Camera and viewport initialization
        this.gamecamera = new OrthographicCamera();
        this.gameViewPort = new FitViewport(PirateGame.V_WIDTH / PirateGame.PPM, PirateGame.V_HEIGHT / PirateGame.PPM, gamecamera);
        gamecamera.position.set(gameViewPort.getWorldWidth()/2, gameViewPort.getWorldHeight()/2, 0);

        hud = new HUD(pirateGame.batch);

        //Map initialization
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("ntileset.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ PirateGame.PPM);





        //Box2D Variables initialization
        this.world = new World(new Vector2(0,-10), true); //The vector stands for gravity.
        this.box2DDR = new Box2DDebugRenderer();



        //Temp Box2d variables
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject mapObject : this.map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){

            Rectangle rect = ((RectangleMapObject)mapObject).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2)/ PirateGame.PPM, (rect.getY() + rect.getHeight() / 2)/ PirateGame.PPM);

            body = this.world.createBody(bodyDef);

            shape.setAsBox((rect.getWidth()/2)/ PirateGame.PPM, (rect.getHeight()/2)/ PirateGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);


        }


        this.ninja = new Ninja(world);





    }

    @Override
    public void show() {

    }

    public void update(float deltaTime){
        this.handleInput(deltaTime);

        this.world.step(1/60f, 6, 2);

        this.gamecamera.position.x = this.ninja.ninjaBody.getPosition().x;

        gamecamera.update();
        renderer.setView(gamecamera);

    }

    public void handleInput(float deltaTime){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){

            this.ninja.ninjaBody.applyLinearImpulse(new Vector2(0, 4f), this.ninja.ninjaBody.getWorldCenter(), true);
        }

        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT)) && (this.ninja.ninjaBody.getLinearVelocity().x <= 2)){
            this.ninja.ninjaBody.applyLinearImpulse(new Vector2(0.1f, 0), this.ninja.ninjaBody.getWorldCenter(), true);
        }

        if((Gdx.input.isKeyPressed(Input.Keys.LEFT)) && (this.ninja.ninjaBody.getLinearVelocity().x >= -2) ){

                this.ninja.ninjaBody.applyLinearImpulse(new Vector2(-0.1f, 0), this.ninja.ninjaBody.getWorldCenter(), true);

        }

    }



    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //Render box2d
        box2DDR.render(this.world, this.gamecamera.combined);


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

    }


}

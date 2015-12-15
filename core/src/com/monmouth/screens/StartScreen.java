package com.monmouth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.monmouth.game.PirateGame;

/**
 * Created by matheussber on 11/17/15.
 */
public class StartScreen implements Screen {
    final PirateGame game;
    private Viewport viewport;
    private Stage stage;
    Label startLabel;
    Label instructionLabel;
    private Texture imgNinja;
    public StartScreen(final PirateGame game) {
        this.game = game;
        this.viewport = new FitViewport(PirateGame.V_WIDTH, PirateGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        imgNinja = new Texture(Gdx.files.internal("Ninja-Shadow.png"));


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("lastninja.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.genMipMaps = true;
        parameter.minFilter = Texture.TextureFilter.Linear.MipMapLinearNearest;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.size = 26;
        BitmapFont font = generator.generateFont(parameter);

        startLabel = new Label("Start Game", new Label.LabelStyle(font, Color.BLACK));
        startLabel.setPosition(500,300);
        startLabel.setTouchable(Touchable.enabled);
        startLabel.setBounds(500,300,startLabel.getWidth(),startLabel.getHeight());
        startLabel.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                return false;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
            }

        });

        instructionLabel = new Label("Instructions", new Label.LabelStyle(font, Color.BLACK));
        instructionLabel.setPosition(500, 200);
        instructionLabel.setTouchable(Touchable.enabled);
        instructionLabel.setBounds(500,200,instructionLabel.getWidth(),instructionLabel.getHeight());
        instructionLabel.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event,x,y,pointer,button);
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event,x,y,pointer,button);

                Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
            }
        });
        Gdx.input.setInputProcessor(stage);
        stage.addActor(startLabel);
        stage.addActor(instructionLabel);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255,255,255,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        game.batch.begin();
        game.batch.draw(imgNinja,10,10,300,470);
        game.batch.end();

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

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

package com.monmouth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
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
    private boolean changeToPlay = false;
    private boolean changeToInstruction = false;
    Label startLabel;
    Label instructionLabel;
    private Texture imgNinja;
    public StartScreen(final PirateGame game) {
        this.game = game;
        this.viewport = new FitViewport(PirateGame.V_WIDTH, PirateGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255,255,255,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        game.batch.begin();
        game.batch.draw(imgNinja,10,10,300,470);
        game.batch.end();
        if(changeToPlay) {
            game.setScreen(new PlayScreen(game));
            this.dispose();
        }

    }

    @Override
    public void show() {
        imgNinja = new Texture(Gdx.files.internal("Ninja-Shadow.png"));
        imgNinja.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("lastninja.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.genMipMaps = true;
        parameter.minFilter = Texture.TextureFilter.Linear.MipMapLinearNearest;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.size = 26;
        final BitmapFont font = generator.generateFont(parameter);

        startLabel = new Label("Start Game", new Label.LabelStyle(font, Color.BLACK));
        startLabel.setPosition(500,300);
        startLabel.setTouchable(Touchable.enabled);
        startLabel.setBounds(500,300,startLabel.getWidth(),startLabel.getHeight());
        startLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game));
                dispose();
            }
        });


        instructionLabel = new Label("Instructions", new Label.LabelStyle(font, Color.BLACK));
        instructionLabel.setPosition(500, 200);
        instructionLabel.setTouchable(Touchable.enabled);
        instructionLabel.setBounds(500,200,instructionLabel.getWidth(),instructionLabel.getHeight());
        instructionLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("este");
                game.setScreen(new InstructionScreen(game));
                dispose();
            }
        });


        stage.addActor(startLabel);
        stage.addActor(instructionLabel);

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
        startLabel.setTouchable(Touchable.disabled);
        instructionLabel.setTouchable(Touchable.disabled);
    }
}

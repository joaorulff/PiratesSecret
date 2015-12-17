package com.monmouth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.monmouth.game.PirateGame;

/**
 * Created by matheussber on 12/16/15.
 */
public class AboutScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private PirateGame game;
    Label objectivesLabel;
    Label whoDevelopedLabel;
    Label objectivesText;
    Label developers;

    public AboutScreen(PirateGame game) {
        this.viewport = new FitViewport(PirateGame.V_WIDTH, PirateGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        this.game = game;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("lastninja.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.genMipMaps = true;
        parameter.minFilter = Texture.TextureFilter.Linear.MipMapLinearNearest;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.size = 20;
        BitmapFont font = generator.generateFont(parameter);
        objectivesLabel = new Label("Objectives", new Label.LabelStyle(font, Color.BLACK));
        objectivesLabel.setPosition(80,450);
        whoDevelopedLabel = new Label("Developers", new Label.LabelStyle(font, Color.BLACK));
        whoDevelopedLabel.setPosition(530, 450);
        developers = new Label("Joao Rulff\nMatheus Bernardo\nRicardo Sena\nVinicius Machado", new Label.LabelStyle(font, Color.BLACK));
        developers.setPosition(500,350);
        objectivesText = new Label("You are a ninja.\n\nYou know their\nsecret and they want\nyou captured.\n\nThe ninja has to\nrun for his life, and\ndefend himself\nfrom the pirates.\n\nGood luck!", new Label.LabelStyle(font, Color.BLACK));
        objectivesText.setPosition(70,250);
        stage.addActor(objectivesLabel);
        stage.addActor(whoDevelopedLabel);
        stage.addActor(objectivesText);
        stage.addActor(developers);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            game.setScreen(new StartScreen(game));
            dispose();
        }
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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

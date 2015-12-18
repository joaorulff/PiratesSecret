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
 * Created by matheussber on 12/18/15.
 */
public class YouWonScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private PirateGame game;
    private Texture imgNinja;
    Label youWonLabel;

    public YouWonScreen(PirateGame game) {
        this.game = game;
        this.viewport = new FitViewport(PirateGame.V_WIDTH,PirateGame.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(this.viewport,game.batch);
        imgNinja = new Texture(Gdx.files.internal("Ninja_desk_vector_Clipart.png"));
        imgNinja.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("lastninja.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.genMipMaps = true;
        parameter.minFilter = Texture.TextureFilter.Linear.MipMapLinearNearest;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.size = 50;
        BitmapFont font = generator.generateFont(parameter);

        youWonLabel = new Label("YOU WON! NINJA ESCAPED!", new Label.LabelStyle(font, Color.BLACK));
        youWonLabel.setPosition(400-(youWonLabel.getWidth()/2),240-(youWonLabel.getHeight()/2));

        stage.addActor(youWonLabel);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.justTouched()) {
            game.setScreen(new StartScreen(game));
            dispose();
        }
        stage.draw();
        game.batch.begin();
        game.batch.draw(imgNinja,0,10,380,350);
        game.batch.end();
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

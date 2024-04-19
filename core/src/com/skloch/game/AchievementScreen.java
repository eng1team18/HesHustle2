package com.skloch.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * A scene2d window consisting of a title, a scrollable widget and an exit button.
 * Used to display the assets used in the game
 */
public class AchievementScreen implements Screen{

    private HustleGame game;
    private Stage achievementStage;
    private OrthographicCamera camera;
    private Viewport viewport;

    /**
     * A scene2d window consisting of a title, a scrollable list of achievements and a back button.
     *
     * @param game An instance of the HustleGame class
     */
    public AchievementScreen (final HustleGame game) {

        // Basically all the same code as the settings menu
        this.game = game;
        achievementStage = new Stage(new FitViewport(game.WIDTH, game.HEIGHT));
        Gdx.input.setInputProcessor(achievementStage);

        camera = new OrthographicCamera();
        viewport = new FitViewport(game.WIDTH, game.HEIGHT, camera);
        camera.setToOrtho(false, game.WIDTH, game.HEIGHT);

        // Create the window
        Window achievementMenu = new Window("", game.skin);
        achievementStage.addActor(achievementMenu);
        achievementMenu.setModal(true);

        // Table for UI elements in window
        Table achievementTable = new Table();
        achievementMenu.add(achievementTable).prefHeight(600);

        // Title
        Label title = new Label("Achievements", game.skin, "button");
        achievementTable.add(title).padTop(10);
        achievementTable.row();

        // Table for things inside the scrollable widget
        Table scrollTable = new Table();

        // Scrollable widget
        ScrollPane scrollWindow = new ScrollPane(scrollTable, game.skin);
        scrollWindow.setFadeScrollBars(false);
//         scrollWindow.setDebug(true);

        // scrollWindow.setFillParent(true);
        achievementTable.add(scrollWindow).padTop(20).height(350);
        achievementTable.row();

        Label text = new Label(Achievement.getInstance().getUserAchievements(), game.skin, "interaction");
        text.setWrap(true);
        scrollTable.add(text).width(520f).padLeft(15);

        // Exit button
        TextButton exitButton = new TextButton("Back", game.skin);
        achievementTable.add(exitButton).bottom().width(300).padTop(10);

        achievementMenu.pack();

        achievementMenu.setSize(600, 600);

        // Centre the window
        achievementMenu.setX((viewport.getWorldWidth() / 2) - (achievementMenu.getWidth() / 2));
        achievementMenu.setY((viewport.getWorldHeight() / 2) - (achievementMenu.getHeight() / 2));

        // Listener for the exit button
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.soundManager.playButton();
                dispose();
                game.setScreen(new GameOverScreen(game));
            }
        });

    }

    /**
     * Renders the achievments window
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render (float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.blueBackground.draw();

        achievementStage.act(delta);
        achievementStage.draw();

        camera.update();
    }


    /**
     * Correctly resizes the onscreen elements when the window is resized
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        achievementStage.getViewport().update(width, height);
        viewport.update(width, height);
    }

    // Other required methods from Screen
    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}

package com.skloch.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.io.FileNotFoundException;

// Changes
//
// - Line 84: GameScreen buttons height and padding has been modified to support the new
//   Leaderboard button
// - Line 69: GameScreen now also takes String userInput, which stores the player name for
//   the Leaderboard
// - Line 753: Added leaderboard.saveScore(playerName, totalScore) to the gameOver() function to
//   save the scores for the Leaderboard
// - Moved Energy from GameScreen to its own class
//

/**
 * Handles the majority of the game logic, rendering and user inputs of the game. Responsible for
 * rendering the player and the map, and calling events.
 */
public class GameScreen implements Screen {

  final HustleGame game;
  private OrthographicCamera camera;

  public Player player;
  private Window escapeMenu;
  private Viewport viewport;
  public OrthogonalTiledMapRenderer mapRenderer;
  public Stage uiStage;
  private Label interactionLabel;
  private EventManager eventManager;
  //    private OptionDialogue optionDialogue;
  protected InputMultiplexer inputMultiplexer;
  private Table uiTable;
  public DialogueBox dialogueBox;
  public final Image blackScreen;
  public boolean sleeping = false;
  public boolean fadeout = false;

  private Leaderboard leaderboard;
  private final Score score;

  private final Time time;

  private CustomInputAdapter customInputAdapter;

  String playerName;
  private Energy energyBar;


  /**
   * @param game         An instance of the class HustleGame containing variables that only need to
   *                     be loaded or initialised once.
   * @param avatarChoice Which avatar the player has picked, 0 for the more masculine avatar, 1 for
   *                     the more feminine
   */
  public GameScreen(final HustleGame game, int avatarChoice, String userInput) {
    // Important game variables
    this.game = game;
    this.game.gameScreen = this;
    this.score = Score.getInstance();
    this.playerName = userInput;

    time = new Time(this);


    // Scores
    time.hoursStudied = time.hoursRecreational = time.hoursSlept = 0;

    // Camera and viewport settings
    camera = new OrthographicCamera();
    viewport = new FitViewport(game.WIDTH, game.HEIGHT, camera);
    Energy energyBar = new Energy(viewport);
    camera.setToOrtho(false, game.WIDTH, game.HEIGHT);
    game.shapeRenderer.setProjectionMatrix(camera.combined);
    eventManager = new EventManager(this, energyBar, time);
    leaderboard = new Leaderboard();

    // Create a stage for the user interface to be on
    uiStage = new Stage(new FitViewport(game.WIDTH, game.HEIGHT));
    // Add a black image over everything first
    blackScreen = new Image(new Texture(Gdx.files.internal("Sprites/black_square.png")));
    blackScreen.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
    blackScreen.addAction(Actions.alpha(0f));

    // UI table to put everything in
    uiTable = new Table();
    uiTable.setSize(game.WIDTH, game.HEIGHT);
    uiStage.addActor(uiTable);

    // Create a player class
    if (avatarChoice == 1) {
      player = new Player("avatar1");
    } else {
      player = new Player("avatar2");
    }

    // USER INTERFACE

    // Create and center the yes/no box that appears when interacting with objects
    //        optionDialogue = new OptionDialogue("", 400, this.game.skin, game.soundManager);
    //        Window optWin = optionDialogue.getWindow();
    //        optionDialogue.setPos(
    //                (viewport.getWorldWidth() / 2f) - (optWin.getWidth() / 2f),
    //                (viewport.getWorldHeight() / 2f) - (optWin.getHeight() / 2f) - 150
    //        );
    //        // Use addActor to add windows to the scene
    //        uiTable.addActor(optionDialogue.getWindow());
    //        optionDialogue.setVisible(false);

    // Interaction label to prompt player
    interactionLabel = new Label("E - Interact", game.skin, "default");

    // Dialogue box
    dialogueBox = new DialogueBox(game.skin);
    dialogueBox.setPos(
        (viewport.getWorldWidth() - dialogueBox.getWidth()) / 2f,
        15f);
    dialogueBox.hide();

    // Set initial time
    time.daySeconds = (8 * 60); // 8:00 am


    // Table to display date and time
    Table timeTable = new Table();
    timeTable.setFillParent(true);
    time.timeLabel = new Label(Time.formatTime((int) time.daySeconds), game.skin, "time");
    time.dayLabel = new Label(String.format("Day %d", time.day), game.skin, "day");
    timeTable.add(time.timeLabel).uniformX();
    timeTable.row();
    timeTable.add(time.dayLabel).uniformX().left().padTop(2);
    timeTable.top().left().padLeft(10).padTop(10);

    // Set the order of rendered UI elements
    uiTable.add(interactionLabel).padTop(300);
    uiStage.addActor(energyBar);
    uiStage.addActor(timeTable);
    uiStage.addActor(blackScreen);
    uiStage.addActor(dialogueBox.getWindow());
    uiStage.addActor(dialogueBox.getSelectBox().getWindow());
    setupEscapeMenu(uiStage);

    // Start music
    game.soundManager.playOverworldMusic();

    //Set InputAdapter
    customInputAdapter = new CustomInputAdapter(this.game, dialogueBox, eventManager, player, escapeMenu, this);

    // Create the keyboard input adapter that defines events to be called based on
    // specific button presses
    InputAdapter gameKeyBoardInput = customInputAdapter.makeInputAdapter();

    // Since we need to listen to inputs from the stage and from the keyboard
    // Use an input multiplexer to listen for one inputadapter and then the other
    // inputMultiplexer needs to be established beforehand since we reference it
    // on resume() when going back to this screen from the settings menu
    inputMultiplexer = new InputMultiplexer();
    inputMultiplexer.addProcessor(gameKeyBoardInput);
    inputMultiplexer.addProcessor(uiStage);
    Gdx.input.setInputProcessor(inputMultiplexer);

    // Setup map
    float unitScale = game.mapScale / game.mapSquareSize;
    mapRenderer = new OrthogonalTiledMapRenderer(game.map, unitScale);

    // Set the player to the middle of the map
    // Get the dimensions of the top layer
    TiledMapTileLayer layer0 = (TiledMapTileLayer) game.map.getLayers().get(0);
    player.setPos(layer0.getWidth() * game.mapScale / 2f, layer0.getHeight() * game.mapScale / 2f);
    // Put camera on player
    camera.position.set(player.getCentreX(), player.getCentreY(), 0);

    // Give objects to player
    for (int layer : game.objectLayers) {
      // Get all objects on the layer
      MapObjects objects = game.map.getLayers().get(layer).getObjects();

      // Loop through each, handing them to the player
      for (int i = 0; i < objects.getCount(); i++) {
        // Get the properties of each object
        MapProperties properties = objects.get(i).getProperties();
        // If this is the spawn object, move the player there and don't collide
        if (properties.get("spawn") != null) {
          player.setPos(((float) properties.get("x")) * unitScale,
              ((float) properties.get("y")) * unitScale);
          camera.position.set(player.getPosAsVec3());
        } else {
          // Make a new gameObject with these properties, passing along the scale the map is
          // rendered at for accurate coordinates
          player.addCollidable(new GameObject(properties, unitScale));
        }
      }
    }

    // Set the player to not go outside the bounds of the map
    // Assumes the bottom left corner of the map is at 0, 0
    player.setBounds(
        new Rectangle(
            0,
            0,
            game.mapProperties.get("width", Integer.class) * game.mapScale,
            game.mapProperties.get("height", Integer.class) * game.mapScale
        )
    );
    game.shapeRenderer.setProjectionMatrix(camera.combined);

    // Display a little good morning message
    dialogueBox.show();
    dialogueBox.setText(time.getWakeUpMessage());
  }

  @Override
  public void show() {
  }

  /**
   * Renders the player, updates sound, renders the map and updates any UI elements. Called every
   * frame.
   *
   * @param delta The time in seconds since the last render.
   */
  @Override
  public void render(float delta) {
    // Clear screen
    ScreenUtils.clear(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    viewport.apply(); // Update the viewport

    // Set delta to a constant value to minimise stuttering issues when moving the camera and player
    // Solution found here: https://www.reddit.com/r/libgdx/comments/5z6qaf/can_someone_help_me_understand_timestepsstuttering/
    delta = 0.016667f;
    // Update sound timers
    game.soundManager.processTimers(delta);

    // Load timer bar - needs fixing and drawing
    // TextureAtlas blueBar = new TextureAtlas(Gdx.files.internal("Interface/BlueTimeBar/"
    //    + "BlueBar.atlas"));
    // Skin blueSkin = new Skin(blueBar);
    // ProgressBar timeBar = new ProgressBar(0, 200, 1, false, blueSkin);
    // timeBar.act(delta);

    time.timeLabel.setText(Time.formatTime((int) time.daySeconds));

    // Freeze the player's movement for this frame if any menus are visible
    if (escapeMenu.isVisible() || dialogueBox.isVisible() || sleeping || fadeout) {
      player.setFrozen(true);
    } else {
      player.setFrozen(false);
    }

    dialogueBox.scrollText(0.8f);

    // Let the player move to keyboard presses if not frozen
    // Player.move() handles player collision
    // Also play a footstep sound if they are moving
    player.move(delta);
    if (player.isRunning()) {
      game.soundManager.playFootstep(0.25f);
    } else if (player.isMoving()) {
      game.soundManager.playFootstep(0.5f);
    } else {
      game.soundManager.footstepBool = false;
    }

    if (Gdx.input.isKeyPressed(Input.Keys.B)) {
      gameOver();
    }

    if (Gdx.input.isKeyPressed(Input.Keys.G)) {
      score.incrementTotalScore(1, 5);
    }

    // Update the map's render position
    mapRenderer.setView(camera);
    // Draw the background layer
    mapRenderer.render(game.backgroundLayers);

    // Begin the spritebatch to draw the player on the screen
    game.batch.setProjectionMatrix(camera.combined);
    game.batch.begin();

    // Player, draw and scale
    game.batch.draw(
        player.getCurrentFrame(),
        player.sprite.x, player.sprite.y,
        0, 0,
        player.sprite.width, player.sprite.height,
        1f, 1f, 1
    );

    game.batch.end();

    // Render map foreground layers
    mapRenderer.render(game.foregroundLayers);

    // Check if the interaction (press e to use) label needs to be drawn
    interactionLabel.setVisible(false);
    if (!dialogueBox.isVisible() && !escapeMenu.isVisible() && !sleeping  && !fadeout) {
      if (player.nearObject()) {
        interactionLabel.setVisible(true);
        // Change text whether pressing E will interact or just read text
        if (player.getClosestObject().get("event") != null) {
          interactionLabel.setText("E - Interact");
        } else if (player.getClosestObject().get("text") != null) {
          interactionLabel.setText("E - Read Sign");
        }
      }
    }

    // Update UI elements
    uiStage.getViewport().apply();
    uiStage.act(delta);
    uiStage.draw();

    // Focus the camera on the center of the player
    // Make it slide into place too
    // Change to camera.positon.set() to remove cool sliding
    camera.position.slerp(
        new Vector3(
            player.getCentreX(),
            player.getCentreY(),
            0
        ),
        delta * 9
    );

    // Debug - Draw player hitboxes
    //    drawHitboxes();

    // Debug - print the event value of the closest object to the player if there is one
    //    if (player.getClosestObject() != null) {
    //    System.out.println(player.getClosestObject().get("event"));
    //     }

    camera.update();
  }


  /**
   * Configures everything needed to display the escape menu window when the player
   * presses 'escape'. Doesn't return anything as the variable escapeMenu is used to store the
   * window takes a table already added to the uiStage
   *
   * @param interfaceStage The stage that the escapeMenu should be added to
   */
  public void setupEscapeMenu(Stage interfaceStage) {
    // Configures an escape menu to display when hitting 'esc'
    // Escape menu
    escapeMenu = new Window("", game.skin);
    interfaceStage.addActor(escapeMenu);
    escapeMenu.setModal(true);

    Table escapeTable = new Table();
    escapeTable.setFillParent(true);

    escapeMenu.add(escapeTable);

    TextButton resumeButton = new TextButton("Resume", game.skin);
    escapeTable.add(resumeButton).pad(60, 80, 10, 80).width(300);
    escapeTable.row();
    TextButton settingsButton = new TextButton("Settings", game.skin);
    escapeTable.add(settingsButton).pad(10, 50, 10, 50).width(300);
    escapeTable.row();
    TextButton exitButton = new TextButton("Exit", game.skin);
    escapeTable.add(exitButton).pad(10, 50, 60, 50).width(300);

    escapeMenu.pack();

    // escapeMenu.setDebug(true);

    // Centre
    escapeMenu.setX((viewport.getWorldWidth() / 2) - (escapeMenu.getWidth() / 2));
    escapeMenu.setY((viewport.getWorldHeight() / 2) - (escapeMenu.getHeight() / 2));

    // Create button listeners

    resumeButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        if (escapeMenu.isVisible()) {
          game.soundManager.playButton();
          game.soundManager.playOverworldMusic();
          escapeMenu.setVisible(false);
        }
      }
    });

    // SETTINGS BUTTON
    // I assign this object to a new var 'thisScreen' since the changeListener overrides 'this'
    // I wasn't sure of a better solution
    Screen thisScreen = this;
    settingsButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        if (escapeMenu.isVisible()) {
          game.soundManager.playButton();
          game.setScreen(new SettingsScreen(game, thisScreen));
        }
      }
    });

    exitButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        if (escapeMenu.isVisible()) {
          game.soundManager.playButton();
          game.soundManager.stopOverworldMusic();
          dispose();
          game.setScreen(new MenuScreen(game));
        }
      }
    });

    escapeMenu.setVisible(false);

  }


  @Override
  public void resize(int width, int height) {
    uiStage.getViewport().update(width, height);
    viewport.update(width, height);
  }

  @Override
  public void pause() {
  }

  /**
   * Called when switching back to this gameScreen.
   */
  @Override
  public void resume() {
    // Set the input multiplexer back to this stage
    Gdx.input.setInputProcessor(inputMultiplexer);

    // I'm not sure why, but there's a small bug where exiting the settings menu doesn't make the
    // previous button on the previous screen update, so it's stuck in the 'over' configuration
    // until the user moves the mouse.
    // Uncomment the below line to bring the bug back
    // It's an issue with changing screens, and I can't figure out why it happens, but setting the
    // mouse position to exactly where it is seems to force the stage to update itself and fixes
    // the visual issue.

    Gdx.input.setCursorPosition(Gdx.input.getX(), Gdx.input.getY());
  }

  @Override
  public void hide() {
  }

  /**
   * Disposes of certain elements, called when the game is closed.
   */
  @Override
  public void dispose() {
    uiStage.dispose();
    mapRenderer.dispose();
  }

  /**
   * DEBUG - Draws the player's 3 hitboxes Uncomment use at the bottom of render to use.
   */
  public void drawHitboxes() {
    game.shapeRenderer.setProjectionMatrix(camera.combined);
    game.shapeRenderer.begin(ShapeType.Line);
    // Sprite
    game.shapeRenderer.setColor(1, 0, 0, 1);
    game.shapeRenderer.rect(player.sprite.x, player.sprite.y, player.sprite.width,
        player.sprite.height);
    // Feet hitbox
    game.shapeRenderer.setColor(0, 0, 1, 1);
    game.shapeRenderer.rect(player.feet.x, player.feet.y, player.feet.width, player.feet.height);
    // Event hitbox
    game.shapeRenderer.setColor(0, 1, 0, 1);
    game.shapeRenderer.rect(player.eventHitbox.x, player.eventHitbox.y, player.eventHitbox.width,
        player.eventHitbox.height);
    game.shapeRenderer.end();
  }

  // Functions related to game score and requirements

  /**
   * @param sleeping Sets the value of sleeping
   */
  public void setSleeping(boolean sleeping) {
    this.sleeping = sleeping;
  }

  /**
   * @return true if the player is sleeping
   */
  public boolean getSleeping() {
    return sleeping;
  }

  /**
   * @param fadeout sets the value of fadeout
   */
  //NEW CODE
  public void setFadeout(boolean fadeout) {
    this.fadeout = fadeout;
  }

  public boolean getFadeout() {
    return fadeout;
  }

  /**
   * Ends the game, called at the end of the 7th day, switches to a screen that displays a score.
   */
  public void gameOver() {
    game.setScreen(new GameOverScreen(game));
    int totalScore = score.getTotalScore();
    leaderboard.saveScore(playerName, totalScore);
  }
}

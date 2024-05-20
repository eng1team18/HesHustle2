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
import com.badlogic.gdx.scenes.scene2d.Action;
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

import java.util.Arrays;

// Changes
//
// - Line 84: GameScreen buttons height and padding has been modified to support the new
//   Leaderboard button
// - Line 69: GameScreen now also takes String userInput, which stores the player name for
//   the Leaderboard
// - Line 753: Added leaderboard.saveScore(playerName, totalScore) to the gameOver() function to
//   save the scores for the Leaderboard
// - Moved Energy from GameScreen to its own class
// - Line 97: Increases the viewport so more part of the map can be visible at once
//

/**
 * Handles the majority of the game logic, rendering and user inputs of the game. Responsible for
 * rendering the player and the map, and calling events.
 */
public class GameScreen implements Screen {

  final HustleGame game;
  private OrthographicCamera camera;

  public Player player;
  private EscapeMenu escapeMenu;
  private EscapeMenu escapeMenuSetup;
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
  public float[] campusSpawn;
  public float[] townSpawn;

  public NPC[] npcs = new NPC[0];

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
    //this.game.gameScreen = this;
    this.score = Score.getInstance();
    this.playerName = userInput;

    time = new Time(this);

    // Create a player class
    if (avatarChoice == 1) {
      player = new Player("avatar1");
    } else {
      player = new Player("avatar2");
    }

    // Scores
    time.hoursStudied = time.hoursRecreational = time.hoursSlept = 0;

    // Camera and viewport settings
    camera = new OrthographicCamera();
    // viewport = new FitViewport(game.width + 0, game.height + 0, camera);
    viewport = new FitViewport(game.width + 550, game.height + 300, camera);
    Energy energyBar = new Energy(viewport);
    camera.setToOrtho(false, game.width, game.height);
    game.shapeRenderer.setProjectionMatrix(camera.combined);
    eventManager = new EventManager(this, energyBar, time, player);
    leaderboard = new Leaderboard();

    // Create a stage for the user interface to be on
    uiStage = new Stage(new FitViewport(game.width, game.height));
    // Add a black image over everything first
    blackScreen = new Image(new Texture(Gdx.files.internal("Sprites/black_square.png")));
    blackScreen.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
    blackScreen.addAction(Actions.alpha(0f));

    // UI table to put everything in
    uiTable = new Table();
    uiTable.setSize(game.width, game.height);
    uiStage.addActor(uiTable);


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
    dialogueBox = new DialogueBox(game.skin, player);
    dialogueBox.setPos(
        ((viewport.getWorldWidth() - dialogueBox.getWidth()) / 2f) - 275,
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
    escapeMenu = new EscapeMenu(this.game, viewport, this, uiStage);

    // Start music
    game.soundManager.playOverworldMusic();

    //Set InputAdapter
    customInputAdapter = new CustomInputAdapter(this.game, dialogueBox, eventManager, player, EscapeMenu.escapeMenu, this);

    // Create the keyboard input adapter that defines events to be called based on
    // specific button presses
    InputAdapter gameKeyBoardInput = customInputAdapter.makeInputAdapter();

    // Since we need to listen to inputs from the stage and from the keyboard
    // Use an input multiplexer to listen for one input adapter and then the other
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

        // New code
        // Check if object is NPC. If it is, expand size of NPC array and add NPC to it with defined properties
        if (properties.get("npc") != null) {
          String avatar = (String) properties.get("avatar");
          int direction = (int) properties.get("direction");

          npcs = Arrays.copyOf(npcs, npcs.length + 1);
          npcs[npcs.length-1] = new NPC(avatar, direction);
          npcs[npcs.length-1].setPos(((float) properties.get("x")) * unitScale,
                  ((float) properties.get("y")) * unitScale);
        }

        // If this is the spawn object, move the player there and don't collide
        if (properties.get("spawn") != null) {
          player.setPos(((float) properties.get("x")) * unitScale,
                  ((float) properties.get("y")) * unitScale);
          camera.position.set(player.getPosAsVec3());
          // New code
          // Define spawn locations for bus stops
        } else if (properties.get("spawnCampus") != null) {
            campusSpawn = new float[]{(float) properties.get("x") * unitScale,
                    (float) properties.get("y") * unitScale};
        } else if (properties.get("spawnTown") != null) {
          townSpawn = new float[]{(float) properties.get("x") * unitScale,
                  (float) properties.get("y") * unitScale};
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
    if (EscapeMenu.escapeMenu.isVisible() || dialogueBox.isVisible() || sleeping || fadeout) {
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

    // New code
    // Draw all NPCs
    for(NPC npc : this.npcs){
      npc.updateAnimation();
      game.batch.draw(
              npc.getCurrentFrame(),
              npc.sprite.x, npc.sprite.y,
              0, 0,
              npc.sprite.width, npc.sprite.height,
              1f, 1f, 1
      );
    }

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
    if (!dialogueBox.isVisible() && !EscapeMenu.escapeMenu.isVisible() && !sleeping  && !fadeout) {
      if (player.nearObject()) {
        interactionLabel.setVisible(true);
        // Change text whether pressing E will interact or just read text
        if (player.getClosestObject().get("npc") != null) {
          interactionLabel.setText("E - Talk");
        } else if (player.getClosestObject().get("event") != null) {
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

  public void hideDialogueSelectBox() {
    dialogueBox.hideSelectBox();
  }

  public void showDialogueBox() {
    dialogueBox.show();
  }

  public void hideDialogueBox() {
    dialogueBox.hide();
  }

  public void setDialogueBoxText(String text) {
    dialogueBox.setText(text);
  }

  public void setDialogueBoxText(String text, String eventKey) {
    dialogueBox.setText(text, eventKey);
  }

  public void setDialogueBoxOptions(String[] options, String[] events) {
    dialogueBox.getSelectBox().setOptions(options, events);
  }

  public void addActionToBlackscreen(Action action) {
    blackScreen.addAction(action);
  }
}

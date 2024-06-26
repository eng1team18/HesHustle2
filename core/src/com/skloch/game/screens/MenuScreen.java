package com.skloch.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.skloch.game.HustleGame;
import com.skloch.game.gamelogic.Time;

/* ASSESSMENT 2 CHANGES
- Added a new nameInputTable for a new Screen that ask for the username for the Leaderboard
- Leaderboard button added along with a listener component
- GameScreen() also sends the playerName
- tutorialWindow redirect to nameInputTable instead and avatarSelectTable are shown
  afterward
- Moved Continue button inside of tutorial into the scroll bar to force users to read
- Added Controls to tutorial
*/

/**
 * A screen to display the game menu to the player has the buttons "Start", "Settings", "Credits",
 * "Exit". ALso displays a tutorial window and an avatar select screen.
 */
public class MenuScreen implements Screen {

  final HustleGame game;

  private Time time;
  private Stage menuStage;
  OrthographicCamera camera;
  private Viewport viewport;
  private Image titleImage;
  String playerName;

  /**
   * A class to display a menu screen, initially gives the player 4 options, Start, Settings,
   * Credits, Quit Upon hitting start, a tutorial window is shown, and then an avatar select screen
   * is shown, and then it is switched to GameScreen. Settings switches to SettingsScreen Credits
   * switches to CreditsScreen Quit exits the game
   *
   * @param game An instance of HustleGame with loaded variables
   */
  public MenuScreen(final HustleGame game) {
    this.game = game;
    // Create stage to draw UI on
    menuStage = new Stage(new FitViewport(game.width, game.height));
    Gdx.input.setInputProcessor(menuStage);

    camera = new OrthographicCamera();
    viewport = new FitViewport(game.width, game.height, camera);
    camera.setToOrtho(false, game.width, game.height);

    // Set the size of the background to the viewport size, only need to do this once,
    // this is then used by all screens as an easy way of having a blue background
    game.blueBackground.getRoot().findActor("blue image")
        .setSize(viewport.getWorldWidth(), viewport.getWorldHeight());

    // Title image
    titleImage = new Image(new Texture(Gdx.files.internal("Sprites/title.png")));
    titleImage.setPosition((viewport.getWorldWidth() / 2f)
        - (titleImage.getWidth() / 2f), 550);
    menuStage.addActor(titleImage);

    // Play menu music
    game.soundManager.playMenuMusic();

    // Make avatar select table
    Table avatarSelectTable = makeAvatarSelectTable();
    menuStage.addActor(avatarSelectTable);
    avatarSelectTable.setVisible(false);

    // Make name input window
    Table nameInputTable = makeNameInputTable(avatarSelectTable);
    menuStage.addActor(nameInputTable);
    nameInputTable.setVisible(false);

    // Make tutorial window
    Window tutorialWindow = makeTutorialWindow(nameInputTable);
    menuStage.addActor(tutorialWindow);
    tutorialWindow.setVisible(false);

    // Make table to draw buttons and title
    Table buttonTable = new Table();
    buttonTable.setFillParent(true);
    menuStage.addActor(buttonTable);

    // Old title, new uses a texture
    //    Label title = new Label("Heslington Hustle", game.skin, "title");

    // Create the buttons and add everything to the table using row() to go to a new line
    int buttonWidth = 340;
    TextButton startButton = new TextButton("New Game", game.skin);
    buttonTable.add(startButton).uniformX().width(buttonWidth).padBottom(10).padTop(210);
    buttonTable.row();
    TextButton settingsButton = new TextButton("Settings", game.skin);
    buttonTable.add(settingsButton).uniformX().width(buttonWidth).padBottom(10);
    buttonTable.row();
    TextButton creditsButton = new TextButton("Credits", game.skin);
    buttonTable.add(creditsButton).uniformX().width(buttonWidth).padBottom(10);
    buttonTable.row();
    TextButton leaderboardButton = new TextButton("Leaderboard", game.skin);
    buttonTable.add(leaderboardButton).uniformX().width(buttonWidth).padBottom(20);
    buttonTable.row();
    TextButton exitButton = new TextButton("Exit", game.skin);
    buttonTable.add(exitButton).uniformX().width(buttonWidth);
    buttonTable.top();

    // Add listeners to the buttons, so that they do things when pressed.

    // START GAME BUTTON - Displays the tutorial window
    startButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.soundManager.playButton();
        buttonTable.setVisible(false);
        titleImage.setVisible(false);
        tutorialWindow.setVisible(true);
        // dispose();
        // game.setScreen(new GameScreen(game));
      }
    });

    // SETTINGS BUTTON
    Screen thisScreen = this;
    settingsButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.soundManager.playButton();
        game.setScreen(new SettingsScreen(game, thisScreen));
      }
    });

    // CREDITS BUTTON
    creditsButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.soundManager.playButton();
        game.setScreen(new CreditScreen(game, thisScreen));
      }
    });

    leaderboardButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.soundManager.playButton();
        game.setScreen(new LeaderboardScreenMain(game));
      }
    });

    // EXIT BUTTON
    exitButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.soundManager.playButton();
        game.dispose();
        dispose();
        Gdx.app.exit();
      }
    });

    game.batch.setProjectionMatrix(camera.combined);

  }

  /**
   * Renders the main menu, and any windows that are displaying information.
   *
   * @param delta The time in seconds since the last render.
   */
  @Override
  public void render(float delta) {
    ScreenUtils.clear(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();

    game.blueBackground.draw();

    // Make the stage follow actions and draw itself
    menuStage.setViewport(viewport);
    menuStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    menuStage.draw();

  }

  /**
   * Correctly resizes the menu screen.
   *
   * @param width  New width of window
   * @param height New width of window
   */
  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
    menuStage.getViewport().update(width, height);
  }

  // Other required methods
  @Override
  public void show() {
  }

  @Override
  public void hide() {
  }

  @Override
  public void pause() {
  }

  /**
   * Correctly sizes the game when resuming it after a pause or switching screens. Fixes a small
   * graphical bug.
   */
  @Override
  public void resume() {
    Gdx.input.setInputProcessor(menuStage);

    // See the comment in the resume() function in GameScreen to see why this pointless line exists
    Gdx.input.setCursorPosition(Gdx.input.getX(), Gdx.input.getY());
  }

  /**
   * Dispose of all menu assets.
   */
  @Override
  public void dispose() {
    menuStage.dispose();
  }

  /**
   * Generates a window to teach the player how to play the game. Displays the tutorial text shown
   * in Text/tutorial_text.txt
   *
   * @return A small window to explain the game
   */
  public Window makeTutorialWindow(Table nextTable) {
    Window tutWindow = new Window("", game.skin);
    Table tutTable = new Table();
    tutWindow.add(tutTable).prefHeight(600).prefWidth(800 - 20);

    // Title
    Label title = new Label("How to play", game.skin, "button");
    tutTable.add(title).padTop(10);
    tutTable.row();

    // Table for things inside the scrollable widget
    Table scrollTable = new Table();

    // Scrollable widget
    ScrollPane scrollWindow = new ScrollPane(scrollTable, game.skin);
    scrollWindow.setFadeScrollBars(false);

    tutTable.add(scrollWindow).padTop(20).height(400).width(870);
    tutTable.row();

    Label text = new Label(game.tutorialText, game.skin, "interaction");
    text.setWrap(true);
    scrollTable.add(text).width(820f).padLeft(20);
    scrollTable.row();
    TextButton continueButton = new TextButton("Continue", game.skin);
    scrollTable.add(continueButton).bottom().width(300).padTop(10);

    // Exit button

    tutWindow.pack();

    tutWindow.setSize(900, 600);

    // Centre the window
    tutWindow.setX((viewport.getWorldWidth() / 2) - (tutWindow.getWidth() / 2));
    tutWindow.setY((viewport.getWorldHeight() / 2) - (tutWindow.getHeight() / 2));

    continueButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.soundManager.playButton();
        tutWindow.setVisible(false);
        nextTable.setVisible(true);
      }
    });

    return tutWindow;
  }


  /**
   * Creates an avatar selection screen, consisting of a label and two buttons.
   *
   * @return A table containing UI elements
   */
  public Table makeAvatarSelectTable() {
    Table table = new Table();
    table.setFillParent(true);
    table.top();

    // Prompt
    Label title = new Label("Select your avatar", game.skin, "button");
    table.add(title).padBottom(120).padTop(80);
    table.row();

    // Image buttons
    Table buttonTable = new Table();
    table.add(buttonTable).width(600);

    ImageButton choice1 = new ImageButton(game.skin, "avatar1");
    buttonTable.add(choice1).left().expandX();
    ImageButton choice2 = new ImageButton(game.skin, "avatar2");
    buttonTable.add(choice2).right().expandX();

    choice1.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.soundManager.playButton();
        game.setScreen(new GameScreen(game, 1, playerName));
        game.soundManager.stopMenuMusic();
        dispose();
      }
    });

    choice2.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.soundManager.playButton();
        game.setScreen(new GameScreen(game, 2, playerName));
        game.soundManager.stopMenuMusic();
        dispose();
      }
    });

    return table;
  }

  /**
   * Makes the table and UI for the name input section of main menu.
   *
   * @param nextTable information regarding dimensions of this table
   * @return window used to enter character name
   */
  public Window makeNameInputTable(Table nextTable) {
    Window nameWindow = new Window("", game.skin);
    Table tutTable = new Table();
    nameWindow.add(tutTable).prefHeight(600).prefWidth(800 - 20);

    // Title
    Label title = new Label("Enter Name", game.skin, "button");
    tutTable.add(title).padTop(10);
    tutTable.row();

    TextField nameField = new TextField("", game.skin2);
    nameField.setMessageText("Enter your name here");
    nameField.setAlignment(Align.center);

    nameField.setTextFieldFilter(new TextField.TextFieldFilter() {
      @Override
      public boolean acceptChar(TextField textField, char c) {
        return Character.isLetter(c) && textField.getText().length() < 16;
      }
    });

    tutTable.add(nameField).padTop(20).fillX().expandX();
    tutTable.row();

    // Exit button
    TextButton continueButton = new TextButton("Continue", game.skin);
    tutTable.add(continueButton).bottom().width(300).padTop(10);

    nameWindow.pack();

    nameWindow.setSize(900, 600);

    // Centre the window
    nameWindow.setX((viewport.getWorldWidth() / 2) - (nameWindow.getWidth() / 2));
    nameWindow.setY((viewport.getWorldHeight() / 2) - (nameWindow.getHeight() / 2));

    continueButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        String inputText = nameField.getText();

        if (!inputText.isEmpty()) {
          game.soundManager.playButton();
          nameWindow.setVisible(false);
          nextTable.setVisible(true);
          playerName = inputText;
        }
      }
    });

    return nameWindow;
  }
}

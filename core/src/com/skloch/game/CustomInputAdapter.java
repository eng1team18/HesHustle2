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
import java.awt.*;

public class CustomInputAdapter {
    private HustleGame game;
    private DialogueBox dialogueBox;
    private EventManager eventManager;
    private Player player;
    private Window escapeMenu;
    private GameScreen screen;


    public CustomInputAdapter(HustleGame game, DialogueBox dialogueBox, EventManager eventManager, Player player, Window escapeMenu, GameScreen screen){
        this.game = game;
        this.dialogueBox = dialogueBox;
        this.eventManager = eventManager;
        this.player = player;
        this.escapeMenu = escapeMenu;
        this.screen = screen;
    }


    /**
     * Generates an InputAdapter to handle game specific keyboard inputs.
     *
     * @return An InputAdapter for keyboard inputs
     */
    public InputAdapter makeInputAdapter() {
        return new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                // SHOW ESCAPE MENU CODE
                if (keycode == Input.Keys.ESCAPE) {
                    if (escapeMenu.isVisible()) {
                        game.soundManager.playButton();
                        game.soundManager.playOverworldMusic();
                        escapeMenu.setVisible(false);
                    } else {
                        // game.soundManager.pauseOverworldMusic();
                        game.soundManager.playButton();
                        escapeMenu.setVisible(true);
                    }
                    // Return true to indicate the keydown event was handled
                    return true;
                }

                // SHOW OPTION MENU / ACT ON OPTION MENU CODE
                if (keycode == Input.Keys.E || keycode == Input.Keys.ENTER || keycode == Input.Keys.SPACE) {
                    if (!escapeMenu.isVisible()) {
                        // If a dialogue box is visible, choose an option or advance text
                        if (dialogueBox.isVisible()) {
                            dialogueBox.enter(eventManager);
                            game.soundManager.playButton();

                        } else if (player.nearObject() && !screen.sleeping && !screen.fadeout) {
                            // If the object has an event associated with it
                            if (player.getClosestObject().get("event") != null) {
                                // Show a dialogue menu asking if they want to do an interaction with the object
                                dialogueBox.show();
                                dialogueBox.getSelectBox().setOptions(new String[]{"Yes", "No"},
                                        new String[]{(String) player.getClosestObject().get("event"), "exit"});
                                if (eventManager.hasCustomObjectInteraction(
                                        (String) player.getClosestObject().get("event"))) {
                                    dialogueBox.setText(eventManager.getObjectInteraction(
                                            (String) player.getClosestObject().get("event")));
                                } else {
                                    dialogueBox.setText(
                                            "Interact with " + player.getClosestObject().get("event") + "?");
                                }
                                dialogueBox.show();
                                dialogueBox.getSelectBox().show();
                                game.soundManager.playDialogueOpen();

                            } else if (player.getClosestObject().get("text") != null) {
                                // Otherwise, if it is a text object, just display its text
                                dialogueBox.show();
                                dialogueBox.setText((String) player.getClosestObject().get("text"));
                            }
                        }
                        return true;
                    }
                }

                // If an option dialogue is open it should soak up all keypresses
                if (dialogueBox.isVisible() && dialogueBox.getSelectBox().isVisible()
                        && !escapeMenu.isVisible()) {
                    // Up or down
                    if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
                        dialogueBox.getSelectBox().choiceUp();
                    } else if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
                        dialogueBox.getSelectBox().choiceDown();
                    }

                    return true;

                }

                return false;
            }
        };
    }

}

package com.skloch.game.gamelogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * NEW CLASS FOR ASSESSMENT 2
 * Class defining non-playable characters. These are sprites that are stationary and can be
 * talked to give hints and suggestions. An NPC can be added by creating a map object with
 * boolean tag 'npc', and string tags 'avatar' (to chose sprite) and 'text' (for dialogue),
 * and int tag 'direction' for the direction they face.
 */
public class NonPlayableCharacter {

  // Hitboxes
  public Rectangle sprite;
  public float centreX;
  public float centreY;
  public int direction; // 0 = up, 1 = right, 2 = down, 3 = left (like a clock)
  public TextureRegion currentFrame;
  private float stateTime = 0;
  private final Array<Animation<TextureRegion>> walkingAnimation;
  private final Array<Animation<TextureRegion>> idleAnimation;
  // Stats
  public int scale = 4;
  public boolean moving = false;

  /**
   * Constructor for NPC class. Will create animations based on input sprite sheet.
   *
   * @param avatar the sprite sheet of the sprite chosen in the map object 'avatar1' or 'avatar2'.
   * @param direction the direction the NPC faces as defined in the map object. 0 = up, 1 = right,
   *                  2 = down and 3 = left, like a clock
   */
  public NonPlayableCharacter(String avatar, int direction) {
    walkingAnimation = new Array<Animation<TextureRegion>>();
    idleAnimation = new Array<Animation<TextureRegion>>();

    this.direction = direction;

    // Load the player's textures from the atlas
    TextureAtlas playerAtlas = new TextureAtlas(
        Gdx.files.internal("Sprites/Player/player_sprites.atlas"));

    // Load walking animation from Sprite atlas
    walkingAnimation.add(
        new Animation<TextureRegion>(0.25f, playerAtlas.findRegions(avatar + "_walk_back"),
            Animation.PlayMode.LOOP),
        new Animation<TextureRegion>(0.25f, playerAtlas.findRegions(avatar + "_walk_right"),
            Animation.PlayMode.LOOP),
        new Animation<TextureRegion>(0.25f, playerAtlas.findRegions(avatar + "_walk_front"),
            Animation.PlayMode.LOOP),
        new Animation<TextureRegion>(0.25f, playerAtlas.findRegions(avatar + "_walk_left"),
            Animation.PlayMode.LOOP));
    // Load idle animation
    idleAnimation.add(
        new Animation<TextureRegion>(0.40f, playerAtlas.findRegions(avatar + "_idle_back"),
            Animation.PlayMode.LOOP),
        new Animation<TextureRegion>(0.40f, playerAtlas.findRegions(avatar + "_idle_right"),
            Animation.PlayMode.LOOP),
        new Animation<TextureRegion>(0.40f, playerAtlas.findRegions(avatar + "_idle_front"),
            Animation.PlayMode.LOOP),
        new Animation<TextureRegion>(0.40f, playerAtlas.findRegions(avatar + "_idle_left"),
            Animation.PlayMode.LOOP)
    );

    // Sprite is a rectangle covering the whole player
    sprite = new Rectangle(0, 0, 17 * scale, 28 * scale);

  }

  /**
   * Advances the current animation based on the time since the last render. The animation frame of
   * the NPC can be grabbed with getCurrentFrame.
   */
  public void updateAnimation() {
    stateTime += Gdx.graphics.getDeltaTime();
    // Set the current frame of the animation
    // Show a different animation if the player is moving vs idling
    if (moving) {
      currentFrame = walkingAnimation.get(direction).getKeyFrame(stateTime);
    } else {
      currentFrame = idleAnimation.get(direction).getKeyFrame(stateTime);
    }
  }

  /**
   * Sets the NPC's state to moving or not moving, a not moving character will just display an idle
   * animation.
   *
   * @param moving The boolean to set moving to
   */
  public void setMoving(boolean moving) {
    this.moving = moving;
  }

  /**
   * Returns if the NPC is moving or not.
   *
   * @return true if the NPC is moving
   */
  public boolean isMoving() {
    return moving;
  }

  /**
   * Returns the current frame the NPC's animation is on.
   *
   * @return TextureRegion the frame of the NPC's animation
   */
  public TextureRegion getCurrentFrame() {
    // Returns the current frame the player animation is on
    return currentFrame;
  }

  /**
   * Returns current x coordinate of NPC.
   *
   * @return The X coordinate of the NPC
   */
  public float getX() {
    return sprite.getX();
  }

  /**
   * Returns current y coordinate of NPC.
   *
   * @return The Y coordinate of the NPC
   */
  public float getY() {
    return sprite.getY();
  }

  /**
   * Returns NPC's current direction.
   *
   * @return The direction the NPC is facing, 0 = up, 1 = right, 2 = down, 3 = left (like a clock)
   */
  public int getDirection() {
    return direction;
  }

  /**
   * Sets the NPC's facing direction.
   *
   * @param direction the direction to set the NPC to, 0 = up, 1 = right, 2 = down, 3 = left (like
   *                  a clock)
   */
  public void setDirection(int direction) {
    this.direction = direction;
  }

  /**
   * Sets the x coordinate of the NPC, updating all 3 hitboxes at once as opposed to just the sprite
   * rectangle.
   */
  public void setX(float x) {
    this.sprite.setX(x);
    this.recalculateCentre();
  }

  /**
   * Sets the Y coordinate of the NPC, updating all 3 hitboxes at once as opposed to just the sprite
   * rectangle.
   */
  public void setY(float y) {
    this.sprite.setY(y);
    this.recalculateCentre();
  }

  /**
   * Sets the x and y positions of the NPC as a single float input, instead of with
   * separate methods.
   *
   * @param x The X coordinate to set the NPC to
   * @param y The Y coordinate to set the NPC to
   */
  public void setPos(float x, float y) {
    this.setX(x);
    this.setY(y);
  }

  /**
   * Recalculates the centre of the NPC, useful after moving the NPC.
   */
  private void recalculateCentre() {
    centreX = sprite.getX() + sprite.getWidth() / 2;
    centreY = sprite.getY() + sprite.getHeight() / 2;
  }
}
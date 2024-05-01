package com.skloch.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Energy extends Group {

  private Image energyBar;
  private float maxEnergyWidth;
  private int energy = 100;

  public Energy(Viewport viewport) {

    Group energyGroup = new Group();
    energyGroup.setDebug(true);
    energyBar = new Image(new Texture(Gdx.files.internal("Interface/Energy Bar/green_bar.png")));
    Image energyBarOutline = new Image(
        new Texture(Gdx.files.internal("Interface/Energy Bar/bar_outline.png")));
    energyBarOutline.setPosition(viewport.getWorldWidth() - energyBarOutline.getWidth() - 15, 15);
    energyBar.setPosition(energyBarOutline.getX() + 16, energyBarOutline.getY() + 16);
    energyGroup.addActor(energyBar);
    energyGroup.addActor(energyBarOutline);

    Texture energyTexture = new Texture(Gdx.files.internal("Interface/Energy Bar/green_bar.png"));
    Texture outlineTexture = new Texture(
        Gdx.files.internal("Interface/Energy Bar/bar_outline.png"));

    energyBar = new Image(energyTexture);
    energyBarOutline = new Image(outlineTexture);

    energyBarOutline.setPosition(viewport.getWorldWidth() - energyBarOutline.getWidth() - 15, 15);
    energyBar.setPosition(energyBarOutline.getX() + 16, energyBarOutline.getY() + 16);

    maxEnergyWidth = energyBar.getWidth();
    addActor(energyBar);
    addActor(energyBarOutline);
  }

  /**
   * Sets the player's energy level and updates the onscreen bar
   *
   * @param energy An int between 0 and 100
   */
  public void setEnergy(int energy) {
    this.energy = energy;
    if (this.energy > 100) {
      this.energy = 100;
    }
    energyBar.setScaleY(this.energy / 100f);
  }

  /**
   * Decreases the player's energy by a certain amount
   *
   * @param energy The energy to decrement
   */
  public void decreaseEnergy(int energy) {
    this.energy -= energy;
    if (this.energy < 0) {
      this.energy = 0;
    }
    energyBar.setScaleY(this.energy / 100f);
  }

  public int getEnergy() {
    return this.energy;
  }
}

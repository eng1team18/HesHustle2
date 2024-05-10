package com.skloch.game.tests;

import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs tests to ensure no assets are missing.
 */
@RunWith(GdxTestRunner.class)
public class AssetTests {

  @Test
  public void testIfEastCampusExists() {
    assertTrue(Gdx.files.internal("East Campus/Campus East.tiled-session").exists());
    assertTrue(Gdx.files.internal("East Campus/east_campus_new.tmx").exists());
  }

  @Test
  public void testIfEastCampusTexturesExists() {
    assertTrue(
        Gdx.files.internal("East Campus/Textures/StarRealmsCozyForestPack24x24.png")
            .exists());
  }

  @Test
  public void testIfEastCampusTilemapsExists() {
    assertTrue(
        Gdx.files.internal("East Campus/Tilemaps/StarRealmsCozyForestPack24x24.tsx")
            .exists());
  }

  @Test
  public void testIfIconsExists() {
    assertTrue(Gdx.files.internal("Icons/icon_16x16.png").exists());
    assertTrue(Gdx.files.internal("Icons/icon_32x32.png").exists());
    assertTrue(Gdx.files.internal("Icons/icon_128x128.png").exists());
  }

  @Test
  public void testIfInterfaceBlockyInterfaceDataExists() {
    assertTrue(Gdx.files.internal("Interface/BlockyInterface_data/avatar2.png").exists());
    assertTrue(Gdx.files.internal("Interface/BlockyInterface_data/button_disabled.9.png")
        .exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/button_over.9.png").exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/button_up.9.png").exists());
    assertTrue(Gdx.files.internal("Interface/BlockyInterface_data/MotorolaScreentype.fnt")
        .exists());
    assertTrue(Gdx.files.internal("Interface/BlockyInterface_data/MotorolaScreentype.png")
        .exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/nk57-monospace.rg-bold.png")
            .exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/nk57-monospace.rg-bold.fnt")
            .exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/PixeloidMono edited(2).png")
            .exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/PixeloidMono edited(2).fnt")
            .exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/scroll_bar.9.png").exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/scroll_bar_v.9.png").exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/slider_bar.png").exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/slider_button.png").exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/window.9.png").exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/scroll_bar.9.png").exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/W95FA(3).png").exists());
    assertTrue(
        Gdx.files.internal("Interface/BlockyInterface_data/W95FA(3).fnt").exists());
  }

  @Test
  public void testIfInterfaceEnergyBarExists() {
    assertTrue(Gdx.files.internal("Interface/Energy Bar/bar_outline.png").exists());
    assertTrue(Gdx.files.internal("Interface/Energy Bar/green_bar.png").exists());
  }

  @Test
  public void testIfInterfaceExists() {
    assertTrue(Gdx.files.internal("Interface/BlockyInterface.atlas").exists());
    assertTrue(Gdx.files.internal("Interface/BlockyInterface.json").exists());
    assertTrue(Gdx.files.internal("Interface/BlockyInterface.png").exists());
    assertTrue(Gdx.files.internal("Interface/Blockyinterface.scmp").exists());
  }

  @Test
  public void testIfMusicExists() {
    assertTrue(Gdx.files.internal("Music/OverworldMusic.mp3").exists());
    assertTrue(Gdx.files.internal("Music/Streetlights.ogg").exists());
  }

  @Test
  public void testIfSoundsExists() {
    assertTrue(Gdx.files.internal("Sounds/Button.wav").exists());
    assertTrue(Gdx.files.internal("Sounds/DialogueOpen.wav").exists());
    assertTrue(Gdx.files.internal("Sounds/DialogueOption.wav").exists());
    assertTrue(Gdx.files.internal("Sounds/footstep1.ogg").exists());
    assertTrue(Gdx.files.internal("Sounds/footstep1 grass.ogg").exists());
    assertTrue(Gdx.files.internal("Sounds/footstep2.ogg").exists());
    assertTrue(Gdx.files.internal("Sounds/footstep2 grass.ogg").exists());
    assertTrue(Gdx.files.internal("Sounds/Pause01.wav").exists());
    assertTrue(Gdx.files.internal("Sounds/Walking.wav").exists());
  }

  @Test
  public void testIfTextExists() {
    assertTrue(Gdx.files.internal("Text/credits.txt").exists());
    assertTrue(Gdx.files.internal("Text/tutorial_text.txt").exists());
  }

  @Test
  public void testIfTextFieldExists() {
    assertTrue(Gdx.files.internal("TextField/TextField.atlas").exists());
    assertTrue(Gdx.files.internal("TextField/TextField.json").exists());
    assertTrue(Gdx.files.internal("TextField/TextField.json").exists());
    assertTrue(Gdx.files.internal("TextField/W95FA(1).fnt").exists());
  }
}

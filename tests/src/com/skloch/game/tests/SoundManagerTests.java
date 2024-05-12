package com.skloch.game.tests;

import static org.junit.Assert.assertEquals;

import com.skloch.game.SoundManager;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core SoundManager class.
 */
@RunWith(GdxTestRunner.class)
public class SoundManagerTests {
  SoundManager soundManager = new SoundManager();

  @Test
  public void testGetMusicVolume() {
    assertEquals(soundManager.getMusicVolume(), 0.8f, 1e-15);
  }

  @Test
  public void testGetSfxVolume() {
    assertEquals(soundManager.getSfxVolume(), 0.8f, 1e-15);
  }

  @Test
  public void testSetMusicVolume() {
    soundManager.setMusicVolume(0.4f);
    assertEquals(soundManager.getMusicVolume(), 0.4f, 1e-15);
  }

  @Test
  public void testSetSfxVolume() {
    soundManager.setSfxVolume(0.3f);
    assertEquals(soundManager.getSfxVolume(), 0.3f, 1e-15);
  }
}

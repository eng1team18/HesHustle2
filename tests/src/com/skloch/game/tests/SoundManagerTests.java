package com.skloch.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.skloch.game.utility.SoundManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core SoundManager class.
 */
@RunWith(GdxTestRunner.class)
@FixMethodOrder
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

  @Test
  public void testPlayFootstepTimerGreaterThanZero() {
    // First check that it won't reach the point where it plays the footstep if timer > 0.
    soundManager.footstepTimer = 1f;
    soundManager.footstepBool = false;
    soundManager.playFootstep(0.5f);
    assertFalse(soundManager.footstepBool);
  }

  @Test
  public void testPlayFootstepTimer() {
    // Now check footstep timer is correctly set and footstep bool switches if timer <= 0.
    soundManager.footstepTimer = 0f;
    soundManager.footstepBool = true;
    soundManager.playFootstep(0.5f);
    assertEquals(soundManager.footstepTimer, 0.5f, 1e-15);
    assertFalse(soundManager.footstepBool);
  }

  @Test
  public void testPlayFootstepTimerOtherFoot() {
    soundManager.footstepTimer = 0f;
    soundManager.playFootstep(0.5f);
    assertTrue(soundManager.footstepBool);
  }

  @Test
  public void testProcessTimers() {
    soundManager.footstepTimer = 0.75f;
    soundManager.processTimers(0.25f);
    assertEquals(soundManager.footstepTimer, 0.5f, 1e-15);
  }

  @Test
  public void testProcessTimersLessThanZero() {
    soundManager.footstepTimer = 0.5f;
    soundManager.processTimers(0.6f);
    assertEquals(soundManager.footstepTimer, 0f, 1e-15);
  }
}

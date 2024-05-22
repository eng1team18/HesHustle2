package com.skloch.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.skloch.game.scoring.Achievement;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core Achievement class.
 */
@FixMethodOrder
@RunWith(GdxTestRunner.class)
public class AchievementTests {

  Achievement achievements = Achievement.getInstance();

  /**
   * Tests that a non-existent achievement will be not given, without raising an error.
   */
  @Test
  public void testCheckNonexistentAchievement() {
    assertFalse(achievements.checkAchievement(99979));
  }

  /**
   * Tests that valid achievements will be given correctly.
   */
  @Test
  public void testGiveAchievement() {
    // First ensure that achievement 1 being unlocked is false by default.
    assertFalse(achievements.checkAchievement(3));

    // Now check giving the achievement makes it true.
    achievements.giveAchievement(3);
    assertTrue(achievements.checkAchievement(3));

    // Additional verification that it doesn't raise an unexpected error to give an already
    // obtained achievement, or an achievement that doesn't exist.
    achievements.giveAchievement(3);
    achievements.giveAchievement(99923);
  }

  /**
   * Tests that all achievements can be reset to false in one method.
   */
  @Test
  public void testResetAllAchievements() {
    achievements.giveAchievement(1);
    achievements.giveAchievement(2);
    achievements.giveAchievement(3);
    achievements.giveAchievement(4);
    achievements.resetAllAchievements();
    for (int i = 1; i <= 4; i++) {
      assertFalse(achievements.checkAchievement(i));
    }
  }

  /**
   * Tests that a string of all the user's achievements can be obtained correctly.
   */
  @Test
  public void testGetUserAchievements() {
    achievements.resetAllAchievements();
    achievements.giveAchievement(1);
    assertEquals(achievements.getUserAchievements(),
        "- Tree? +100: Talk to the mysterious tree.\n");
  }

  /**
   * Tests that the bookworm achievement won't be given if the number of times is too low.
   */
  @Test
  public void testBookwormAchievementNotEnough() {
    achievements.resetAllAchievements();
    assertFalse(achievements.bookwormAchievement(3));
  }

  /**
   * Tests that the bookworm achievement will be given correctly if there has been enough studying,
   * and it hasn't been obtained before.
   */
  @Test
  public void testGiveBookwormAchievement() {
    achievements.resetAllAchievements();
    assertTrue(achievements.bookwormAchievement(4));
    assertTrue(achievements.checkAchievement(2));
  }

  /**
   * Tests that the bookworm achievement will stay given if already obtained.
   */
  @Test
  public void testBookwormAchievementAlreadyGiven() {
    achievements.resetAllAchievements();
    achievements.bookwormAchievement(5);
    assertFalse(achievements.bookwormAchievement(5));
  }

  /**
   * Tests that the duckduckgo achievement won't be given if the number of times is too low.
   */
  @Test
  public void testDuckDuckGoAchievementNotEnough() {
    achievements.resetAllAchievements();
    assertFalse(achievements.duckDuckGoAchievement(5));
  }

  /**
   * Tests that the duckduckgo achievement will be given correctly if there has been enough
   * studying, and it hasn't been obtained before.
   */
  @Test
  public void testGiveDuckDuckGoAchievement() {
    achievements.resetAllAchievements();
    assertTrue(achievements.duckDuckGoAchievement(6));
    assertTrue(achievements.checkAchievement(3));
  }

  /**
   * Tests that the duckduckgo achievement will stay given if already obtained.
   */
  @Test
  public void testDuckDuckGoAchievementAlreadyGiven() {
    achievements.resetAllAchievements();
    achievements.duckDuckGoAchievement(7);
    assertFalse(achievements.duckDuckGoAchievement(7));
  }

  /**
   * Tests that the jogger achievement won't be given if the number of times is too low.
   */
  @Test
  public void testJoggerAchievementNotEnough() {
    achievements.resetAllAchievements();
    assertFalse(achievements.joggerAchievement(6));
  }

  /**
   * Tests that the jogger achievement will be given correctly if there has been enough
   * studying, and it hasn't been obtained before.
   */
  @Test
  public void testGiveJoggerAchievement() {
    achievements.resetAllAchievements();
    assertTrue(achievements.joggerAchievement(7));
    assertTrue(achievements.checkAchievement(4));
  }

  /**
   * Tests that the jogger achievement will stay given if already obtained.
   */
  @Test
  public void testJoggerAchievementAlreadyGiven() {
    achievements.resetAllAchievements();
    achievements.joggerAchievement(8);
    assertFalse(achievements.joggerAchievement(8));
  }
}

package com.skloch.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.skloch.game.Achievement;
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

  @Test
  public void testCheckNonexistentAchievement() {
    assertFalse(achievements.checkAchievement(99979));
  }

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

  @Test
  public void testGetUserAchievements() {
    achievements.resetAllAchievements();
    achievements.giveAchievement(1);
    assertEquals(achievements.getUserAchievements(),
        "- Tree? +100: Talk to the mysterious tree.\n");
  }

  @Test
  public void testBookwormAchievementNotEnough() {
    achievements.resetAllAchievements();
    assertFalse(achievements.BookwormAchievement(3));
  }

  @Test
  public void testGiveBookwormAchievement() {
    achievements.resetAllAchievements();
    assertTrue(achievements.BookwormAchievement(4));
    assertTrue(achievements.checkAchievement(2));
  }

  @Test
  public void testBookwormAchievementAlreadyGiven() {
    achievements.resetAllAchievements();
    achievements.BookwormAchievement(5);
    assertFalse(achievements.BookwormAchievement(5));
  }

  @Test
  public void testDuckDuckGoAchievementNotEnough() {
    achievements.resetAllAchievements();
    assertFalse(achievements.DuckDuckGoAchievement(5));
  }

  @Test
  public void testGiveDuckDuckGoAchievement() {
    achievements.resetAllAchievements();
    assertTrue(achievements.DuckDuckGoAchievement(6));
    assertTrue(achievements.checkAchievement(3));
  }

  @Test
  public void testDuckDuckGoAchievementAlreadyGiven() {
    achievements.resetAllAchievements();
    achievements.DuckDuckGoAchievement(7);
    assertFalse(achievements.DuckDuckGoAchievement(7));
  }

  @Test
  public void testJoggerAchievementNotEnough() {
    achievements.resetAllAchievements();
    assertFalse(achievements.JoggerAchievement(6));
  }

  @Test
  public void testGiveJoggerAchievement() {
    achievements.resetAllAchievements();
    assertTrue(achievements.JoggerAchievement(7));
    assertTrue(achievements.checkAchievement(4));
  }

  @Test
  public void testJoggerAchievementAlreadyGiven() {
    achievements.resetAllAchievements();
    achievements.JoggerAchievement(8);
    assertFalse(achievements.JoggerAchievement(8));
  }
}

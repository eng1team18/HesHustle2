package com.skloch.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.skloch.game.Achievement;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core Achievement class.
 */
@RunWith(GdxTestRunner.class)
public class AchievementTests {
  Achievement achievements = Achievement.getInstance();

  @Test
  public void testCheckNonexistentAchievement() {
    assertFalse(achievements.checkAchievement(99979));
  }

  @Test
  public void testGiveAchievement() {
    // First check that achievement 1 being unlocked is false by default.
    assertFalse(achievements.checkAchievement(1));

    // Now check giving the achievement makes it true.
    achievements.giveAchievement(1);
    assertTrue(achievements.checkAchievement(1));

    // Verifying that it doesn't raise an unexpected error to give an already obtained achievement,
    // or an achievement that doesn't exist.
    achievements.giveAchievement(1);
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
    achievements.giveAchievement(1);
    assertEquals(achievements.getUserAchievements(),
        "- Tree? +100: Talk to the mysterious tree\n");
  }

}

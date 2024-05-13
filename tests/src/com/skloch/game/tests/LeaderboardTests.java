package com.skloch.game.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.skloch.game.Leaderboard;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core Leaderboard class.
 */
@RunWith(GdxTestRunner.class)
public class LeaderboardTests {
  Leaderboard leaderboard = Leaderboard.getInstance();

  @Test
  public void testGetTopScores() {
    ;
  }


  @Test
  public void testScoreFileExists() {
    FileHandle file = Gdx.files.internal(leaderboard.returnFileName());
    System.out.println(file);
    assertTrue(file.exists());
  }
}
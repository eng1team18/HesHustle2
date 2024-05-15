package com.skloch.game.tests;

import static java.nio.file.Files.deleteIfExists;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.skloch.game.Leaderboard;
import java.io.IOException;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core Leaderboard class.
 */
@RunWith(GdxTestRunner.class)
public class LeaderboardTests {
  // deleteIfExists(Paths.get("leaderboard.json"));
  Leaderboard leaderboard = Leaderboard.getInstance();

  //@Before


  @Test
  public void testSaveScore() {
    leaderboard.saveScore("Test", 1300);
    System.out.println(leaderboard.getTopScores());
  }


  @Test
  public void testScoreFileExists() {
    FileHandle file = Gdx.files.internal(leaderboard.returnFileName());
    System.out.println(file);
    assertTrue(file.exists());
  }
}
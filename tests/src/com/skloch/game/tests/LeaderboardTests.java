package com.skloch.game.tests;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;
import static java.nio.file.Files.deleteIfExists;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.skloch.game.Leaderboard;
import com.skloch.game.Leaderboard.ScoreEntry;
import java.io.IOException;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core Leaderboard class.
 */
@FixMethodOrder
@RunWith(GdxTestRunner.class)
public class LeaderboardTests {
  Leaderboard leaderboard = Leaderboard.getInstance();

  @BeforeClass
  public static void setUpClass() throws IOException {
    deleteIfExists(Paths.get("../tests/leaderboard.json"));
  }

  @Test
  public void testReturnFileName() {
    assertEquals(leaderboard.returnFileName(), Leaderboard.SCORE_FILE);
  }

  @Test
  public void testScoresStoredAndReturnedCorrectly() {
    leaderboard.saveScore("4th Place", 0);
    leaderboard.saveScore("1st Place", 80);
    leaderboard.saveScore("3rd Place", 20);
    leaderboard.saveScore("2nd Place", 40);
    String expectedScores = "1. 1st Place : 80\n2. 2nd Place : 40\n"
        + "3. 3rd Place : 20\n4. 4th Place : 0\n";

    assertEquals(leaderboard.getFormattedTopScores(), expectedScores);
  }

  @Test
  public void testOnlyTopTenScoresKept() {
    leaderboard.saveScore("10th Player", 500);
    leaderboard.saveScore("1st Player", 1000);
    leaderboard.saveScore("2nd Player", 950);
    leaderboard.saveScore("3rd Player", 900);
    leaderboard.saveScore("4th Player", 850);
    leaderboard.saveScore("5th Player", 800);
    leaderboard.saveScore("6th Player", 750);
    leaderboard.saveScore("7th Player", 700);
    leaderboard.saveScore("8th Player", 650);
    leaderboard.saveScore("9th Player", 600);
    leaderboard.saveScore("10th Player", 550);

    String expectedLastPlace = "10. 10th Player : 550\n";
    String actualScores = leaderboard.getFormattedTopScores();
    assertEquals(actualScores.substring(actualScores.length() - 22), expectedLastPlace);
  }
}
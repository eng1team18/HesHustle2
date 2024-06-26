package com.skloch.game.tests;

import static java.nio.file.Files.deleteIfExists;
import static org.junit.Assert.assertEquals;

import com.skloch.game.scoring.Leaderboard;
import java.io.IOException;
import java.nio.file.Paths;
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
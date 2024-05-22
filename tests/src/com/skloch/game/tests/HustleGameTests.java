package com.skloch.game.tests;

import static org.junit.Assert.assertEquals;

import com.skloch.game.HustleGame;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core HustleGame class.
 */
@RunWith(GdxTestRunner.class)
public class HustleGameTests {

  HustleGame game = new HustleGame(800, 400);

  @Test
  public void testReadTextFile() {
    String credits = game.readTextFile("Text/credits.txt");
    assertEquals(credits.substring(0, 21), "Improvements Made By:");
  }

  @Test
  public void testReadTextFileNonexistentFile() {
    String filepath = "Text/non_existent_file.txt";
    String returnedText = game.readTextFile(filepath);
    assertEquals(returnedText, "Couldn't load " + filepath);
  }
}

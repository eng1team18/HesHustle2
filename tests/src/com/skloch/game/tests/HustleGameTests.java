package com.skloch.game.tests;

import static java.nio.file.Files.deleteIfExists;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.skloch.game.HustleGame;
import com.skloch.game.Leaderboard;
import com.skloch.game.MenuScreen;
import java.io.IOException;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.BeforeClass;
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

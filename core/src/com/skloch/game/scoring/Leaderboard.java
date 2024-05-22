package com.skloch.game.scoring;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

/**
 * NEW CLASS FOR ASSESSMENT 2
 * Class for the leaderboard. Will track player's high scores and rank them, and save this in
 * a JSON file.
 */
public class Leaderboard {

  public static final String SCORE_FILE = "leaderboard.json";
  private Json json = new Json();
  private Array<ScoreEntry> scores = new Array<>();
  private static final Leaderboard instance = new Leaderboard();

  /**
   * Returns the current instance of leaderboard.
   *
   * @return the current instance of leaderboard
   */
  public static Leaderboard getInstance() {
    return instance;
  }

  /**
   * Writes the player's score into the score JSON file. Will truncate the scores to a maximum
   * of 10, so if any scores are not in the top 10, they will not be added.
   *
   * @param playerName the name of the player
   * @param score the score the player achieved
   */
  public void saveScore(String playerName, int score) {
    FileHandle file = Gdx.files.local(SCORE_FILE);

    // Check if file exist and load if it does
    if (file.exists()) {
      String scoresJson = file.readString();
      scores = json.fromJson(Array.class, ScoreEntry.class, scoresJson);
    }

    scores.add(new ScoreEntry(playerName, score));

    scores.sort();

    if (scores.size > 10) {
      scores.truncate(10);
    }

    file.writeString(json.toJson(scores, Array.class, ScoreEntry.class), false);
  }

  /**
   * Returns an array of all score entries in the leaderboard.
   *
   * @return an array of all score entries in the leaderboard
   */
  public Array<ScoreEntry> getTopScores() {
    FileHandle file = Gdx.files.local(SCORE_FILE);
    if (file.exists()) {
      String scoresJson = file.readString();
      return json.fromJson(Array.class, ScoreEntry.class, scoresJson);
    }
    return new Array<>();
  }

  /**
   * Returns the name of the score JSON file.
   *
   * @return the name of the score JSON file
   */
  public String returnFileName() {
    return SCORE_FILE;
  }

  /**
   * Returns a formatted string of all scores in the leaderboard and player names relating to
   * these, separated with colons and line breaks.
   *
   * @return a string of the formatted scores
   */
  public String getFormattedTopScores() {
    StringBuilder sb = new StringBuilder();
    Array<ScoreEntry> topScores = getTopScores();
    for (int i = 0; i < topScores.size; i++) {
      sb.append(
          (i + 1) + ". " + topScores.get(i).playerName + " : " + topScores.get(i).score + "\n");
    }
    return sb.toString();
  }

  /**
   * The class ScoreEntry that represents individual scores.
   */
  public static class ScoreEntry implements Comparable<ScoreEntry> {

    public String playerName;
    public int score;

    // Do not delete, ScoreEntry gets run when LeaderboardScreen is being displayed
    public ScoreEntry() {
    }

    /**
     * The constructor for ScoreEntry, combining the player name and their score into one
     * object.
     *
     * @param playerName the name of the player
     * @param score the score the player achieved
     */
    public ScoreEntry(String playerName, int score) {
      this.playerName = playerName;
      this.score = score;
    }

    /**
     * Checks which score out of two ScoreEntrys is higher.
     *
     * @param other the object to be compared.
     * @return which ScoreEntry is higher
     */
    @Override
    public int compareTo(ScoreEntry other) {
      return Integer.compare(other.score, this.score);
    }
  }
}

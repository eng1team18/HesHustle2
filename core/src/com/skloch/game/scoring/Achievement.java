package com.skloch.game.scoring;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * NEW CLASS FOR ASSESSMENT 2
 * A class that manages the achievement system. Will track completion of achievements, give points
 * when achievements are earned, and display them in GameOverScreen
 */
public class Achievement {

  private Map<Integer, AchievementEntry> achievements;
  private static final Achievement instance = new Achievement();
  private int scoreGainedPer = 100;

  /**
   * The constructor for achievement. Defines all achievement names and descriptions.
   */
  public Achievement() {
    achievements = new HashMap<>();
    addAchievement(1, "Tree?", "Talk to the mysterious tree.");
    addAchievement(2, "Bookworm", "Study at least 4 times this week.");
    addAchievement(3, "Duck duck go!", "Feed the ducks at least 6 times.");
    addAchievement(4, "Jogger", "Go for a walk every day during the week");
  }

  /**
   * Returns the current instance of achievement class.
   *
   * @return the current instance of achievement class
   */
  public static Achievement getInstance() {
    return instance;
  }

  /**
   * Add a new instance of achievement entry with the achievement name, ID and description.
   *
   * @param id the unique ID of the achievement
   * @param name the name of the achievement
   * @param desc the description of the achievement
   */
  private void addAchievement(int id, String name, String desc) {
    achievements.put(id, new AchievementEntry(id, name, desc));
  }

  /**
   * Sets an achievement to achieved if the requirements are met so it cannot be achieved
   * multiple times.
   *
   * @param id the unique ID of the achieved achievement
   */
  public void giveAchievement(int id) {
    AchievementEntry achievement = achievements.get(id);
    if (achievement != null && !achievement.isAchieved()) {
      achievement.setAchieved(true);
    }
  }

  /**
   * Reset all achievements to not achieved.
   */
  public void resetAllAchievements() {
    for (AchievementEntry achievement : achievements.values()) {
      achievement.setAchieved(false);
    }
  }

  /**
   * Gives a list of all achievements achieved by the player.
   *
   * @return returns a string of all achievements achieved
   */
  public String getUserAchievements() {
    return achievements.values().stream()
        .filter(AchievementEntry::isAchieved)
        .map(achievement -> "- " + achievement.getName() + " +" + scoreGainedPer + ": "
            + achievement.getDesc() + "\n")
        .collect(Collectors.joining());
  }

  /**
   * Check if an achievement has been achieved or not and return this state.
   *
   * @param id the unique ID of the achievement
   * @return the boolean state of if the achievement has been achieved or not
   */
  public boolean checkAchievement(int id) {
    AchievementEntry achievement = achievements.get(id);
    if (achievement != null) {
      return achievement.isAchieved();
    }
    return false;
  }

  /**
   * Definition of the bookworm achievement. Received if player studies >=4 times a week.
   *
   * @param studyCount the number of times the player studied in the week
   * @return true if the achievement has been received, else false
   */
  public boolean bookwormAchievement(int studyCount) {
    if (studyCount >= 4) {
      if (!Achievement.getInstance().checkAchievement(2)) {
        Achievement.getInstance().giveAchievement(2);
        return true;
      }
    }
    return false;
  }

  /**
   * Definition of the duckDuckGo achievement. Received if player feeds the ducks >=6 times.
   *
   * @param duckCount the number of times the player fed the ducks in the week
   * @return true if the achievement has been received, else false
   */
  public boolean duckDuckGoAchievement(int duckCount) {
    if (duckCount >= 6) {
      if (!Achievement.getInstance().checkAchievement(3)) {
        Achievement.getInstance().giveAchievement(3);
        return true;
      }
    }
    return false;
  }

  /**
   * Definition of the jogger achievement. Received if player goes on the walking trail
   * >=7 times a week.
   *
   * @param walkCount the number of times the player walked in the week
   * @return true if the achievement has been received, else false
   */
  public boolean joggerAchievement(int walkCount) {
    if (walkCount >= 7) {
      if (!Achievement.getInstance().checkAchievement(4)) {
        Achievement.getInstance().giveAchievement(4);
        return true;
      }
    }
    return false;
  }

  /**
   * Class containing individual achievements, including their name, unique ID, description and
   * boolean achieved.
   */
  public class AchievementEntry {

    private int id;
    private String name;
    private String desc;
    private boolean achieved;

    /**
     * The constructor for AchievementEntry, defining each individual achievement.
     *
     * @param id the achievement's unique ID
     * @param name the achievement's name
     * @param desc the description of the achievement
     */
    public AchievementEntry(int id, String name, String desc) {
      this.id = id;
      this.name = name;
      this.desc = desc;
      this.achieved = false;
    }

    /**
     * Return the ID of the achievement.
     *
     * @return the ID of the achievement
     */
    public int getId() {
      return id;
    }

    /**
     * Return the name of the achievement.
     *
     * @return the name of the achievement
     */
    public String getName() {
      return name;
    }

    /**
     * Return the description of the achievement.
     *
     * @return the description of the achievement
     */
    public String getDesc() {
      return desc;
    }

    /**
     * Return if the achievement has been achieved.
     *
     * @return true if it has been achieved, false otherwise
     */
    public boolean isAchieved() {
      return achieved;
    }

    /**
     * Sets if the achievement has been achieved. True if yes, false otherwise.
     */
    public void setAchieved(boolean achieved) {
      this.achieved = achieved;
    }
  }
}

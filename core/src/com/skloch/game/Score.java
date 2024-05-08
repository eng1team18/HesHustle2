package com.skloch.game;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Counter to track your score when you perform various activities within the game. Each activity is
 * identified by an ID.
 */
public class Score {

  private int totalScore;
  private Map<Integer, ScoreActivity> activities;

  private static final Score instance = new Score();

  private Score() {
    activities = new HashMap<>();
    initializeActivities();
  }

  public static Score getInstance() {
    return instance;
  }

  // New
  private int lastDay = 0;
  // pos 0 is piazza
  // pos 1 TBD and so forth
  private int[] recActivityLog = {0, 0, 0, 0, 0}; // Add more based on number of different rec activities

  private void initializeActivities() {
    // Initialize with some example activities
    addActivity(1, "Eating");
    addActivity(2, "Studying");
    addActivity(3, "Recreation");
    addActivity(4, "Sleeping");
    addActivity(5, "Achievements");
  }

  public void addActivity(int id, String name) {
    activities.put(id, new ScoreActivity(name));
  }

  //New method
  public int hungerScore(int timeOfDay, int timeLastEat) {
    double x = timeOfDay - timeLastEat;
    //Multiplier, change to shorten wait time
    int multiplier = 800;
    x = x / multiplier;
    x = (Math.exp(x) / (1 + Math.exp(x)) - 0.5);
    //Double max score awarded for a meal
    int maxScore = 500;
    return (int) Math.round(maxScore * x);
  }

  //New
  public int activityScore(int activityType, int currentDay) {
    if (lastDay != currentDay) {
      lastDay = currentDay;
      recActivityLog = new int[]{0, 0, 0, 0, 0};
    }
    recActivityLog[activityType] += 1;
    int recreationScore = 200; // is changeable
    return recreationScore / recActivityLog[activityType];
  }

  public int getTotalScore() {
    return totalScore;
  }

  public void incrementTotalScore(int id, int scoreToAdd) {
    this.totalScore += scoreToAdd;
    ScoreActivity activity = activities.get(id);
    if (activity != null) {
      activity.addScore(scoreToAdd);
    }
  }

  public void setTotalScore(int value) {
    this.totalScore = value;
  }

  public void resetScores() {
    totalScore = 0;
    activities.values().forEach(ScoreActivity::resetScore);
  }

  public String getUserScores() {
    return activities.values().stream()
        .map(activity -> activity.getName() + ": " + activity.getScore() + "\n")
        .collect(Collectors.joining());
  }


  public int getScoreByActivity(int id) {
    return activities.containsKey(id) ? activities.get(id).getScore() : 0;
  }

  public class ScoreActivity {

    private String name;
    private int score;

    public ScoreActivity(String name) {
      this.name = name;
      this.score = 0;
    }

    public void addScore(int amount) {
      this.score += amount;
    }

    public String getName() {
      return name;
    }

    public int getScore() {
      return score;
    }

    public void resetScore() {
      this.score = 0;
    }
  }
}

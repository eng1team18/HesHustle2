package com.skloch.game.scoring;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * NEW FOR ASSESSMENT 2
 * Counter to track your score when you perform various activities within the game. Each activity is
 * identified by an ID.
 */
public class Score {

  private int totalScore;
  public int numEating;
  public int numStudying;
  public int numBus;
  public int numRecreationalWalk;
  public int numRecreationalDuck;
  public int numRecreationalBar;
  public int numRecreationalFriends;

  public int numSleeping;
  private Map<Integer, ScoreActivity> activities;

  private static final Score instance = new Score();

  /**
   * The constructor for Score, which creates a hashmap of all activities.
   */
  private Score() {
    activities = new HashMap<>();
    initializeActivities();
  }

  /**
   * Returns current instance of Score.
   *
   * @return the current instance of score
   */
  public static Score getInstance() {
    return instance;
  }

  // New
  private int lastDay = 0;
  // pos 0 is piazza
  // pos 1 TBD and so forth
  private int[] recActivityLog = {0, 0, 0, 0};
  // Add more based on number of different rec activities

  /**
   * Initialises the activity names with their IDs.
   */
  private void initializeActivities() {
    // Initialize with some example activities
    addActivity(1, "Eating");
    addActivity(2, "Studying");
    addActivity(3, "Recreation");
    addActivity(4, "Sleeping");
    addActivity(5, "Achievements");
  }

  /**
   * Add a new activity into the hashmap.
   *
   * @param id the unique ID of the activity
   * @param name the name of the activity
   */
  public void addActivity(int id, String name) {
    activities.put(id, new ScoreActivity(name));
  }

  /**
   * The algorithm for calculating score from eating activities. After eating, set points to
   * minimum. Increase points along a sigmoid function as time passes up to a maximum. When
   * you eat, give number of points that function is on and reset back to minimum.
   *
   * @param timeOfDay the current time of day
   * @param timeLastEat the time player last ate
   * @return the number of points the player receives for eating at that time
   */
  public int hungerScore(int timeOfDay, int timeLastEat) {
    double x = timeOfDay - timeLastEat;
    //Multiplier, change to shorten wait time
    int multiplier = 240;
    x = x / multiplier;
    x = (Math.exp(x) / (1 + Math.exp(x)) - 0.5);
    //Double max score awarded for a meal
    int maxScore = 500;
    return (int) Math.round(maxScore * x);
  }

  /**
   * The algorithm for calculating score from recreational activities. If an activity is
   * performed, add 1 to corresponding entry in the recActivityLog. If that activity is performed
   * again on that day, reduce points given by a factor of its recActivityLog entry.
   *
   * @param activityType the type of recreational activity being performed
   * @param currentDay the current game day
   * @return the number of points the player receives for that activity
   */
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

  /**
   * Returns the total score the player currently has.
   *
   * @return the total score the player currently has
   */
  public int getTotalScore() {
    return totalScore;
  }

  public int getNumRecreationalWalk() {
    return numRecreationalWalk;
  }

  public int getNumRecreationalDuck() {
    return numRecreationalDuck;
  }

  public int getNumStudying() {
    return numStudying;
  }

  public int getNumSleeping() {
    return numSleeping;
  }

  /**
   * Increment the total player score by the amount they should receive from the activity taken.
   *
   * @param id the ID of the activity completed
   * @param scoreToAdd the amount of score they should receive from the activity
   */
  public void incrementTotalScore(int id, int scoreToAdd) {
    this.totalScore += scoreToAdd;
    ScoreActivity activity = activities.get(id);
    if (activity != null) {
      activity.addScore(scoreToAdd);
    }
  }

  public void incrementNumEating() {
    this.numEating += 1;
  }

  public void incrementNumBus() {
    this.numBus += 1;
  }

  public void incrementNumRecreationalWalk() {
    this.numRecreationalWalk += 1;
  }

  public void incrementNumRecreationalDuck() {
    this.numRecreationalDuck += 1;
  }

  public void incrementNumRecreationalBar() {
    this.numRecreationalBar += 1;
  }

  public void incrementNumRecreationalFriends() {
    this.numRecreationalFriends += 1;
  }

  public void incrementNumStudying() {
    this.numStudying += 1;
  }

  public void incrementNumSleeping() {
    this.numSleeping += 1;
  }

  public void setTotalScore(int value) {
    this.totalScore = value;
  }

  /**
   * Reset the activity counts back to 0 for when a new game is started.
   */
  public void resetScores() {
    totalScore = 0;
    numEating = 0;
    numBus = 0;
    numRecreationalWalk = 0;
    numRecreationalDuck = 0;
    numRecreationalBar = 0;
    numRecreationalFriends = 0;
    numStudying = 0;
    numSleeping = 0;
    activities.values().forEach(ScoreActivity::resetScore);
    recActivityLog = new int[]{0, 0, 0, 0};
  }

  /**
   * Return the scores achieved for each activity by the player in a formatted string.
   *
   * @return a string of the scores achieved for each activity
   */
  public String getUserScores() {
    return activities.values().stream()
        .map(activity -> activity.getName() + ": " + activity.getScore() + "\n")
        .collect(Collectors.joining());
  }


  public int getScoreByActivity(int id) {
    return activities.containsKey(id) ? activities.get(id).getScore() : 0;
  }

  public Map getActivities() {
    return activities;
  }

  /**
   * Class for the score breakdown screen. Stores amount of score earned for each type of
   * activity, i.e. recreational, eating, sleeping, studying and achievements.
   */
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

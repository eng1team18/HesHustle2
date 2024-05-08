package com.skloch.game;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Achievement {

  private Map<Integer, AchievementEntry> achievements;
  private static final Achievement instance = new Achievement();
  private int scoreGainedPer = 100;

  public Achievement() {
    achievements = new HashMap<>();
    addAchievement(1, "Tree?", "Talk to the mysterious tree");
  }

  public static Achievement getInstance() {
    return instance;
  }

  private void addAchievement(int id, String name, String desc) {
    achievements.put(id, new AchievementEntry(id, name, desc));
  }

  public void giveAchievement(int id) {
    AchievementEntry achievement = achievements.get(id);
    if (achievement != null && !achievement.isAchieved()) {
      achievement.setAchieved(true);
    }
  }

  public void resetAllAchievements() {
    for (AchievementEntry achievement : achievements.values()) {
      achievement.setAchieved(false);
    }
  }

  public String getUserAchievements() {
    return achievements.values().stream()
        .filter(AchievementEntry::isAchieved)
        .map(achievement -> "- " + achievement.getName() + " +" + scoreGainedPer + ": "
            + achievement.getDesc() + "\n")
        .collect(Collectors.joining());
  }

  public boolean checkAchievement(int id) {
    AchievementEntry achievement = achievements.get(id);
    if (achievement != null) {
      return achievement.isAchieved();
    }
    return false;
  }

  public class AchievementEntry {

    private int id;
    private String name;
    private String desc;
    private boolean achieved;

    public AchievementEntry(int id, String name, String desc) {
      this.id = id;
      this.name = name;
      this.desc = desc;
      this.achieved = false;
    }

    public int getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getDesc() {
      return desc;
    }

    public boolean isAchieved() {
      return achieved;
    }

    public void setAchieved(boolean achieved) {
      this.achieved = achieved;
    }
  }
}

package com.skloch.game.gamelogic;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.skloch.game.screens.GameScreen;

/**
 * NEW CLASS FOR ASSESSMENT 2 - Refactored from GameScreen
 * A class that manages the in game time, and displays it on the games HUD.
 */
public class Time {

  public float daySeconds = 0; // Current seconds elapsed in day
  public int day = 1; // What day the game is on
  public Label timeLabel;
  public Label dayLabel;
  public int hoursStudied;
  public int hoursRecreational;
  public int hoursSlept;
  private final GameScreen game;

  public Time(GameScreen game) {
    this.game = game;
  }

  /**
   * Takes a time in seconds and formats it a time in the format HH:MMam/pm.
   *
   * @param seconds The seconds elapsed in a day
   * @return A formatted time on a 12-hour clock
   */
  public static String formatTime(int seconds) {
    // Takes a number of seconds and converts it into a 12-hour clock time
    int hour = Math.floorDiv(seconds, 60);
    String minutes = String.format("%02d", (seconds - hour * 60));

    // Make 12 hour
    if (hour == 24 || hour == 0) {
      return String.format("12:%sam", minutes);
    } else if (hour == 12) {
      return String.format("12:%spm", minutes);
    } else if (hour > 12) {
      return String.format("%d:%spm", hour - 12, minutes);
    } else {
      return String.format("%d:%sam", hour, minutes);
    }
  }

  /**
   * Returns 'breakfast', 'lunch' or 'dinner' depending on the time of day.
   */
  public String getMeal() {
    int hours = Math.floorDiv((int) daySeconds, 60);
    if (hours >= 7 && hours <= 10) {
      // Breakfast between 7:00-10:59am
      return "breakfast";
    } else if (hours > 10 && hours <= 16) {
      // Lunch between 10:00am and 4:59pm
      return "lunch";
    } else if (hours > 16 && hours <= 21) {
      // Dinner served between 4:00pm and 9:59pm
      return "dinner";
    } else {
      // Nothing is served between 10:00pm and 6:59am
      return "food";
    }
  }

  /**
   * Add a number of seconds to the time elapsed in the day.
   *
   * @param delta The time in seconds to add
   */
  public void passTime(float delta) {
    daySeconds += delta;
    while (daySeconds >= 1440) {
      daySeconds -= 1440;
      day += 1;

      game.updateDay(dayLabel, String.format("Day %s", day));
    }

    if (day >= 8) {
      game.gameOver();
    }
  }

  /**
   * Adds an amount of hours to the total hours slept.
   *
   * @param hours The amount of hours to add
   */
  public void addSleptHours(int hours) {
    hoursSlept += hours;
  }

  /**
   * Adds an amount of hours studied to the total hours studied.
   *
   * @param hours The amount of hours to add
   */
  public void addStudyHours(int hours) {
    hoursStudied += hours;
  }

  /**
   * Adds an amount of recreational hours to the total amount for the current day.
   *
   * @param hours The amount of hours to add
   */
  public void addRecreationalHours(int hours) {
    hoursRecreational += hours;
  }


  /**
   * Returns a wake-up message based on the time left until the exam.
   */
  public String getWakeUpMessage() {
    int daysLeft = 8 - day;
    if (daysLeft != 1) {
      return String.format(
          "You have %d days left until your exam!\nRemember to eat, study and have fun, "
              + "but don't overwork yourself!", daysLeft);
    } else {
      return "Your exam is tomorrow! I hope you've been studying! Remember not to overwork "
          + "yourself and get enough sleep!";
    }
  }


  /**
   * Returns the number of seconds elapsed in the day.
   */
  public float getSeconds() {
    return daySeconds;
  }
}

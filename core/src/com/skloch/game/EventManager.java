package com.skloch.game;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

// Changes
//
// - Time check for piazzaEvent, compSciEvent, ronCookeEvent was remove to support the no time ticking change
// - Energy now use the Energy class instead of GameScreen
//

/**
 * A class that maps Object's event strings to actual Java functions.
 */
public class EventManager {

  private final GameScreen game;
  public HashMap<String, Integer> activityEnergies;
  private final HashMap<String, String> objectInteractions;
  private final Array<String> talkTopics;
  Score score = Score.getInstance();
  private Energy energyBar;

  // New Stuff
  private boolean studiedToday = false;
  private boolean catchUpUsed = false;
  private boolean missedDay = false;
  private int dayLastStudied = 0;
  private int timeLastEat = 0;

  /**
   * A class that maps Object's event strings to actual Java functions. To run a function call
   * event(eventString), to add arguments add dashes. E.g. a call to the Piazza function with an arg
   * of 1 would be: "piazza-1" Which the function interprets as "study at the piazza for 1 hour".
   * Object's event strings can be set in the Tiled map editor with a property called "event"
   *
   * @param game An instance of the GameScreen containing a player and dialogue box
   */
  public EventManager(GameScreen game, Energy energyBar) {
    this.game = game;
    this.energyBar = energyBar;

    // How much energy an hour of each activity should take
    activityEnergies = new HashMap<String, Integer>();
    activityEnergies.put("studying", 30);
    activityEnergies.put("meet_friends", 10);
    activityEnergies.put("walk", 10);
    activityEnergies.put("ducks", 10);
    activityEnergies.put("bus", 10);
    activityEnergies.put("eating", 10);

    // Define what to say when interacting with an object whose text won't change
    objectInteractions = new HashMap<String, String>();
    objectInteractions.put("chest", "Open the chest?");
    objectInteractions.put("ron_cooke", "Study in the Ron Cooke building?");
    objectInteractions.put("friends", "Talk to your friends?");
    objectInteractions.put("accomodation",
            "Go to sleep for the night?\nYour alarm is set for 8am.");
    objectInteractions.put("piazza", null); // Changes, dynamically returned in getObjectInteraction
    objectInteractions.put("tree", "Speak to the tree?");
    objectInteractions.put("walk", "Go on a walk in the woods?");
    objectInteractions.put("ducks", "Feed the ducks?");
    objectInteractions.put("bus", "Get the bus to town?");

    // Some random topics that can be chatted about
    String[] topics = {"Dogs", "Cats", "Exams", "Celebrities", "Flatmates", "Video games", "Sports",
            "Food", "Fashion"};
    talkTopics = new Array<String>(topics);
  }

  public void event(String eventKey) {
    String[] args = eventKey.split("-");

    // Important functions, most likely called after displaying text
    if (args[0] == "fadefromblack") {
      fadeFromBlack();
    } else if (args[0] == "fadetoblack") {
      fadeToBlack();
    } else if (args[0] == "gameover") {
      game.GameOver();
    }

    // Events related to objects
    switch (args[0]) {
      case "tree":
        treeEvent();
        break;
      case "chest":
        chestEvent();
        break;
      case "friends":
        friendsEvent(args);
        break;
      case "ron_cooke":
        ronCookeEvent(args);
        break;
      case "piazza":
        piazzaEvent(args);
        break;
      case "accomodation":
        accomEvent(args);
        break;
      case "walk":
        walk(args);
        break;
      case "ducks":
        ducks(args);
        break;
      case "bus":
        bus(args);
        break;
      case "exit":
        // Should do nothing and just close the dialogue menu
        game.dialogueBox.hide();
        break;
      default:
        objectEvent(eventKey);
        break;

    }

  }

  /**
   * Gets the interaction text associated with each object via a key
   *
   * @param key
   * @return The object interaction text
   */
  public String getObjectInteraction(String key) {
    if (key.equals("piazza")) {
      return String.format("Eat %s at the Piazza Building?", game.getMeal());
    } else {
      return objectInteractions.get(key);
    }
  }

  /**
   * @return True if the object has some custom text to display that isn't just "This is an x!"
   */
  public boolean hasCustomObjectInteraction(String key) {
    return objectInteractions.containsKey(key);
  }

  /**
   * Sets the text when talking to a tree
   */
  public void treeEvent() {
    game.dialogueBox.hideSelectBox();
    game.dialogueBox.setText("The tree doesn't say anything back.");
  }


  public void chestEvent() {
    game.dialogueBox.hideSelectBox();
    game.dialogueBox.setText(
            "Wow! This chest is full of so many magical items! I wonder how they will help you out on your journey! Boy, this is an awfully long piece of text, I wonder if someone is testing something?\n...\n...\n...\nHow cool!");

  }

  /**
   * Sets the text when talking to an object without a dedicated function
   */
  public void objectEvent(String object) {
    game.dialogueBox.hideSelectBox();
    game.dialogueBox.setText("This is a " + object + "!");
  }

  /**
   * Lets the player study at the Ron cooke hub for 3 hours, decreases the player's energy and
   * increments the game time.
   *
   * @param args
   */
  public void friendsEvent(String[] args) {
    int energyCost = activityEnergies.get("meet_friends");
    // If the player is too tired to meet friends
    if (energyBar.getEnergy() < energyCost) {
      game.dialogueBox.setText("You are too tired to meet your friends right now!");

    } else if (args.length == 1) {
      // Ask the player to chat about something (makes no difference)
      String[] topics = randomTopics(3);
      game.dialogueBox.setText("What do you want to chat about?");
      game.dialogueBox.getSelectBox().setOptions(topics,
              new String[]{"piazza-" + topics[0], "piazza-" + topics[1], "piazza-" + topics[2]});
    } else {
      // Say that the player chatted about this topic for 1-3 hours
      // RNG factor adds a slight difficulty (may consume too much energy to study)
      int hours = ThreadLocalRandom.current().nextInt(1, 4);
      game.dialogueBox.setText(
              String.format("You talked about %s for %d hours!", args[1].toLowerCase(), hours));
      //New
      score.incrementTotalScore(3, score.activityScore(0, game.day));
      energyBar.decreaseEnergy(energyCost * hours);
      game.passTime(hours * 60); // in seconds
      game.addRecreationalHours(hours);
    }
  }

  /**
   * @param amount The amount of topics to return
   * @return An array of x random topics the player can chat about
   */
  private String[] randomTopics(int amount) {
    // Returns an array of 3 random topics
    Array<String> topics = new Array<String>(amount);

    for (int i = 0; i < amount; i++) {
      String choice = talkTopics.random();
      // If statement to ensure topic hasn't already been selected
      if (!topics.contains(choice, false)) {
        topics.add(choice);
      } else {
        i -= 1;
      }
    }

    return topics.toArray(String.class);
  }

  /**
   * The event to be run when interacting with the computer science building Gives the player the
   * option to study for 2, 3 or 4 hours
   *
   * @param args
   */
  public void ronCookeEvent(String[] args) {
    //New
    if (dayLastStudied < game.day) {
      studiedToday = false;
    }
    if (game.day - dayLastStudied > 1) {
      missedDay = true;
    }
    int energyCost = activityEnergies.get("studying");
    // If the player is too tired for any studying:
    if (energyBar.getEnergy() < energyCost) {
      game.dialogueBox.hideSelectBox();
      game.dialogueBox.setText("You are too tired to study right now!");

    } else {
      // If the player does not have enough energy for the selected hours
      if (energyBar.getEnergy() < energyCost) {
        game.dialogueBox.setText("You don't have the energy to study for this long!");
      }
      // New stuff/changes made
      else if (!studiedToday) {
        // If they do have the energy to study and haven't studied today
        studiedToday = true;
        dayLastStudied = game.day;
        game.dialogueBox.setText(
                String.format("You studied for 3 hours!\nYou lost %d energy",
                        energyCost));
        energyBar.decreaseEnergy(energyCost);
        game.addStudyHours(3);
        game.passTime(3 * 60); // in seconds
        score.incrementTotalScore(2, 5);
        Achievement.getInstance().giveAchievement(1);
      } else if (missedDay && !catchUpUsed) {
        // If you have missed a day, this code should only ever be able to be called once
        catchUpUsed = true;
        dayLastStudied = game.day;
        game.dialogueBox.setText(
                String.format("You caught up for 3 hours!\nYou lost %d energy",
                        energyCost));
        energyBar.decreaseEnergy(energyCost);
        game.addStudyHours(3);
        game.passTime(
                3 * 60); // in seconds   POSSIBLY make longer/ shorter as a catchup session?
        score.incrementTotalScore(2, 3); //slightly lower score for catch up
        Achievement.getInstance().giveAchievement(1);

      } else {
        // This should catch the cases where a user tries to study but either already has today or already used their catchup session
        game.dialogueBox.setText("You've already studied as much as you can for today!");
      }
    }
  }


  /**
   * The event to be run when the player interacts with the ron cooke hub Gives the player the
   * choice to eat breakfast, lunch or dinner depending on the time of day
   *
   * @param args
   */
  public void piazzaEvent(String[] args) {
    int energyCost = activityEnergies.get("eating");
    if (energyBar.getEnergy() < energyCost) {
      game.dialogueBox.setText("You are too tired to eat right now!");
    } else {
      game.dialogueBox.setText(
              String.format("You took an hour to eat %s at the Piazza Building!\nYou lost %d energy!",
                      game.getMeal(), energyCost));
      energyBar.decreaseEnergy(energyCost);
      score.incrementTotalScore(1, score.hungerScore(Math.round(game.daySeconds), timeLastEat));
      game.passTime(60); // in seconds
    }

  }

  /**
   * Lets the player go to sleep, fades the screen to black then shows a dialogue about the amount
   * of sleep the player gets Then queues up fadeFromBlack to be called when this dialogue closes
   *
   * @param args Unused currently
   * @see GameScreen fadeToBlack function
   */
  public void accomEvent(String[] args) {
    game.setSleeping(true);
    game.dialogueBox.hide();

    // Calculate the hours slept to the nearest hour
    // Wakes the player up at 8am
    float secondsSlept;
    if (game.getSeconds() < 60 * 8) {
      secondsSlept = (60 * 8 - game.getSeconds());
    } else {
      // Account for the wakeup time being in the next day
      secondsSlept = (((60 * 8) + 1440) - game.getSeconds());
    }
    int hoursSlept = Math.round(secondsSlept / 60f);

    RunnableAction setTextAction = new RunnableAction();
    setTextAction.setRunnable(new Runnable() {
      @Override
      public void run() {
        if (game.getSleeping()) {
          game.dialogueBox.show();
          game.dialogueBox.setText(
                  String.format("You slept for %d hours!\nYou recovered %d energy!", hoursSlept,
                          Math.min(100, hoursSlept * 13)), "fadefromblack");
          // Restore energy and pass time
          energyBar.setEnergy(hoursSlept * 13);
          //New
          score.incrementTotalScore(4, Math.min(hoursSlept * 13, 100));
          game.passTime(secondsSlept);
          game.addSleptHours(hoursSlept);
        }
      }
    });

    fadeToBlack(setTextAction);
  }

  //NEW CODE
  public void walk(String[] args) {
    game.setFadeout(true);
    game.dialogueBox.hide();
    int energyCost = activityEnergies.get("walk");
    // If the player is too tired to meet friends
    if (energyBar.getEnergy() < energyCost) {
      game.dialogueBox.setText("You are too tired to go on a walk right now!");
    } else {
      RunnableAction setTextAction = new RunnableAction();
      setTextAction.setRunnable(new Runnable() {
        @Override
        public void run() {
          game.dialogueBox.show();
          game.dialogueBox.setText(
                  String.format("You went on a walk for 2 hours!\nYou recovered %d energy!",
                          energyCost), "fadefromblack");
          energyBar.decreaseEnergy(energyCost);
          score.incrementTotalScore(3, score.activityScore(2, game.day));
          game.passTime(2 * 60);
        }
      });
      fadeToBlack(setTextAction);
    }
  }

  public void bus(String[] args) {
    int energyCost = activityEnergies.get("bus");
    // If the player is too tired to meet friends
    if (energyBar.getEnergy() < energyCost) {
      game.dialogueBox.setText("You are too tired to into town right now!");
    } else {
      game.dialogueBox.show();
      game.dialogueBox.setText(
              String.format("You went into town for 2 hours!\nYou lost %d energy!", energyCost));
      energyBar.decreaseEnergy(energyCost);
      score.incrementTotalScore(3, score.activityScore(3, game.day));
      game.passTime(2 * 60); // in seconds
    }
  }

  public void ducks(String[] args) {
    int energyCost = activityEnergies.get("ducks");
    // If the player is too tired to meet friends
    if (energyBar.getEnergy() < energyCost) {
      game.dialogueBox.setText("You are too tired to feed the ducks right now!");
    } else {
      game.dialogueBox.show();
      game.dialogueBox.setText(
              String.format("You fed the ducks for 1 hour!\nYou lost %d energy!", energyCost));
      energyBar.decreaseEnergy(energyCost);
      score.incrementTotalScore(3, score.activityScore(3, game.day));
      game.passTime(2 * 60); // in seconds
    }
  }


  /**
   * Fades the screen to black
   */
  public void fadeToBlack() {
    game.blackScreen.addAction(Actions.fadeIn(3f));
  }

  /**
   * Fades the screen to black, then runs a runnable after it is done
   *
   * @param runnable A runnable to execute after fading is finished
   */
  public void fadeToBlack(RunnableAction runnable) {
    game.blackScreen.addAction(Actions.sequence(Actions.fadeIn(3f), runnable));
  }

  /**
   * Fades the screen back in from black, displays a good morning message if the player was
   * sleeping
   */
  public void fadeFromBlack() {
    // If the player is sleeping, queue up a message to be sent
    if (game.getSleeping()) {
      RunnableAction setTextAction = new RunnableAction();
      setTextAction.setRunnable(new Runnable() {
        @Override
        public void run() {
          if (game.getSleeping()) {
            game.dialogueBox.show();
            // Show a text displaying how many days they have left in the game
            game.dialogueBox.setText(game.getWakeUpMessage());
            game.setSleeping(false);
          }
        }
      });
      // Queue up events
      game.blackScreen.addAction(Actions.sequence(Actions.fadeOut(3f), setTextAction));
    } else {
      game.blackScreen.addAction(Actions.fadeOut(3f));
    }
    game.setFadeout(false);
  }
}
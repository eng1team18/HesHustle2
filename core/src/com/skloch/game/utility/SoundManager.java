package com.skloch.game.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

/* ASSESSMENT 2 CHANGES
- Added delay parameter to playFootstep, allowing sprinting to have
  quicker footstep sounds.
*/

/**
 * A class handling loading, playing and disposing of sounds.
 */
public class SoundManager implements Disposable {

  public Music overworldMusic;
  public Music menuMusic;
  private Sound footstep1;
  private Sound footstep2;
  public boolean footstepBool;
  public float footstepTimer;
  private float sfxVolume = 0.8f;
  private float musicVolume = 0.8f;
  private Sound pauseSound;
  private Sound dialogueOpenSound;
  private Sound dialogueOptionSound;
  private Sound buttonSound;

  /**
   * A class to handle playing sounds in the game, handles loading and playing of music and sounds
   * so a GameScreen can just call "play overworld music" without needing to know the track title.
   * Also handles disposing sounds and music.
   */
  public SoundManager() {
    // Load music
    overworldMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/OverworldMusic.mp3"));
    overworldMusic.setLooping(true);
    menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Streetlights.ogg"));
    menuMusic.setLooping(true);

    // Load SFX
    footstep1 = Gdx.audio.newSound(Gdx.files.internal("Sounds/footstep1 grass.ogg"));
    footstep2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/footstep2 grass.ogg"));

    pauseSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Pause01.wav"));
    dialogueOpenSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/DialogueOpen.wav"));
    dialogueOptionSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/DialogueOption.wav"));
    buttonSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Button.wav"));
  }

  /**
   * Sets the volume of the music.
   *
   * @param volume Music volume value to be set, from 0 (silent) to 1 (loudest).
   */
  public void setMusicVolume(float volume) {
    this.musicVolume = volume;
    overworldMusic.setVolume(musicVolume);
    menuMusic.setVolume(musicVolume);
  }

  /**
   * Sets the sound effect volume.
   *
   * @param volume SFX volume value to be set, from 0 (silent) to 1 (loudest).
   */
  public void setSfxVolume(float volume) {
    this.sfxVolume = volume;
  }

  /**
   * A sound for when the pause menu appears.
   */
  public void playPauseSound() {
    pauseSound.play(sfxVolume);
  }

  /**
   * A sound for when the dialogue box appears.
   */
  public void playDialogueOpen() {
    dialogueOpenSound.play(sfxVolume);
  }

  /**
   * A sound for when the arrow in the selectBox is moved.
   */
  public void playDialogueOption() {
    dialogueOptionSound.play(sfxVolume);
  }

  /**
   * A sound for when a button is pressed.
   */
  public void playButton() {
    buttonSound.play(sfxVolume);
  }

  /**
   * Plays the music for the overworld (main game).
   */
  public void playOverworldMusic() {
    overworldMusic.play();
  }

  /**
   * Stops the music for the overworld.
   */
  public void stopOverworldMusic() {
    overworldMusic.stop();
  }

  /**
   * Plays the music for the menu.
   */
  public void playMenuMusic() {
    menuMusic.play();
  }

  /**
   * Stops the music for the menu.
   */
  public void stopMenuMusic() {
    menuMusic.stop();
  }

  /**
   * Pauses the overworld music, so it can be resumed from this point later.
   */
  public void pauseOverworldMusic() {
    overworldMusic.pause();
  }

  /**
   * Gets the current music volume.
   *
   * @return The current music volume
   */
  public float getMusicVolume() {
    return musicVolume;
  }

  /**
   * Gets the current SFX volume.
   *
   * @return The current sound effect volume
   */
  public float getSfxVolume() {
    return sfxVolume;
  }

  /**
   * Updates the timers for sounds that repeat regularly, needs to be called every render cycle.
   * Specifically handles triggering footsteps when the player is moving
   *
   * @param delta Time passed since the last render
   */
  public void processTimers(float delta) {
    // Decrements timers for any recurring sounds, like footsteps. Events that make these sounds
    // can then check that their specific timer is zero and play a noise.
    footstepTimer -= delta;
    if (footstepTimer < 0) {
      footstepTimer = 0;
    }
  }

  /**
   * Plays an alternating footstep sound when the player is moving and the footstepTimer variable
   * has hit zero. Uses two different SFX to sound more realistic and to allow the timing to be
   * configured.
   *
   * @param delay Determines the delay until the next footstep sound can play. Increasing this value
   *              would mean slower step sounds.
   */
  public void playFootstep(float delay) {
    // If it is time to play a footstep, play one
    if (footstepTimer <= 0) {
      footstepTimer = delay;
      if (!footstepBool) {
        footstep1.play(sfxVolume);
        footstepBool = true;
      } else {
        footstep2.play(sfxVolume);
        footstepBool = false;
      }
    }
  }

  /**
   * Disposes of all music and sound effects.
   */
  @Override
  public void dispose() {
    overworldMusic.dispose();
    menuMusic.dispose();
    footstep1.dispose();
    footstep2.dispose();
    pauseSound.dispose();
    dialogueOpenSound.dispose();
    dialogueOptionSound.dispose();
  }
}
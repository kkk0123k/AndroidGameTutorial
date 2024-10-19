package com.tutorial.androidgametutorial.Music;

import android.content.Context;
import android.media.MediaPlayer;

import com.tutorial.androidgametutorial.R;

public class SoundManager {

    private MediaPlayer BGPlayingMusicPlayer;
    private MediaPlayer BGMenuMusicPlayer; // Add for menu music
    private MediaPlayer moveMusicPlayer;
    private MediaPlayer attackMusicPlayer;
    private MediaPlayer getAttackedMusicPlayer;

    public enum PlayerAction {
        MOVE,
        ATTACK,
        GET_ATTACKED,
        NONE
    }

    private PlayerAction currentAction = PlayerAction.NONE;

    public SoundManager(Context context) {
        BGPlayingMusicPlayer = MediaPlayer.create(context, R.raw.menu_background_music);
        BGPlayingMusicPlayer.setLooping(true);

        BGMenuMusicPlayer = MediaPlayer.create(context, R.raw.menu_background_playing); // Initialize menu music
        BGMenuMusicPlayer.setLooping(true);

        moveMusicPlayer = MediaPlayer.create(context, R.raw.move_sound);
        attackMusicPlayer = MediaPlayer.create(context, R.raw.attack_sound);
        getAttackedMusicPlayer = MediaPlayer.create(context, R.raw.get_attacked_sound);
    }

    public void playBGPlayingMusic() {
        if (!BGPlayingMusicPlayer.isPlaying()) {
            BGPlayingMusicPlayer.start();
        }
        // Stop other music if playing
        stopBGMenuMusic(); // Stop menu music
        // Remove the call to stopActionMusic() here
        currentAction = PlayerAction.NONE;
    }

    // Add method to play menu music
    public void playBGMenuMusic() {
        if (!BGMenuMusicPlayer.isPlaying()) {
            BGMenuMusicPlayer.start();
        }
        // Stop other music if playing
        stopBGPlayingMusic(); // Stop background music
        stopActionMusic(); // Stop action music
    }

    // Add method to stop menu music
    public void stopBGMenuMusic() {
        if (BGMenuMusicPlayer.isPlaying()) {
            BGMenuMusicPlayer.pause();
        }
    }

    public void playActionMusic(PlayerAction action) {
        if (action == currentAction) {
            return; // If the action is the same, don't restart the music
        }

        currentAction = action;

        stopActionMusic(); // Stop any currently playing action music

        switch (action) {
            case MOVE:
                if (!moveMusicPlayer.isPlaying()) {
                    moveMusicPlayer.start();
                }
                break;
            case ATTACK:
                if (!attackMusicPlayer.isPlaying()) {
                    attackMusicPlayer.start();
                }
                break;
            case GET_ATTACKED:
                if (!getAttackedMusicPlayer.isPlaying()) {
                    getAttackedMusicPlayer.start();
                }
                break;
            case NONE:
                break;
        }

    }

    public void stopBGPlayingMusic() {
        if (BGPlayingMusicPlayer.isPlaying()) {
            BGPlayingMusicPlayer.pause();
        }
    }

    public void stopActionMusic() {
        if (moveMusicPlayer.isPlaying()) {
            moveMusicPlayer.pause();
        }
        if (attackMusicPlayer.isPlaying()) {
            attackMusicPlayer.pause();
        }
        if (getAttackedMusicPlayer.isPlaying()) {
            getAttackedMusicPlayer.pause();
        }
    }

    public boolean isAnyActionMusicPlaying() {
        return moveMusicPlayer.isPlaying() || attackMusicPlayer.isPlaying() || getAttackedMusicPlayer.isPlaying();
    }

    public void setBackgroundMusicVolume(float volume) {
        BGPlayingMusicPlayer.setVolume(volume, volume);
        BGMenuMusicPlayer.setVolume(volume, volume); // Apply to menu music as well
    }

    public void setActionMusicVolume(float volume) {
        moveMusicPlayer.setVolume(volume, volume);
        attackMusicPlayer.setVolume(volume, volume);
        getAttackedMusicPlayer.setVolume(volume, volume);
    }

    public boolean isPlaying(PlayerAction action) {
        switch (action) {
            case MOVE:
                return moveMusicPlayer.isPlaying();
            case ATTACK:
                return attackMusicPlayer.isPlaying();
            case GET_ATTACKED:
                return getAttackedMusicPlayer.isPlaying();
            default:
                return false;
        }
    }

    public void stopActionMusic(PlayerAction action) {
        switch (action) {
            case MOVE:
                if (moveMusicPlayer.isPlaying()) {
                    moveMusicPlayer.pause();
                    moveMusicPlayer.seekTo(0); // Reset to the beginning
                }
                break;
            case ATTACK:
                if (attackMusicPlayer.isPlaying()) {
                    attackMusicPlayer.pause();
                    attackMusicPlayer.seekTo(0); // Reset to the beginning
                }
                break;
            case GET_ATTACKED:
                if (getAttackedMusicPlayer.isPlaying()) {
                    getAttackedMusicPlayer.pause();
                    getAttackedMusicPlayer.seekTo(0); // Reset to the beginning
                }
                break;
        }
    }


    public void release() {
        BGPlayingMusicPlayer.release();
        BGMenuMusicPlayer.release(); // Release menu music player
        moveMusicPlayer.release();
        attackMusicPlayer.release();
        getAttackedMusicPlayer.release();

        BGPlayingMusicPlayer = null;
        BGMenuMusicPlayer = null; // Set to null
        moveMusicPlayer = null;
        attackMusicPlayer = null;
        getAttackedMusicPlayer = null;
    }
}
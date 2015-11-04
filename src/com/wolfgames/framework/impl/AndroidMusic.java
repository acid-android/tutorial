package com.wolfgames.framework.impl;

import java.io.IOException;

import com.wolfgames.framework.Music;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class AndroidMusic implements Music, MediaPlayer.OnCompletionListener {
    MediaPlayer mediaPlayer;
    boolean isPrepared = false; /* helps us determine the state of the MediaPlayer, whether or not it's ready to play music */

    public AndroidMusic(AssetFileDescriptor assetFileDescriptor) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
        }
        catch (Exception e) {
            throw new RuntimeException("Couldn't load music");
        }
    }

    /**
     *
     */
    public void dispose() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    /**
     *
     * @return
     */
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    /**
     *
     * @return
     */
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    /**
     *
     * @return
     */
    public boolean isStopped() {
        return !isPrepared;
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void play() {
        // if the music is already playing, we just return
        if (mediaPlayer.isPlaying()) {
            return;
        }

        try {
            // we start the MediaPlayer within a synchronized block, since we're using the isPrepared flag
            // and we might get and set this on separate threads
            synchronized (this) {
                // if the MediaPlayer is not prepared/ready, then we prepare it
                if (!isPrepared) {
                    mediaPlayer.prepare();
                }

                // start the music
                mediaPlayer.start();
            }
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }

    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }

    /**
     * Stops the MediaPlayer and sets the isPrepared flag to false, to indicate that the MediaPlayer
     * is not ready to play
     */
    public void stop() {
        mediaPlayer.stop();

        synchronized(this) {
            isPrepared = false;
        }
    }

    public void onCompletion(MediaPlayer player) {
        synchronized(this) {
            isPrepared = false;
        }
    }
}

package com.example.mytunes;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.MalformedURLException;

public class Player {
    private String path;
    private MediaPlayer mediaPlayer;

    public Player() {
        // Initialize mediaPlayer as null initially
        mediaPlayer = null;
    }

    public void setPath(String path) throws MalformedURLException {
        this.path = path;
        // Create MediaPlayer only once when path is set
        Media media = new Media(new File(path).toURI().toURL().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
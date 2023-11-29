package com.example.mytunes;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MytunesController {
    private String media_url;
    private String filename;
    private String mediaPath;


    private SongDao sdi = new SongDaoimpl();

    public void Rewind(MouseEvent mouseEvent) {

    }

    public void Filter(MouseEvent mouseEvent) {
    }

    public void AddSongToPlaylistButton(MouseEvent mouseEvent) {
    }

    public void PlaylistNewButton(MouseEvent mouseEvent) {
    }

    public void PlaylistEditButton(MouseEvent mouseEvent) {
    }

    public void PlaylistDeleteButton(MouseEvent mouseEvent) {
    }

    public void SongNewButton(MouseEvent mouseEvent) {
        FileChooser filechooser = new FileChooser();
        Stage stage = new Stage();
        filechooser.setTitle("Add new song");
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac", "*.mp4"),
                new FileChooser.ExtensionFilter("All Files", "."));
        File selectedFile = filechooser.showOpenDialog(stage);
        if (selectedFile != null) {
            media_url = selectedFile.toPath().toString();
            filename = selectedFile.getName();
             mediaPath = media_url.substring(media_url.indexOf("src"));
            System.out.println(mediaPath);
        }
    }

    public void SongEditButton(MouseEvent mouseEvent) {
    }

    public void SongDeleteButton(MouseEvent mouseEvent) {
    }

    public void VolumeSlider(MouseEvent mouseEvent) {
    }

    public void PlayPause(MouseEvent mouseEvent) {
    }

    public void Next(MouseEvent mouseEvent) {
    }

    public void SongOnPlaylistUpButton(MouseEvent mouseEvent) {
    }

    public void SongOnPlaylistDownButton(MouseEvent mouseEvent) {
    }

    public void SongOnPlaylistDeleteButton(MouseEvent mouseEvent) {
    }

    public void CloseButton(MouseEvent mouseEvent) {
        try{
            System.out.println("here");
            Mp3File mp3file = new Mp3File(mediaPath);
            System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedTagException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void PlayPauseButton(MouseEvent mouseEvent) {
    }
}
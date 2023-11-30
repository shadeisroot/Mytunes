package com.example.mytunes;

import com.mpatric.mp3agic.*;
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
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac", "*.m4a"),
                new FileChooser.ExtensionFilter("All Files", "*."));
        File selectedFile = filechooser.showOpenDialog(stage);
        if (selectedFile != null) {
            media_url = selectedFile.toPath().toString();
            String filename = selectedFile.getName();
             mediaPath = media_url.substring(media_url.indexOf("src"));
             try{
                 Mp3File mp3file = new Mp3File(mediaPath);
                 Song ssong = new Song(mp3file.getId3v1Tag().getTitle(), mp3file.getId3v1Tag().getArtist() , "mp3file.getId3v1Tag().getGenre()", mp3file.getLengthInSeconds());

             } catch (InvalidDataException e) {
                 throw new RuntimeException(e);
             } catch (UnsupportedTagException e) {
                 throw new RuntimeException(e);
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }

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
            System.out.println(mp3file.getId3v2Tag().getTitle());
            if (mp3file.hasId3v1Tag()) {
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                System.out.println("Track: " + id3v1Tag.getTrack());
                System.out.println("Artist: " + id3v1Tag.getArtist());
                System.out.println("Title: " + id3v1Tag.getTitle());
                System.out.println("Album: " + id3v1Tag.getAlbum());
                System.out.println("Year: " + id3v1Tag.getYear());
                System.out.println("Genre: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")");
                System.out.println("Comment: " + id3v1Tag.getComment());
            } else if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                System.out.println("Track: " + id3v2Tag.getTrack());
                System.out.println("Artist: " + id3v2Tag.getArtist());
                System.out.println("Title: " + id3v2Tag.getTitle());
                System.out.println("Album: " + id3v2Tag.getAlbum());
                System.out.println("Year: " + id3v2Tag.getYear());
                System.out.println("Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
                System.out.println("Comment: " + id3v2Tag.getComment());

            }
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
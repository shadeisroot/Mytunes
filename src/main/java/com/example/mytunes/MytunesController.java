package com.example.mytunes;

import com.mpatric.mp3agic.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MytunesController {
    private String media_url;
    private String mediaPath;
    private boolean id3v1 = false;
    private boolean id3v2 = false;

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
                 try{
                     if (mp3file.hasId3v1Tag()) {
                         id3v1 = true;
                     } else if (mp3file.hasId3v2Tag()) {
                         id3v2 = true;


                         Stage editStage = new Stage();
                     editStage.initModality(Modality.APPLICATION_MODAL);
                     editStage.setTitle("Add Song");

                     GridPane dialogLayout = new GridPane();
                     dialogLayout.setHgap(10);
                     dialogLayout.setVgap(10);
                     dialogLayout.setPadding(new Insets(10));

                     TextField artisttTextField = new TextField();
                     TextField titleTextField = new TextField();
                     TextField genreTextField = new TextField();

                     dialogLayout.add(new Label("Artist:"), 0, 0);
                     dialogLayout.add(artisttTextField, 1, 0);
                     if (id3v1){
                         artisttTextField.setText(mp3file.getId3v1Tag().getArtist());
                     } else if (id3v2) {
                         artisttTextField.setText(mp3file.getId3v2Tag().getArtist());
                     } else {
                         artisttTextField.setText("");
                     }

                     dialogLayout.add(new Label("Title:"), 0, 1);
                     dialogLayout.add(titleTextField, 1, 1);
                     if (id3v1){
                             titleTextField.setText(mp3file.getId3v1Tag().getTitle());
                     } else if (id3v2) {
                             titleTextField.setText(mp3file.getId3v2Tag().getTitle());
                     }else {
                         titleTextField.setText("");
                     }



                     dialogLayout.add(new Label("Genre:"), 0, 2);
                     dialogLayout.add(genreTextField, 1, 2);
                     if (id3v1){
                         genreTextField.setText(mp3file.getId3v1Tag().getGenreDescription());
                             id3v1 = false;
                     } else if (id3v2) {
                         genreTextField.setText(mp3file.getId3v2Tag().getGenreDescription());
                             id3v2 = false;
                     }else {
                         genreTextField.setText("");
                     }



                     Button submitButton = new Button("Submit");
                     submitButton.setOnAction(e -> {

                         Song ssong = new Song(titleTextField.getText(), artisttTextField.getText() , genreTextField.getText(), mp3file.getLengthInSeconds());

                         sdi.saveSong(ssong);

                         editStage.close();
                     });

                     dialogLayout.add(submitButton, 1, 3);

                     Scene editScene = new Scene(dialogLayout, 300, 200);
                     editStage.setScene(editScene);

                     editStage.showAndWait();
                 }
             } catch (Exception e) {
                     throw new RuntimeException(e);
                 }

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
    }

    public void PlayPauseButton(MouseEvent mouseEvent) {
    }
}
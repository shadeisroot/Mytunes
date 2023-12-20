package com.example.mytunes.Business;

import com.example.mytunes.Data.SongDao;
import com.example.mytunes.Data.SongDaoimpl;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class NewSongDialog {
    private SongDao sdi = new SongDaoimpl();
    private boolean id3v1 = false, id3v2 = false;
    private double leangth;

    public NewSongDialog(String mediaPath, ObservableList<Song> SongTabledata) throws InvalidDataException, UnsupportedTagException, IOException {


        Mp3File mp3file = new Mp3File(mediaPath);
        if (mp3file.hasId3v1Tag()) {
            id3v1 = true;
        } else if (mp3file.hasId3v2Tag()) {
            id3v2 = true;
        }
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
        TextField urlTextField = new TextField();
        TextField lengthTextField = new TextField();

        dialogLayout.add(new Label("Artist:"), 0, 0);
        dialogLayout.add(artisttTextField, 1, 0);
        if (id3v1) {
            artisttTextField.setText(mp3file.getId3v1Tag().getArtist());
        } else if (id3v2) {
            artisttTextField.setText(mp3file.getId3v2Tag().getArtist());
        } else {
            artisttTextField.setText("");
        }

        dialogLayout.add(new Label("Title:"), 0, 1);
        dialogLayout.add(titleTextField, 1, 1);
        if (id3v1) {
            titleTextField.setText(mp3file.getId3v1Tag().getTitle());
        } else if (id3v2) {
            titleTextField.setText(mp3file.getId3v2Tag().getTitle());
        } else {
            titleTextField.setText("");
        }


        dialogLayout.add(new Label("Genre:"), 0, 2);
        dialogLayout.add(genreTextField, 1, 2);
        if (id3v1) {
            genreTextField.setText(mp3file.getId3v1Tag().getGenreDescription());
            id3v1 = false;
        } else if (id3v2) {
            genreTextField.setText(mp3file.getId3v2Tag().getGenreDescription());
            id3v2 = false;
        } else {
            genreTextField.setText("");
        }

        dialogLayout.add(new Label("Url:"), 0, 3);
        dialogLayout.add(urlTextField, 1, 3);
        if (id3v1) {
            urlTextField.setText(mediaPath);
            id3v1 = false;
        } else if (id3v2) {
            urlTextField.setText(mediaPath);
            id3v2 = false;
        } else {
            urlTextField.setText(mediaPath);
        }

        dialogLayout.add(new Label("Length:"), 0, 4);
        dialogLayout.add(lengthTextField, 1, 4);
        if (id3v1) {
            double durationInMinutes = (double) mp3file.getLengthInSeconds() / 60;
            leangth = Math.round(durationInMinutes * 100.0) / 100.0;
            ;


            lengthTextField.setText(String.valueOf(leangth));
            id3v1 = false;
        } else if (id3v2) {
            double durationInMinutes = (double) mp3file.getLengthInSeconds() / 60;
            leangth = Math.round(durationInMinutes * 100.0) / 100.0;
            ;


            lengthTextField.setText(String.valueOf(leangth));
            id3v2 = false;
        } else {
            double durationInMinutes = (double) mp3file.getLengthInSeconds() / 60;
            leangth = Math.round(durationInMinutes * 100.0) / 100.0;
            ;


            lengthTextField.setText(String.valueOf(leangth));


        }


        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {

            Song ssong = new Song(0, titleTextField.getText(), artisttTextField.getText(), genreTextField.getText(), leangth, urlTextField.getText());

            sdi.saveSong(ssong);
            sdi.getAllSongs(SongTabledata);

            editStage.close();
        });

        dialogLayout.add(submitButton, 1, 5);

        Scene editScene = new Scene(dialogLayout, 300, 250);
        editStage.setScene(editScene);

        editStage.showAndWait();
    }
}

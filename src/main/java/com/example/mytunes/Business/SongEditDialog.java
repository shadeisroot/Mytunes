package com.example.mytunes.Business;

import com.example.mytunes.Data.SongDao;
import com.example.mytunes.Data.SongDaoimpl;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class SongEditDialog {
    private SongDao sdi = new SongDaoimpl();
    public SongEditDialog(TableView<Song> SongsTableview, ObservableList<Song> SongTabledata) {
        Song s = SongsTableview.getSelectionModel().getSelectedItem();
        System.out.println(s.getId());
        if (s != null) {
            Dialog<ButtonType> dialogvindue = new Dialog();
            dialogvindue.setTitle("Edit song");
            dialogvindue.setHeaderText("Edit name on song");
            dialogvindue.getDialogPane().getStyleClass().add("Dialog");
            dialogvindue.getDialogPane().getStylesheets().add(getClass().getResource("/MyTunesCSS.css").toExternalForm());
            ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialogvindue.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);
            TextField name = new TextField(s.getTitel());
            VBox box = new VBox(name);
            box.setPrefHeight(50);
            box.setPrefWidth(300);
            dialogvindue.getDialogPane().setContent(box);

            name.setText(s.getTitel());

            Optional<ButtonType> button = dialogvindue.showAndWait();
            if (button.get() == saveButton) {
                String enteredTitel = name.getText().trim();
                if (isTitelDuplicate(enteredTitel, SongTabledata)) {
                    s.setTitel(enteredTitel);
                    SongsTableview.refresh();
                    SongsTableview.sort();
                    sdi.editSong(s);

                } else {
                    new AlertDialog("You already have a song with the name: " + enteredTitel + "\nThe changes has failed.");
                }
            }
        }

    }
    private boolean isTitelDuplicate(String newTitel, ObservableList<Song> SongTabledata) {
        for (Song existingSong : SongTabledata) {
            if (existingSong.getTitel().equalsIgnoreCase(newTitel)) {
                return false;
            }
        }
        return true;
    }
}

package com.example.mytunes.Business;

import com.example.mytunes.Data.PlaylistDao;
import com.example.mytunes.Data.PlaylistDaoimpl;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class PlaylistEditDialog {
    private PlaylistDao pdi = new PlaylistDaoimpl();

    public PlaylistEditDialog(TableView<Playlist> PlaylistTableview, ObservableList<Playlist> tabeldata) {
        Playlist p = PlaylistTableview.getSelectionModel().getSelectedItem();
        if (p != null) {
            Dialog<ButtonType> dialogvindue = new Dialog();
            dialogvindue.setTitle("Edit playlist");
            dialogvindue.setHeaderText("Edit name on playlist");
            dialogvindue.getDialogPane().getStyleClass().add("Dialog");
            dialogvindue.getDialogPane().getStylesheets().add(getClass().getResource("/MyTunesCSS.css").toExternalForm());
            ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialogvindue.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);
            TextField name = new TextField(p.getName());
            VBox box = new VBox(name);
            box.setPrefHeight(50);
            box.setPrefWidth(300);
            dialogvindue.getDialogPane().setContent(box);

            name.setText(p.getName());

            Optional<ButtonType> button = dialogvindue.showAndWait();
            if (button.get() == saveButton) {
                String enteredName = name.getText().trim();
                if (isNameDuplicate(enteredName, tabeldata)) {
                    p.setName(enteredName);
                    PlaylistTableview.refresh();
                    PlaylistTableview.sort();
                    pdi.editPlaylist(p);
                } else {
                    new AlertDialog("You already have a playlist with the name: " + enteredName + "\nThe changes has failed.");
                }
            }
        }
    }

    private boolean isNameDuplicate(String newName, ObservableList<Playlist> tabeldata) {
        for (Playlist existingPlaylist : tabeldata) {
            if (existingPlaylist.getName().equalsIgnoreCase(newName)) {
                return false;
            }
        }
        return true;
    }
}

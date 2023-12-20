package com.example.mytunes.Business;

import com.example.mytunes.Data.PlaylistDao;
import com.example.mytunes.Data.PlaylistDaoimpl;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class NewPlaylistDialog {
    private PlaylistDao pdi = new PlaylistDaoimpl();
    public NewPlaylistDialog(String title, String header, TableView<Playlist> PlaylistTableview, ObservableList<Playlist> tabeldata) {
        Playlist p = new Playlist("", 0, 0.0,0);

        Dialog<ButtonType> dialogvindue = new Dialog();
        dialogvindue.setTitle(title);
        dialogvindue.setHeaderText(header);
        dialogvindue.getDialogPane().getStyleClass().add("Dialog");
        dialogvindue.getDialogPane().getStylesheets().add(getClass().getResource("/MyTunesCSS.css").toExternalForm());
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialogvindue.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
        TextField name = new TextField();
        name.setPromptText("Name");
        VBox box = new VBox(name);
        box.setPrefHeight(50);
        box.setPrefWidth(300);
        dialogvindue.getDialogPane().setContent(box);

        name.setText(p.getName());

        Optional<ButtonType> button = dialogvindue.showAndWait();
        if (button.get() == addButton) {
            String enteredName = name.getText().trim();
            if (isNameDuplicate(enteredName, tabeldata)) {
                p.setName(enteredName);
                PlaylistTableview.refresh();
                pdi.newPlaylist(p);
                pdi.getAllPlaylists(tabeldata);
            } else {
                new AlertDialog("You already have a playlist with the name: " + enteredName);
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

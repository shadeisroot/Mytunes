package com.example.mytunes.Business;

import javafx.scene.control.Alert;

public class AlertDialog {
    public AlertDialog(String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.getDialogPane().getStyleClass().add("Alert");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/MyTunesCSS.css").toExternalForm());
        alert.setHeaderText(null);
        alert.setContentText(context);
        alert.showAndWait();
    }
}

package com.example.mytunes.Business;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class AlertConfirmation {
    boolean yesno = false;
    public AlertConfirmation() {
    }



    public void alert(String textfield) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, textfield, ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().getStyleClass().add("Alert");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/MyTunesCSS.css").toExternalForm());
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES){
            yesno = true;
        }else yesno = false;
    }
    public boolean isYesno() {
        return yesno;
    }
}

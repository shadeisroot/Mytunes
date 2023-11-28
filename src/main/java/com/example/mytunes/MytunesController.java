package com.example.mytunes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MytunesController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
package com.example.mytunes.Business;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
public class Filechooser {
    private String mediaPath;


    public Filechooser(String title) {
        FileChooser filechooser = new FileChooser();
        Stage stage = new Stage();
        filechooser.setTitle(title);
        filechooser.getExtensionFilters().
                addAll(new FileChooser.ExtensionFilter("Audio Files", "*.wav","*.mp3","*.aac","*.m4a"),
                        new FileChooser.ExtensionFilter("All Files","*."));
        File selectedFile = filechooser.showOpenDialog(stage);
        if(selectedFile !=null)
        {
            String media_url = selectedFile.toPath().toString();
            String filename = selectedFile.getName();
            mediaPath = media_url.substring(media_url.indexOf("src"));
            if (mediaPath.contains("\\")) {
                mediaPath = mediaPath.replaceAll("\\\\", "/");
                System.out.println(mediaPath);
            }
        }
    }
    public String getMediaPath() {
        return mediaPath;
    }
}

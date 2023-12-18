package com.example.mytunes;

import com.mpatric.mp3agic.*;
import java.text.DecimalFormat;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.*;

public class MytunesController {
    public ListView<String> SongsOnPlaylistListView = new ListView<>();
    public Slider PlayerDuration;
    public Label DurationLabel;
    private String media_url;
    @FXML
    private Image mute, unmute, playit, pauseit, close, Search;
    private String mediaPath;
    private boolean id3v1 = false;
    private boolean id3v2 = false;
    private String path;
    private String sourcepath;
    private SongDao sdi = new SongDaoimpl();
    private PlaylistDao pdi = new PlaylistDaoimpl();
    private Player player = new Player();

    private ImageView imageView = new ImageView();
    private String pathstring;

    @FXML
    private Button AddSongToPlaylistButton;

    @FXML
    private Button CloseButton;

    @FXML
    private TableColumn<Song, String> ColumnArtist = new TableColumn();

    @FXML
    private TableColumn<Song, String> ColumnGenre = new TableColumn();

    @FXML
    private TableColumn<Playlist, String> ColumnLength = new TableColumn();

    @FXML
    private TableColumn<Song, String> ColumnLength2 = new TableColumn();

    @FXML
    private TableColumn<Playlist, String> ColumnName = new TableColumn();

    @FXML
    private TableColumn<Playlist, Integer> ColumnSongs = new TableColumn();

    @FXML
    private TableColumn<Song, String> ColumnTitel = new TableColumn();

    private ObservableList<String> playlistNames;

    private double leangth;


    @FXML
    private ImageView FilterButtonImage = new ImageView();

    @FXML
    private Button FilterButton;

    @FXML
    private Button PlayButton;

    @FXML
    private ImageView PlayPauseButton;
    @FXML
    private ImageView pauseplaybutton;

    @FXML
    private Button PlaylistDeleteButton;

    @FXML
    private Button PlaylistEditButton;

    @FXML
    private Button PlaylistNewButton;

    @FXML
    private TableView<Playlist> PlaylistTableview = new TableView<Playlist>();

    @FXML
    private Button SongDeleteButton;

    @FXML
    private Button SongEditButton;

    @FXML
    private Button SongNewButton;

    @FXML
    private Button SongOnPlaylistDeleteButton;

    @FXML
    private Button SongOnPlaylistDownButton;

    @FXML
    private Button SongOnPlaylistUpButton;

    @FXML
    private Label isplayingText;

    @FXML
    private TextField filterTextField;

    @FXML
    private TableView<Song> SongsTableview = new TableView<Song>();

    @FXML
    private ImageView VolumeButton;

    @FXML
    private Slider VolumeSliderButton;

    private final ObservableList<String> playlistsongtitelfromid = FXCollections.observableArrayList();

    private final ObservableList<Playlist> tabeldata = FXCollections.observableArrayList();

    private final ObservableList<Song> SongTabledata = FXCollections.observableArrayList();

    private final ObservableList<Double> playlistlengthfromid = FXCollections.observableArrayList();


    public MytunesController() throws MalformedURLException {
    }

    public void initialize() {
        PlaylistTableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        SongsTableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ColumnName.setCellValueFactory(new PropertyValueFactory<Playlist, String>("name"));
        ColumnSongs.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("songs"));

        ColumnLength.setCellValueFactory(new PropertyValueFactory<Playlist, String>("length"));

        ColumnTitel.setCellValueFactory(new PropertyValueFactory<Song, String>("Titel"));
        ColumnArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("Artist"));
        ColumnGenre.setCellValueFactory(new PropertyValueFactory<Song, String>("Genre"));
        ColumnLength2.setCellValueFactory(new PropertyValueFactory<Song, String>("Length"));


        PlaylistTableview.setItems(tabeldata);
        SongsTableview.setItems(SongTabledata);


        pdi.getAllPlaylists(tabeldata);
        sdi.getAllSongs(SongTabledata);


        ColumnTitel.setSortType(TableColumn.SortType.ASCENDING);
        SongsTableview.getSortOrder().add(ColumnTitel);
        SongsTableview.sort();

        VolumeSliderButton.setMin(0);
        VolumeSliderButton.setMax(1.0);
        VolumeSliderButton.setBlockIncrement(0.1);
        VolumeSliderButton.setValue(0.5);

        PlayerDuration = new Slider();
        PlayerDuration.setMin(0);
        PlayerDuration.setMax(100);

        SongsTableview.getSelectionModel().select(0);
        PlaylistTableview.getSelectionModel().select(0);


        try {
            isplayingText.setText(SongsTableview.getSelectionModel().getSelectedItem().getTitel() + " " + "Is Playing");

        } catch (NullPointerException e) {
        }

        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterSongs(newValue);
        });

        try {
            playlistNames = sdi.getAllPlaylistSong(PlaylistTableview.getSelectionModel().getSelectedItem().getId());
            SongsOnPlaylistListView.setItems(playlistNames);

        } catch (NullPointerException e) {

        }

        //pdi.updatesongCount(sdi.getAllPlaylistSong(PlaylistTableview.getSelectionModel().getSelectedItem().getId()).size(), PlaylistTableview.getSelectionModel().getSelectedItem().getId());
    }


    public void updateplaylistsongs(ObservableList<String> list) {
        try {
            int playlistId = PlaylistTableview.getSelectionModel().getSelectedItem().getId();
            for (int i = 0; i < list.size(); i++) {
                int songId = sdi.getidfromtitle(list.get(i));
                pdi.updatePosition(playlistId, i + 1, songId); // Adjust the position to start from 1
            }
            System.out.println("Positions updated successfully.");
        } catch (Exception e) {
            System.err.println("Error updating positions: " + e.getMessage());
        }
    }

    private void updateLabel(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) (duration.toSeconds() - (minutes * 60));
        String time = String.format("Time: %02d:%02d", minutes, seconds);
        DurationLabel.setText(time);
    }

    @FXML
    void AddSongToPlaylistButton(MouseEvent event) {
        Playlist plst = PlaylistTableview.getSelectionModel().getSelectedItem();
        Song sngs = SongsTableview.getSelectionModel().getSelectedItem();

        String chosenTitel = sngs.getTitel().trim();
        if (isSongDuplicate(chosenTitel)) {

            try {
                pdi.addtoplaylistsong(plst, sngs);
            } catch (NullPointerException e) {

            }
            playlistNames = sdi.getAllPlaylistSong(PlaylistTableview.getSelectionModel().getSelectedItem().getId());
            pdi.updatesongCount(pdi.countSongs(PlaylistTableview.getSelectionModel().getSelectedItem().getId()), PlaylistTableview.getSelectionModel().getSelectedItem().getId());
            int privselect = PlaylistTableview.getSelectionModel().getSelectedIndex();
            playlistlengthfromid.setAll(pdi.getLength(PlaylistTableview.getSelectionModel().getSelectedItem().getId()));

            double sum = 0;
            for (double value : playlistlengthfromid) {
                sum += value;
            }
            pdi.updatelengthplaylist(sum, PlaylistTableview.getSelectionModel().getSelectedItem().getId());

            pdi.getAllPlaylists(tabeldata);
            PlaylistTableview.getSelectionModel().select(privselect);
            SongsOnPlaylistListView.setItems(playlistNames);
        } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.getDialogPane().getStyleClass().add("Alert");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/MyTunesCSS.css").toExternalForm());
        alert.setHeaderText(null);
        alert.setContentText("You already have "  + chosenTitel + " on this playlist.");
        alert.showAndWait();
        }
    }

    private boolean isSongDuplicate(String newName) {
        for (String existingSongName : SongsOnPlaylistListView.getItems()) {
            if (existingSongName.equalsIgnoreCase(newName)) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void CloseButton(MouseEvent event) {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void Filter(MouseEvent event) {

    }
    public String isselected (){
        String selected = " ";
        if (SongsTableview.getSelectionModel().getSelectedItem() != null){
            selected = "Songs";
        } else if (SongsOnPlaylistListView.getSelectionModel().getSelectedItem() != null) {
            selected = "Playlistsongs";
        }
        return selected;
    }

    @FXML
    void PlayPauseButton(MouseEvent event) throws MalformedURLException {
        try {
            if (SongsTableview.getSelectionModel().getSelectedItem() != null){
                path = SongsTableview.getSelectionModel().getSelectedItem().getURL();
            } else if (SongsOnPlaylistListView.getSelectionModel().getSelectedItem() != null) {
                path = (sdi.geturlfromtitle(SongsOnPlaylistListView.getSelectionModel().getSelectedItem()));
            }
            pathstring = path.replaceAll("\\s+" , "%20");
            sourcepath = player.getMediaPlayer().getMedia().getSource();
            sourcepath = sourcepath.substring(sourcepath.indexOf("src"));
        } catch (NullPointerException e){

        }
        try{
            if(player.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING) && Objects.equals(sourcepath, pathstring)){
                player.getMediaPlayer().pause();
                pauseplaybutton.setImage(playit);
            }else {
                if(!Objects.equals(sourcepath, pathstring)){
                    player.getMediaPlayer().stop();
                    player.setPath(path);
                    player.getMediaPlayer().volumeProperty().setValue(VolumeSliderButton.getValue());
                    player.getMediaPlayer().play();
                    if (SongsTableview.getSelectionModel().getSelectedItem() != null){
                        isplayingText.setText(SongsTableview.getSelectionModel().getSelectedItem().getTitel() + " " + "Is Playing");
                    } else if (SongsOnPlaylistListView.getSelectionModel().getSelectedItem() != null) {
                        isplayingText.setText(SongsOnPlaylistListView.getSelectionModel().getSelectedItem() + " " + "Is Playing");

                    }
                    pauseplaybutton.setImage(pauseit);
                }else{
                    player.getMediaPlayer().volumeProperty().setValue(VolumeSliderButton.getValue());
                    player.getMediaPlayer().play();
                    pauseplaybutton.setImage(pauseit);
                }
            }
        }catch (NullPointerException e){
            try{
                player.setPath(path);
                player.getMediaPlayer().volumeProperty().setValue(VolumeSliderButton.getValue());
                player.getMediaPlayer().play();
                if (SongsTableview.getSelectionModel().getSelectedItem() != null){
                    isplayingText.setText(SongsTableview.getSelectionModel().getSelectedItem().getTitel() + " " + "Is Playing");
                } else if (SongsOnPlaylistListView.getSelectionModel().getSelectedItem() != null) {
                    isplayingText.setText(SongsOnPlaylistListView.getSelectionModel().getSelectedItem() + " " + "Is Playing");

                }
                pauseplaybutton.setImage(pauseit);
            }catch (NullPointerException ee){
            }
        }

    }

    @FXML
    void PlaylistDeleteButton(MouseEvent event) {
            Playlist p = PlaylistTableview.getSelectionModel().getSelectedItem();
            String pn = PlaylistTableview.getSelectionModel().getSelectedItem().getName();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + pn + " ?", ButtonType.YES, ButtonType.NO);
            alert.getDialogPane().getStyleClass().add("Alert");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/MyTunesCSS.css").toExternalForm());
            alert.showAndWait();

        if (p != null){
            if (alert.getResult() == ButtonType.YES) {
                if (pdi.deletePlaylist(p)){
                    tabeldata.remove(p);
                    PlaylistTableview.refresh();
                    playlistNames = sdi.getAllPlaylistSong(PlaylistTableview.getSelectionModel().getSelectedItem().getId());
                    SongsOnPlaylistListView.setItems(playlistNames);

                }
            }
        }
    }

    @FXML
    void PlaylistEditButton(MouseEvent event) {
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
                if (isNameDuplicate(enteredName)) {
                    p.setName(enteredName);
                    PlaylistTableview.refresh();
                    PlaylistTableview.sort();
                    pdi.editPlaylist(p);
                } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.getDialogPane().getStyleClass().add("Alert");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/MyTunesCSS.css").toExternalForm());
                alert.setHeaderText(null);
                alert.setContentText("You already have a playlist with the name: " + enteredName + "\nThe changes has failed.");
                alert.showAndWait();
            }
            }
        }

    }

    @FXML
    void PlaylistNewButton (MouseEvent event){
        Playlist p = new Playlist("", 0, 0.0,0);

        Dialog<ButtonType> dialogvindue = new Dialog();
        dialogvindue.setTitle("New playlist");
        dialogvindue.setHeaderText("Add new playlist");
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
            if (isNameDuplicate(enteredName)) {
                p.setName(enteredName);
                PlaylistTableview.refresh();
                pdi.newPlaylist(p);
                pdi.getAllPlaylists(tabeldata);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.getDialogPane().getStyleClass().add("Alert");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/MyTunesCSS.css").toExternalForm());
                alert.setHeaderText(null);
                alert.setContentText("You already have a playlist with the name: " + enteredName);
                alert.showAndWait();
            }
        }
    }

    private boolean isNameDuplicate(String newName) {
        for (Playlist existingPlaylist : tabeldata) {
            if (existingPlaylist.getName().equalsIgnoreCase(newName)) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void Rewind (MouseEvent event) throws MalformedURLException {
        if (SongsTableview.getSelectionModel().getSelectedItem() != null){
            SongsTableview.getSelectionModel().selectPrevious();
        } else if (SongsOnPlaylistListView.getSelectionModel().getSelectedItem() != null) {
            SongsOnPlaylistListView.getSelectionModel().selectPrevious();

        }
        if (SongsTableview.getSelectionModel().getSelectedItem() != null){
            path = SongsTableview.getSelectionModel().getSelectedItem().getURL();
        } else if (SongsOnPlaylistListView.getSelectionModel().getSelectedItem() != null) {
            path = (sdi.geturlfromtitle(SongsOnPlaylistListView.getSelectionModel().getSelectedItem()));
        }
        player.getMediaPlayer().stop();
        player.setPath(path);
        player.getMediaPlayer().play();
        if (SongsTableview.getSelectionModel().getSelectedItem() != null){
            isplayingText.setText(SongsTableview.getSelectionModel().getSelectedItem().getTitel() + " " + "Is Playing");
        } else if (SongsOnPlaylistListView.getSelectionModel().getSelectedItem() != null) {
            isplayingText.setText(SongsOnPlaylistListView.getSelectionModel().getSelectedItem() + " " + "Is Playing");

        }

    }

    @FXML
    void SongDeleteButton (MouseEvent event){
        Song song = SongsTableview.getSelectionModel().getSelectedItem();
        String sn = SongsTableview.getSelectionModel().getSelectedItem().getTitel();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + sn + " ?", ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().getStyleClass().add("Alert");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/MyTunesCSS.css").toExternalForm());
        alert.showAndWait();

        if (song != null){
            if (alert.getResult() == ButtonType.YES) {
                if (sdi.deleteSong(song)){
                    SongTabledata.remove(song);
                    sdi.deleteplaylistsong(song.getId());
                    playlistNames = sdi.getAllPlaylistSong(PlaylistTableview.getSelectionModel().getSelectedItem().getId());
                    pdi.updatesongCount(pdi.countSongs(PlaylistTableview.getSelectionModel().getSelectedItem().getId()),PlaylistTableview.getSelectionModel().getSelectedItem().getId());
                    int privselect = PlaylistTableview.getSelectionModel().getSelectedIndex();
                    playlistlengthfromid.setAll(pdi.getLength(PlaylistTableview.getSelectionModel().getSelectedItem().getId()));

                    double sum = 0;
                    for (double value : playlistlengthfromid) {
                        sum += value;
                    }
                    pdi.updatelengthplaylist(sum, PlaylistTableview.getSelectionModel().getSelectedItem().getId());

                    pdi.getAllPlaylists(tabeldata);
                    PlaylistTableview.getSelectionModel().select(privselect);
                    SongsOnPlaylistListView.setItems(playlistNames);

                }
            }
        }
    }

    @FXML
    void SongEditButton (MouseEvent event){
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
                if (isTitelDuplicate(enteredTitel)) {
                    s.setTitel(enteredTitel);
                    SongsTableview.refresh();
                    SongsTableview.sort();
                    sdi.editSong(s);
                    playlistNames = sdi.getAllPlaylistSong(PlaylistTableview.getSelectionModel().getSelectedItem().getId());
                    SongsOnPlaylistListView.setItems(playlistNames);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.getDialogPane().getStyleClass().add("Alert");
                    alert.getDialogPane().getStylesheets().add(getClass().getResource("/MyTunesCSS.css").toExternalForm());
                    alert.setHeaderText(null);
                    alert.setContentText("You already have a song with the name: " + enteredTitel + "\nThe changes has failed.");
                    alert.showAndWait();
                }
            }
        }
    }

    private boolean isTitelDuplicate(String newTitel) {
        for (Song existingSong : SongTabledata) {
            if (existingSong.getTitel().equalsIgnoreCase(newTitel)) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void SongNewButton (MouseEvent event){
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
            if (mediaPath.contains("\\")) {
                mediaPath = mediaPath.replaceAll("\\\\", "/");
                System.out.println(mediaPath);
            }
            try {
                Mp3File mp3file = new Mp3File(mediaPath);
                try {
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
                            leangth = Math.round(durationInMinutes * 100.0) / 100.0;;


                            lengthTextField.setText(String.valueOf(leangth));
                            id3v1 = false;
                        } else if (id3v2) {
                            double durationInMinutes = (double) mp3file.getLengthInSeconds() / 60;
                            leangth = Math.round(durationInMinutes * 100.0) / 100.0;;


                            lengthTextField.setText(String.valueOf(leangth));
                            id3v2 = false;
                        } else {
                            double durationInMinutes = (double) mp3file.getLengthInSeconds() / 60;
                            leangth = Math.round(durationInMinutes * 100.0) / 100.0;;


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
        }
    }

    @FXML
    void SongOnPlaylistDeleteButton (MouseEvent event){
        String pn = SongsOnPlaylistListView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + pn + " ?", ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().getStyleClass().add("Alert");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/MyTunesCSS.css").toExternalForm());

        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            sdi.deleteplaylistsong(sdi.getidfromtitle(SongsOnPlaylistListView.getSelectionModel().getSelectedItem()));
            pdi.updatesongCount(pdi.countSongs(PlaylistTableview.getSelectionModel().getSelectedItem().getId()),PlaylistTableview.getSelectionModel().getSelectedItem().getId());


        }
        int privselect = PlaylistTableview.getSelectionModel().getSelectedIndex();
        pdi.getAllPlaylists(tabeldata);
        PlaylistTableview.getSelectionModel().select(privselect);
        playlistNames = sdi.getAllPlaylistSong(PlaylistTableview.getSelectionModel().getSelectedItem().getId());
        SongsOnPlaylistListView.setItems(playlistNames);
    }

    private static void moveElementUp(ObservableList<String> list, String element) {
        int index = list.indexOf(element);
        if (index > 0 && index < list.size()) {
            Collections.swap(list, index, index - 1);
        }
    }

    private static void moveElementDown(ObservableList<String> list, String element) {
        int index = list.indexOf(element);
        if (index >= 0 && index < list.size() - 1) {
            Collections.swap(list, index, index + 1);
        }
    }
    @FXML
    void SongOnPlaylistDownButton (MouseEvent event){
        moveElementDown(playlistNames, SongsOnPlaylistListView.getSelectionModel().getSelectedItem());
        SongsOnPlaylistListView.getSelectionModel().selectNext();
        SongsOnPlaylistListView.setItems(playlistNames);
        updateplaylistsongs(playlistNames);
    }

    @FXML
    void SongOnPlaylistUpButton (MouseEvent event){
        moveElementUp(playlistNames, SongsOnPlaylistListView.getSelectionModel().getSelectedItem());
        SongsOnPlaylistListView.getSelectionModel().selectPrevious();
        SongsOnPlaylistListView.setItems(playlistNames);
        updateplaylistsongs(playlistNames);
    }

    @FXML
    void FilterButton (MouseEvent event) {
        filterTextField.clear();
    }

    @FXML
    void VolumeSlider (MouseEvent event){
        if (VolumeSliderButton.isValueChanging()){
            try{
                player.getMediaPlayer().volumeProperty().setValue(VolumeSliderButton.getValue());
            }catch (NullPointerException e){

            }
        }
    }

    public void NextButtonclicked(MouseEvent event) throws MalformedURLException {

        if (SongsTableview.getSelectionModel().getSelectedItem() != null){
            SongsTableview.getSelectionModel().selectNext();
        } else if (SongsOnPlaylistListView.getSelectionModel().getSelectedItem() != null) {
            SongsOnPlaylistListView.getSelectionModel().selectNext();

        }
        if (SongsTableview.getSelectionModel().getSelectedItem() != null){
            path = SongsTableview.getSelectionModel().getSelectedItem().getURL();
        } else if (SongsOnPlaylistListView.getSelectionModel().getSelectedItem() != null) {
            path = (sdi.geturlfromtitle(SongsOnPlaylistListView.getSelectionModel().getSelectedItem()));
        }
        player.getMediaPlayer().stop();
        player.setPath(path);
        player.getMediaPlayer().play();

        if (SongsTableview.getSelectionModel().getSelectedItem() != null){
            isplayingText.setText(SongsTableview.getSelectionModel().getSelectedItem().getTitel() + " " + "Is Playing");
        } else if (SongsOnPlaylistListView.getSelectionModel().getSelectedItem() != null) {
            isplayingText.setText(SongsOnPlaylistListView.getSelectionModel().getSelectedItem() + " " + "Is Playing");

        }

    }

    public void Volumebuttonclicked(MouseEvent event) throws URISyntaxException {
        try{
            if (player.getMediaPlayer().isMute()){
                VolumeButton.setImage(mute);
                VolumeSliderButton.setValue(player.getMediaPlayer().getVolume());
                player.getMediaPlayer().setMute(false);
            }

            else{
                VolumeButton.setImage(unmute);
                VolumeSliderButton.setValue(0);
                player.getMediaPlayer().setMute(true);
            }
        }catch (NullPointerException e){

        }
    }

    void filterSongs(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            SongsTableview.setItems(SongTabledata);
            FilterButtonImage.setImage(Search);
        } else {
            ObservableList<Song> filteredSongs = FXCollections.observableArrayList();

            for (Song song : SongTabledata) {
                if (song.getTitel().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredSongs.add(song);
                    FilterButtonImage.setImage(close);
                }
            }
            SongsTableview.setItems(filteredSongs);
        }

    }

    public void playlistonmouseclicked(MouseEvent event) {
        playlistNames = sdi.getAllPlaylistSong(PlaylistTableview.getSelectionModel().getSelectedItem().getId());
        SongsOnPlaylistListView.setItems(playlistNames);

    }

    public void playlistsongclicked(MouseEvent event) throws MalformedURLException {
        SongsTableview.getSelectionModel().clearSelection();
        path = (sdi.geturlfromtitle(SongsOnPlaylistListView.getSelectionModel().getSelectedItem()));

        try {

            if (player.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING) && !Objects.equals(sourcepath, pathstring)) {
                pauseplaybutton.setImage(playit);
            }else if (player.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING) && Objects.equals(sourcepath, pathstring)) {
                pauseplaybutton.setImage(pauseit);
            }

            }catch(NullPointerException e){

            }
        }

        public void Songstableviewclicked (MouseEvent event){
            SongsOnPlaylistListView.getSelectionModel().clearSelection();
            path = SongsTableview.getSelectionModel().getSelectedItem().getURL();

            try {
                if (player.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING) && !Objects.equals(sourcepath, pathstring)) {
                    pauseplaybutton.setImage(playit);
                } else if (player.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING) && Objects.equals(sourcepath, pathstring)) {
                    pauseplaybutton.setImage(pauseit);
                }
            }catch(NullPointerException e){

                }



            }

        }
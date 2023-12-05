package com.example.mytunes;

import com.mpatric.mp3agic.*;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;

public class MytunesController {
    private String media_url;
    private String mediaPath;
    private boolean id3v1 = false;
    private boolean id3v2 = false;
    private String path;
    private String sourcepath;
    private String srcpath;
    private SongDao sdi = new SongDaoimpl();
    private Player player = new Player();

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
    private TableColumn<Playlist, String> ColumnSongs = new TableColumn();

    @FXML
    private TableColumn<Song, String> ColumnTitel = new TableColumn();

    @FXML
    private ImageView FilterButton;

    @FXML
    private ImageView NextButton;

    @FXML
    private Button PlayButton;

    @FXML
    private ImageView PlayPauseButton;

    @FXML
    private Button PlaylistDeleteButton;

    @FXML
    private Button PlaylistEditButton;

    @FXML
    private Button PlaylistNewButton;

    @FXML
    private TableView<Playlist> PlaylistTableview = new TableView<Playlist>();

    @FXML
    private ImageView RewindButton;

    @FXML
    private Button SongDeleteButton;

    @FXML
    private Button SongEditButton;

    @FXML
    private Button SongNewButton;

    @FXML
    private Button SongOnPlaylistDeleteButton;

    @FXML
    private ImageView SongOnPlaylistDownButton;

    @FXML
    private ImageView SongOnPlaylistUpButton;

    @FXML
    private ListView<?> SongsOnPlaylistTableview;

    @FXML
    private TableView<Song> SongsTableview = new TableView<Song>();

    @FXML
    private ImageView VolumeButton;

    @FXML
    private Slider VolumeSliderButton;

    private final ObservableList<Playlist> tabeldata = FXCollections.observableArrayList();

    private final ObservableList<Song> SongTabledata = FXCollections.observableArrayList();

    public MytunesController() throws MalformedURLException {
    }

    public void initialize() {
        // Start database og sæt gui op med alle bøger i en tabel
        PlaylistTableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        SongsTableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Kolonnerne sættes op med forbindelse til klassen Person med hver sit felt
        ColumnName.setCellValueFactory(new PropertyValueFactory<Playlist, String>("name"));
        ColumnSongs.setCellValueFactory(new PropertyValueFactory<Playlist, String>("songs"));
        ColumnLength.setCellValueFactory(new PropertyValueFactory<Playlist, String>("length"));

        ColumnTitel.setCellValueFactory(new PropertyValueFactory<Song, String>("Titel"));
        ColumnArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("Artist"));
        ColumnGenre.setCellValueFactory(new PropertyValueFactory<Song, String>("Genre"));
        ColumnLength2.setCellValueFactory(new PropertyValueFactory<Song, String>("Length"));

        PlaylistTableview.setItems(tabeldata);
        SongsTableview.setItems(SongTabledata);

        // Læs alle bøger ind i tabellen
        sdi.getAllPlaylists(tabeldata);
        sdi.getAllSongs(SongTabledata);

        // Sortér som udgangspunkt efter id
        ColumnName.setSortType(TableColumn.SortType.ASCENDING);
        PlaylistTableview.getSortOrder().add(ColumnName);
        PlaylistTableview.sort();

        ColumnTitel.setSortType(TableColumn.SortType.ASCENDING);
        SongsTableview.getSortOrder().add(ColumnTitel);
        SongsTableview.sort();
    }
    @FXML
    void AddSongToPlaylistButton(MouseEvent event) {
    }

    @FXML
    void CloseButton(MouseEvent event) {

    }

    @FXML
    void Filter(MouseEvent event) {

    }

    @FXML
    void Next(MouseEvent event) {

    }

    @FXML
    void PlayPauseButton(MouseEvent event) throws MalformedURLException {
        path = SongsTableview.getSelectionModel().getSelectedItem().getURL();
        String pathstring = path.replaceAll("\\s+" , "%20");
        try {
            sourcepath = player.getMediaPlayer().getMedia().getSource();
            sourcepath = sourcepath.substring(sourcepath.indexOf("src"));
        } catch (NullPointerException e){

        }
        try{
            if(player.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING) && Objects.equals(sourcepath, pathstring)){
                player.getMediaPlayer().pause();
            }else {
                if(!Objects.equals(sourcepath, pathstring)){
                    player.getMediaPlayer().stop();
                    player.setPath(path);
                    player.getMediaPlayer().play();
                }else{
                    player.getMediaPlayer().play();
                }
            }
        }catch (NullPointerException e){
            player.setPath(path);
            player.getMediaPlayer().play();
        }

    }

    @FXML
    void PlaylistDeleteButton(MouseEvent event) {
            Playlist p = PlaylistTableview.getSelectionModel().getSelectedItem();
            if (p != null)
                if (sdi.deletePlaylist(p))
                    tabeldata.remove(p);
    }

    @FXML
    void PlaylistEditButton(MouseEvent event) {
    }

    @FXML
    void PlaylistNewButton (MouseEvent event){

    }

    @FXML
    void Rewind (MouseEvent event){


    }

    @FXML
    void SongDeleteButton (MouseEvent event){

    }

    @FXML
    void SongEditButton (MouseEvent event){

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


                        Button submitButton = new Button("Submit");
                        submitButton.setOnAction(e -> {

                            Song ssong = new Song(titleTextField.getText(), artisttTextField.getText(), genreTextField.getText(), mp3file.getLengthInSeconds(), urlTextField.getText());

                            sdi.saveSong(ssong);
                            sdi.getAllSongs(SongTabledata);

                            editStage.close();
                        });

                        dialogLayout.add(submitButton, 1, 4);

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
        }
    }

    @FXML
    void SongOnPlaylistDeleteButton (MouseEvent event){

    }


    @FXML
    void SongOnPlaylistDownButton (MouseEvent event){

    }

    @FXML
    void SongOnPlaylistUpButton (MouseEvent event){

    }

    @FXML
    void VolumeSlider (MouseEvent event){

    }

}
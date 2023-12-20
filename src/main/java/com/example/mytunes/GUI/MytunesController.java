package com.example.mytunes.GUI;

import com.example.mytunes.Business.*;
import com.example.mytunes.Data.PlaylistDao;
import com.example.mytunes.Data.PlaylistDaoimpl;
import com.example.mytunes.Data.SongDao;
import com.example.mytunes.Data.SongDaoimpl;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Objects;

public class MytunesController {

    private String sourcepath, mediaPath, path, pathstring;
    private SongDao sdi = new SongDaoimpl();
    private PlaylistDao pdi = new PlaylistDaoimpl();
    private Player player = new Player();
    private AlertConfirmation alertConfirmation = new AlertConfirmation();
    private ObservableList<String> playlistNames;
    private final ObservableList<Playlist> tabeldata = FXCollections.observableArrayList();

    private final ObservableList<Song> SongTabledata = FXCollections.observableArrayList();

    private final ObservableList<Double> playlistlengthfromid = FXCollections.observableArrayList();


    @FXML
    private Image mute, unmute, playit, pauseit, close, Search;

    @FXML
    private Button CloseButton;

    @FXML
    private ListView<String> SongsOnPlaylistListView = new ListView<>();

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

    @FXML
    private ImageView FilterButtonImage , pauseplaybutton, VolumeButton = new ImageView();

    @FXML
    private TableView<Playlist> PlaylistTableview = new TableView<Playlist>();

    @FXML
    private Label isplayingText;

    @FXML
    private TextField filterTextField;

    @FXML
    private TableView<Song> SongsTableview = new TableView<Song>();

    @FXML
    private Slider VolumeSliderButton;

    public MytunesController() throws MalformedURLException {
    }

    // Variables declaration and initialization, instance creation for various classes, and ObservableList objects
    public void initialize() {
        initializeSongsTableView();
        initializeplaylistTableView();
        initializeVolumeSlider();

        try {
            isplayingText.setText(SongsTableview.getSelectionModel().getSelectedItem().getTitel() + " " + "Is Playing");
            refreshPlaylist();
        } catch (NullPointerException e) {
            System.out.println("its null");
        }

        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterSongs(newValue);
        });

    }

    // A method to refresh the display of songs on a playlist.
    public void refreshPlaylist(){
        playlistNames = sdi.getAllPlaylistSong(PlaylistTableview.getSelectionModel().getSelectedItem().getId());
        SongsOnPlaylistListView.setItems(playlistNames);
    }
    // A method to initialize and configure the VolumeSlider.
    public void initializeVolumeSlider(){
        VolumeSliderButton.setMin(0);
        VolumeSliderButton.setMax(1.0);
        VolumeSliderButton.setBlockIncrement(0.1);
        VolumeSliderButton.setValue(0.5);
    }
    // Method sets up TableView element for displaying Playlists.
    // It uses PropertyValueFactory to associate columns in TableView with properties of Playlist objects.
    // It also uses a PlaylistDao to fetch data for display in TableView.
    public void initializeplaylistTableView(){
        PlaylistTableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ColumnName.setCellValueFactory(new PropertyValueFactory<Playlist, String>("name"));
        ColumnSongs.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("songs"));
        ColumnLength.setCellValueFactory(new PropertyValueFactory<Playlist, String>("length"));
        PlaylistTableview.setItems(tabeldata);
        pdi.getAllPlaylists(tabeldata);
        PlaylistTableview.getSelectionModel().select(0);
    }

    // Method sets up TableView element for displaying Songs.
    // It uses PropertyValueFactory to associate columns in TableView with properties of Song objects.
    // It also uses a SongDao to fetch data for display in TableView.

    public void initializeSongsTableView(){
        SongsTableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ColumnTitel.setCellValueFactory(new PropertyValueFactory<Song, String>("Titel"));
        ColumnArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("Artist"));
        ColumnGenre.setCellValueFactory(new PropertyValueFactory<Song, String>("Genre"));
        ColumnLength2.setCellValueFactory(new PropertyValueFactory<Song, String>("Length"));
        SongsTableview.setItems(SongTabledata);
        ColumnTitel.setSortType(TableColumn.SortType.ASCENDING);
        SongsTableview.getSortOrder().add(ColumnTitel);
        SongsTableview.sort();
        sdi.getAllSongs(SongTabledata);
        SongsTableview.getSelectionModel().select(0);

    }
    // A method to update the order of songs in a playlist.
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
    // A method to add a song to a playlist.
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
            new AlertDialog("You already have "  + chosenTitel + " on this playlist.");
        }
    }
    // A private helper function to check if a song is already added to a playlist.
    private boolean isSongDuplicate(String songTitle) {
        for (String existingSongName : SongsOnPlaylistListView.getItems()) {
            if (existingSongName.equalsIgnoreCase(songTitle)) {
                return false;
            }
        }
        return true;
    }
    // A method to close the application.
    @FXML
    void CloseButton(MouseEvent event) {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }

    // Method filters songs based on a search text.
    @FXML
    void Filter(MouseEvent event) {

    }
    // A method to handle play/pause status for a song.
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

    //Method for deleting a playlist
    @FXML
    void PlaylistDeleteButton(MouseEvent event) {
        Playlist p = PlaylistTableview.getSelectionModel().getSelectedItem();
        String pn = PlaylistTableview.getSelectionModel().getSelectedItem().getName();
        alertConfirmation.alert("Are you sure you want to delete " + pn + " ?");

        if (p != null){
            if (alertConfirmation.isYesno()) {
                if (pdi.deletePlaylist(p)){
                    tabeldata.remove(p);
                    PlaylistTableview.refresh();
                    refreshPlaylist();

                }
            }
        }
    }


    //Method for editing Playlist
    @FXML
    void PlaylistEditButton(MouseEvent event) {
        PlaylistEditDialog playlistEditDialog = new PlaylistEditDialog(PlaylistTableview, tabeldata);


    }
    //Method for adding a new playlist
    @FXML
    void PlaylistNewButton (MouseEvent event){
        NewPlaylistDialog newPlaylistDialog = new NewPlaylistDialog("add new Playlist","Add Playlist ", PlaylistTableview, tabeldata);
    }


    //Method for playing the previous song
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
    //Method for deleting a song from the songstableview
    @FXML
    void SongDeleteButton (MouseEvent event){
        Song song = SongsTableview.getSelectionModel().getSelectedItem();
        String sn = SongsTableview.getSelectionModel().getSelectedItem().getTitel();
        alertConfirmation.alert("Are you sure you want to delete " + sn + " ?");

        if (song != null){
            if (alertConfirmation.isYesno()) {
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

    //Method for Editing the song name
    @FXML
    void SongEditButton (MouseEvent event){
        SongEditDialog songEditDialog = new SongEditDialog(SongsTableview, SongTabledata);
        refreshPlaylist();
    }

    //Method for adding a new song
    @FXML
    void SongNewButton (MouseEvent event) throws InvalidDataException, UnsupportedTagException, IOException {
        mediaPath = new Filechooser("add new song").getMediaPath();
        try {
            Mp3File mp3file = new Mp3File(mediaPath);
            try {
                new NewSongDialog(mediaPath, SongTabledata);
            } catch (InvalidDataException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedTagException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e){
        }
    }

    //Method for Deleting a song from a playlist
    @FXML
    void SongOnPlaylistDeleteButton (MouseEvent event){
        String pn = SongsOnPlaylistListView.getSelectionModel().getSelectedItem();

        if (pn != null){
            alertConfirmation.alert("Are you sure you want to delete " + pn + " ?");
            if (alertConfirmation.isYesno()) {
                sdi.deleteplaylistsong(sdi.getidfromtitle(SongsOnPlaylistListView.getSelectionModel().getSelectedItem()));
                pdi.updatesongCount(pdi.countSongs(PlaylistTableview.getSelectionModel().getSelectedItem().getId()),PlaylistTableview.getSelectionModel().getSelectedItem().getId());


            }
            int privselect = PlaylistTableview.getSelectionModel().getSelectedIndex();
            pdi.getAllPlaylists(tabeldata);
            PlaylistTableview.getSelectionModel().select(privselect);
            refreshPlaylist();
        }

    }

    //Method to move the song in the playlist up
    public void moveElementUp(ObservableList<String> list, String element) {
        int index = list.indexOf(element);
        if (index > 0 && index < list.size()) {
            Collections.swap(list, index, index - 1);
        }
    }


    //Method to move the song in the playlist down
    public void moveElementDown(ObservableList<String> list, String element) {
        int index = list.indexOf(element);
        if (index >= 0 && index < list.size() - 1) {
            Collections.swap(list, index, index + 1);
        }
    }

    //Method for moving the song down in the database
    @FXML
    void SongOnPlaylistDownButton (MouseEvent event){
        moveElementDown(playlistNames, SongsOnPlaylistListView.getSelectionModel().getSelectedItem());
        SongsOnPlaylistListView.getSelectionModel().selectNext();
        SongsOnPlaylistListView.setItems(playlistNames);
        updateplaylistsongs(playlistNames);
    }


    //Method for moving the song up in the database
    @FXML
    void SongOnPlaylistUpButton (MouseEvent event){
        moveElementUp(playlistNames, SongsOnPlaylistListView.getSelectionModel().getSelectedItem());
        SongsOnPlaylistListView.getSelectionModel().selectPrevious();
        SongsOnPlaylistListView.setItems(playlistNames);
        updateplaylistsongs(playlistNames);
    }


    //Filters based on text
    @FXML
    void FilterButton (MouseEvent event) {
        filterTextField.clear();
    }

    //Method for the volume slider to change its value
    @FXML
    void VolumeSlider (MouseEvent event){
        if (VolumeSliderButton.isValueChanging()){
            try{
                player.getMediaPlayer().volumeProperty().setValue(VolumeSliderButton.getValue());
            }catch (NullPointerException e){

            }
        }
    }

    //Method for playing the next song when clicking next
    public void NextButtonclicked(MouseEvent event) throws MalformedURLException {

        if (SongsTableview.getSelectionModel().getSelectedItem() != null){
            SongsTableview.getSelectionModel().selectNext();
            path = SongsTableview.getSelectionModel().getSelectedItem().getURL();
            player.getMediaPlayer().stop();
            player.setPath(path);
            player.getMediaPlayer().play();
            isplayingText.setText(SongsTableview.getSelectionModel().getSelectedItem().getTitel() + " " + "Is Playing");
        } else if (SongsOnPlaylistListView.getSelectionModel().getSelectedItem() != null) {
            SongsOnPlaylistListView.getSelectionModel().selectNext();
            path = (sdi.geturlfromtitle(SongsOnPlaylistListView.getSelectionModel().getSelectedItem()));
            player.getMediaPlayer().stop();
            player.setPath(path);
            player.getMediaPlayer().play();
            isplayingText.setText(SongsOnPlaylistListView.getSelectionModel().getSelectedItem() + " " + "Is Playing");

        }

    }
    //Method for clicking the volume button
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
    //Method for Filtering
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

    //Method for event on clicking the playlist and refreshes it
    public void playlistonmouseclicked(MouseEvent event) {
        refreshPlaylist();

    }

    //Method for clicking the playlistsong
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
    //Method for Clicking the songstableview
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
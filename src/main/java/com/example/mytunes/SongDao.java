package com.example.mytunes;

import javafx.collections.ObservableList;

public interface SongDao {
    public boolean saveSong(Song song);

    boolean deleteSong(Song song);

    void getAllSongs(ObservableList<Song> SongTabledata);

    void editSong(Song song);

    ObservableList<String> getAllPlaylistSong(int playlistid);

    String geturlfromtitle(String songtitel);

    void deleteplaylistsong(int id);

    int getidfromtitle(String title);

}

package com.example.mytunes;

import javafx.collections.ObservableList;

import java.util.List;

public interface SongDao {
    public boolean saveSong(Song song);

    boolean deleteSong(Song song);

    void getAllSongs(ObservableList<Song> SongTabledata);

    void editSong(Song song);
    List<String> showSongById(List<Integer> song);

}

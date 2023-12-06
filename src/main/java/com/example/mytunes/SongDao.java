package com.example.mytunes;

import javafx.collections.ObservableList;

public interface SongDao {
    public boolean saveSong(Song song);

    boolean deleteSong(Song song);

    void getAllSongs(ObservableList<Song> SongTabledata);

    void editSong(Song song);

}

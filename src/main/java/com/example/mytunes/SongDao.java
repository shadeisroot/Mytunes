package com.example.mytunes;

import javafx.collections.ObservableList;

public interface SongDao {
    public boolean saveSong(Song song);

    void getAllPlaylists(ObservableList<Playlist> tabeldata);

    boolean deletePlaylist(Playlist p);
}

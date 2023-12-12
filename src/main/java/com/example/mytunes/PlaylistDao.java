package com.example.mytunes;

import javafx.collections.ObservableList;

import java.util.List;

public interface PlaylistDao {
    void getAllPlaylists(ObservableList<Playlist> tabeldata);
    boolean deletePlaylist(Playlist p);
    void editPlaylist(Playlist playlist);
    boolean newPlaylist(Playlist playlist);
    void addtoplaylistsong(Playlist playlist, Song song);
    List<Integer> showallsongsfromPlaylist(Playlist playlist);

}

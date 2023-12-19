package com.example.mytunes.Data;

import com.example.mytunes.Business.Playlist;
import com.example.mytunes.Business.Song;
import javafx.collections.ObservableList;

public interface PlaylistDao {
    void getAllPlaylists(ObservableList<Playlist> tabeldata);
    boolean deletePlaylist(Playlist p);
    void editPlaylist(Playlist playlist);
    boolean newPlaylist(Playlist playlist);
    void addtoplaylistsong(Playlist playlist, Song song);
    void updatePosition(int playlistId, int newPosition ,int songId);

    void updatesongCount(int songs, int id);
    int countSongs(int id);
    ObservableList<Double> getLength(int playlistId);
    void updatelengthplaylist(double length, int id);

}

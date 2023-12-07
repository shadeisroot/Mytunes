package com.example.mytunes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private SimpleStringProperty name;
    private SimpleIntegerProperty songs;
    private SimpleDoubleProperty length;
    private List<Song> songsOnPlaylist;

    private SimpleIntegerProperty id;

    public Playlist(Integer id ,String name, Integer songs, Double length) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.songs = new SimpleIntegerProperty(songs);
        this.length = new SimpleDoubleProperty(length);
    }


    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);

        this.songsOnPlaylist = new ArrayList<>();
    }

    public void addSong (Song song) {
        songsOnPlaylist.add(song);
    }

    public List<Song> getSongsOnPlaylist() {
        return songsOnPlaylist;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getSongs() {
        return songs.get();
    }

    public SimpleIntegerProperty songsProperty() {
        return songs;
    }

    public double getLength() {
        return length.get();
    }

    public SimpleDoubleProperty lengthProperty() {
        return length;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setSongs(int songs) {
        this.songs.set(songs);
    }

    public void setLength(double length) {
        this.length.set(length);
    }
}

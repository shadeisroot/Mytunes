package com.example.mytunes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Song {
    private SimpleStringProperty Titel;
    private SimpleStringProperty Artist;
    private SimpleStringProperty Genre;
    private SimpleStringProperty URL;
    private double Length;


    private SimpleIntegerProperty id;

    public String getURL() {
        return URL.get();
    }

    public SimpleStringProperty URLProperty() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL.set(URL);
    }

    public Song(Integer id, String titel, String artist, String genre, double length, String url) {
        this.Titel = new SimpleStringProperty(titel);
        this.Artist = new SimpleStringProperty(artist);
        this.Genre = new SimpleStringProperty(genre);
        this.URL = new SimpleStringProperty(url);
        this.id = new SimpleIntegerProperty(id);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public SimpleIntegerProperty idProperty() {
        return id;

    }



    public String getTitel() {
        return Titel.get();
    }


    public SimpleStringProperty titleProperty() {
        return Titel;
    }

    public void setTitel(String title) {
        this.Titel.set(title);
    }

    public String getArtist() {
        return Artist.get();
    }

    public SimpleStringProperty artistProperty() {
        return Artist;
    }

    public void setArtist(String artist) {
        this.Artist.set(artist);
    }

    public String getGenre() {
        return Genre.get();
    }

    public SimpleStringProperty genreProperty() {
        return Genre;
    }

    public void setGenre(String genre) {
        this.Genre.set(genre);
    }

    public double getLength() {
        return Length;
    }

    public void setLength(double length) {
        Length = length;
    }

}
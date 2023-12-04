package com.example.mytunes;

import javafx.beans.property.SimpleStringProperty;

public class Song {
    private SimpleStringProperty Titel;
    private SimpleStringProperty Artist;
    private SimpleStringProperty Genre;
    private double Length;

    public Song(String titel, String artist, String genre, double length) {
        this.Titel = new SimpleStringProperty(titel);
        this.Artist = new SimpleStringProperty(artist);
        this.Genre = new SimpleStringProperty(genre);

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

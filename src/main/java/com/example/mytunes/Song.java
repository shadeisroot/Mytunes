package com.example.mytunes;

import javafx.beans.property.SimpleStringProperty;

public class Song {
    private SimpleStringProperty Title;
    private SimpleStringProperty Artist;
    private SimpleStringProperty Genre;
    private double Length;

    public Song(String title, String artist, String genre, double length) {
        this.Title = new SimpleStringProperty(title);
        this.Artist = new SimpleStringProperty(artist);
        this.Genre = new SimpleStringProperty(genre);

    }

    public String getTitle() {
        return Title.get();
    }

    public SimpleStringProperty titleProperty() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title.set(title);
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

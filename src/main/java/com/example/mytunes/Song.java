package com.example.mytunes;

public class Song {
    private String Title;
    private String Artist;
    private String Genre;
    private double Length;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public double getLength() {
        return Length;
    }

    public void setLength(double length) {
        Length = length;
    }

    public Song(String title, String artist, String genre, double length) {
        Title = title;
        Artist = artist;
        Genre = genre;
        Length = length;
    }
}

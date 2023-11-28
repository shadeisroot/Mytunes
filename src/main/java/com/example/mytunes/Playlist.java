package com.example.mytunes;

public class Playlist {
    private String name;
    private int songs;
    private double length;

    public void setName(String name) {
        this.name = name;
    }

    public void setSongs(int songs) {
        this.songs = songs;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public int getSongs() {
        return songs;
    }

    public double getLength() {
        return length;
    }
}

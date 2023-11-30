package com.example.mytunes;

public interface SongDao {
    public boolean saveSong(Song song);

    public void deleteSong(Song song);
}

package com.example.mytunes;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDaoimpl implements SongDao {
    private Connection con;

    public SongDaoimpl() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://10.176.111.34:1433;database=Mytunesgrp2;userName=CSe2023t_t_2;password=CSe2023tT2#23;encrypt=true;trustServerCertificate=true");

        } catch (SQLException e) {
            System.out.println("Cant connect to Database" + e);
        }
    }

    @Override
    public boolean saveSong(Song song) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Songs VALUES(?,?,?,?,?);");
            ps.setString(1, song.getTitel());
            ps.setString(2, song.getArtist());
            ps.setString(3, song.getGenre());
            ps.setDouble(4, song.getLength());
            ps.setString(5, song.getURL());
            ps.executeUpdate();
            System.out.println(("Song Created"));
            return true;
        } catch (SQLException e) {
            System.out.println("Cannot Add Song" + e);
            return false;
        }

    }

    public boolean deleteSong(Song song) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Songs WHERE Titel = ?");
            ps.setString(1, song.getTitel());
            ps.executeUpdate();
            return true;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllSongs(ObservableList<Song> SongTabledata) {
        SongTabledata.clear();
        int antal = 0;
        try {
            Statement database = con.createStatement();
            String sql = "SELECT * FROM Songs";
            ResultSet rs = database.executeQuery(sql);
            while (rs.next()) {
                String titel = rs.getString("Titel");
                String artist = rs.getString("Artist");
                String genre = rs.getString("Genre");
                Double length = Double.valueOf(rs.getString("Length"));
                String url = rs.getString("Url");
                Integer id = Integer.valueOf(rs.getString("id"));


                Song song = new Song(id, titel, artist, genre, length, url);
                SongTabledata.add(song);
                ++antal;
            }
        } catch (SQLException e) {
            System.err.println("Kan ikke læse records" + e);
        }

    }

    public void editSong(Song song) {
        try {
            Statement database = con.createStatement();
            String sql = "UPDATE Songs SET Titel='"
                    + song.getTitel() + "' WHERE Id='" + song.getId() + "'";
            database.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Opdatering lykkedes ikke: " + e.getMessage());
        }

    }

    public List<String> showSongById(List<Integer> songId) {
        String songTitle = null;
        List<String> songidTitle = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT Titel FROM dbo.Songs WHERE Id = ?"
            );


            for (int sid : songId) {
                preparedStatement.setInt(1, sid);
                ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query here


                if (resultSet.next()) {
                    songTitle = resultSet.getString("Titel");
                    songidTitle.add(songTitle);

                    resultSet.close();
                }
            }
                preparedStatement.close();
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return songidTitle;
        }
    }
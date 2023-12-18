package com.example.mytunes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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
    public ObservableList<String> getAllPlaylistSong(int playlistId) {
        ObservableList<String> playlistNameFromId = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT SongId FROM PlaylistSongs WHERE PlaylistId = ? ORDER BY Posistion"
            );
            preparedStatement.setInt(1, playlistId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int songId = rs.getInt("SongId");
                PreparedStatement preparedStatement2 = con.prepareStatement(
                        "SELECT Titel FROM Songs WHERE Id = ?"
                );
                preparedStatement2.setInt(1, songId);
                ResultSet rs2 = preparedStatement2.executeQuery();

                if (rs2.next()) {
                    String title = rs2.getString("Titel");
                    playlistNameFromId.add(title);
                }
                rs2.close();
                preparedStatement2.close();
            }
            rs.close();
            preparedStatement.close();

            System.out.println(playlistNameFromId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playlistNameFromId;
    }

    public String geturlfromtitle(String songtitel){
        String url = null;
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT Url FROM Songs WHERE Titel = ?");
            preparedStatement.setString(1, songtitel);
            ResultSet resultSet = preparedStatement.executeQuery(); // Use executeQuery for SELECT

            if (resultSet.next()) {
                url = resultSet.getString("Url"); // Get the URL value from the result set
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return url;
    }

    public void deleteplaylistsong(int id) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM PlaylistSongs WHERE SongId = ? ");
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int getidfromtitle(String title){
        int id = 0;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT Id FROM Songs WHERE Titel = ?");
            ps.setString(1, title);
            ResultSet resultSet = ps.executeQuery(); // Use executeQuery for SELECT

            if (resultSet.next()) {
                id = resultSet.getInt("id"); // Get the URL value from the result set
            }

            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

}
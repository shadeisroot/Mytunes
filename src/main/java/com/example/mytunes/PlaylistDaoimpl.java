package com.example.mytunes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDaoimpl implements PlaylistDao {
    private Connection con;
    private SongDao sdi;

    public PlaylistDaoimpl() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://10.176.111.34:1433;database=Mytunesgrp2;userName=CSe2023t_t_2;password=CSe2023tT2#23;encrypt=true;trustServerCertificate=true");

        } catch (SQLException e) {
            System.out.println("Cant connect to Database" + e);
        }
    }

    public void getAllPlaylists(ObservableList<Playlist> tabeldata) {
        tabeldata.clear();
        int antal = 0;
        try {
            Statement database = con.createStatement();
            String sql = "SELECT * FROM Playlist";
            ResultSet rs = database.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                Integer songs = rs.getInt("Songs");
                Double length = rs.getDouble("length");
                int id = rs.getInt("id");

                Playlist playlist = new Playlist(name, songs, length, id);
                tabeldata.add(playlist);
                ++antal;
            }
        } catch (SQLException e) {
            System.err.println("Kan ikke læse records");
        }

    }

    public boolean deletePlaylist(Playlist playlist) {
        try {
            Statement database = con.createStatement();
            String sql = "DELETE FROM Playlist WHERE name = '" + playlist.getName() + "'";
            database.executeUpdate(sql);

            String sql2 = "DELETE FROM PlaylistSongs WHERE PlaylistId = '" + playlist.getId() + "'";
            database.executeUpdate(sql2);

            return true;
        } catch (SQLException e) {
            System.err.println("Sletning lykkedes ikke" + e.getMessage());
            return false;
        }

    }

    @Override
    public void editPlaylist(Playlist playlist) {
        try {
            Statement database = con.createStatement();
            String sql = "UPDATE Playlist SET name='"
                    + playlist.getName() + "' WHERE Id='" + playlist.getId() + "'";
            database.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Opdatering lykkedes ikke: " + e.getMessage());
        }

    }

    public boolean newPlaylist(Playlist playlist) {
        try {
            Statement database = con.createStatement();
            String sql = "INSERT INTO Playlist (Name, Songs, Length) VALUES "
                    + "('" + playlist.getName()
                    + "', '0', '0.0')";
            ;
            database.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.err.println("Kan ikke indsætte playlisten");
            return false;
        }

    }

    public void addtoplaylistsong(Playlist playlist, Song song) {

        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO PlaylistSongs (PlaylistId, SongId) VALUES (?, ?)"
            );
            preparedStatement.setInt(1, playlist.getId());
            preparedStatement.setInt(2, song.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your application's error handling strategy
        }
    }


    public void updatePosition(int playlistId, int newPosition ,int songId) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "UPDATE PlaylistSongs SET Posistion = ? WHERE playlistId = ? AND songId = ?"
            );
            preparedStatement.setInt(1, newPosition);
            preparedStatement.setInt(2, playlistId);
            preparedStatement.setInt(3, songId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating position for the song in the playlist");
        }
    }

    //test

    public int countSongs(int id){
        int count = 0;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT COUNT(*) FROM PlaylistSongs WHERE PlaylistId = ?"
            );

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Retrieve the count from the result set
            if (resultSet.next()) {
                count = resultSet.getInt(1); // Getting the count from the first column
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(count);
        return count;
    }

    public void updatesongCount(int songs, int id){
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "UPDATE Playlist SET Songs = ? WHERE Id = ?"
            );
            preparedStatement.setInt(1, songs);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your application's error handling strategy
        }

    }

    public void updatelengthplaylist(double length , int id) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "UPDATE Playlist SET Length = ? WHERE Id = ?"
            );

            preparedStatement.setDouble(1, length);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Double> getLength(int playlistId) {
        ObservableList<Double> playlistlengthfromid = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT SongId FROM PlaylistSongs WHERE PlaylistId = ? ORDER BY Posistion"
            );
            preparedStatement.setInt(1, playlistId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int songId = rs.getInt("SongId");
                PreparedStatement preparedStatement2 = con.prepareStatement(
                        "SELECT Length FROM Songs WHERE Id = ?"
                );
                preparedStatement2.setInt(1, songId);
                ResultSet rs2 = preparedStatement2.executeQuery();

                if (rs2.next()) {
                    double length = rs2.getDouble("length");
                    playlistlengthfromid.add((double) length);
                }
                rs2.close();
                preparedStatement2.close();
            }
            rs.close();
            preparedStatement.close();

            System.out.println(playlistlengthfromid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playlistlengthfromid;
    }





}
package com.example.mytunes;

import javafx.collections.ObservableList;

import java.sql.*;

public class PlaylistDaoimpl implements PlaylistDao {
    private Connection con;

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
                Integer songs = Integer.valueOf(rs.getString("songs"));
                Double length = Double.valueOf(rs.getString("length"));
                int id = Integer.valueOf(rs.getString("id"));

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
            return true;
        } catch (SQLException e) {
            System.err.println("Sletning lykkedes ikke" + e.getMessage());
            return false;
        }

    }

    @Override
    public void editPlaylist(Playlist playlist) {
        try{
            Statement database = con.createStatement();
            String sql = "UPDATE Playlist SET name='"
                    + playlist.getName() + "' WHERE Id='" + playlist.getId() + "'";
            database.executeUpdate(sql);
        } catch (SQLException e){
            System.err.println("Opdatering lykkedes ikke: "+e.getMessage());
        }

    }
}

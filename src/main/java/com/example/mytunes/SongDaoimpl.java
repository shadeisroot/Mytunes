package com.example.mytunes;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.sql.*;
import javafx.scene.control.TextArea;
public class SongDaoimpl implements SongDao{
    private Connection con;

    public SongDaoimpl() {
        try{
            con = DriverManager.getConnection("jdbc:sqlserver://10.176.111.34:1433;database=Mytunesgrp2;userName=CSe2023t_t_2;password=CSe2023tT2#23;encrypt=true;trustServerCertificate=true");

        } catch (SQLException e) {
            System.out.println("Cant connect to Database" + e);
        }
    }

    @Override
    public boolean saveSong(Song song) {
        try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO Songs VALUES(?,?,?,?);");
            ps.setString(1, song.getTitle());
            ps.setString(2,song.getArtist());
            ps.setString(3,song.getGenre());
            ps.setDouble(4, song.getLength());
            ps.executeUpdate();
            System.out.println(("Song Created"));
            return true;
        } catch (SQLException e) {
            System.out.println("Cannot Add Song" + e);
            return false;
        }

    }

    public void getAllPlaylists(ObservableList<Playlist> tabeldata) {
        tabeldata.clear();
        int antal = 0;
        try{
            Statement database = con.createStatement();
            String sql = "SELECT * FROM Playlist";
            ResultSet rs = database.executeQuery(sql);
            while (rs.next()) {
                String name     = rs.getString("name");
                Integer songs    = Integer.valueOf(rs.getString("songs"));
                Double length     = Double.valueOf(rs.getString("length"));

                Playlist playlist = new Playlist(name, songs, length);
                tabeldata.add(playlist);
                ++antal;
            }
        } catch (SQLException e){
            System.err.println("Kan ikke læse records");
        }

    }

    public boolean deletePlaylist(Playlist playlist) {
        try{
            Statement database = con.createStatement();
            String sql = "DELETE FROM Playlist WHERE name = '" + playlist.getName() + "'";
            database.executeUpdate(sql);
            return true;
        } catch (SQLException e){
            System.err.println("Sletning lykkedes ikke"+e.getMessage());
            return false;
        }
    }
}

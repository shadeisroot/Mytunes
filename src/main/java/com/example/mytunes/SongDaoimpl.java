package com.example.mytunes;

import javafx.scene.control.TextArea;

import java.sql.*;
import javafx.scene.control.TextArea;
public class SongDaoimpl implements SongDao{
    private Connection con;
    private TextArea status;
    public SongDaoimpl(TextArea s) {
        status = s;
        try{
            con = DriverManager.getConnection("jdbc:sqlserver://10.176.111.34:1433;database=Mytunesgrp2;encrypt=true;trustServerCertificate=true");

        } catch (SQLException e) {
            System.out.println("Cant connect to Database");
        }
    }

    @Override
    public boolean saveSong(Song song) {
        try{
            Statement database = con.createStatement();
            String sql = "INSERT INTO Song(Title, Artist, Genre, Length) VALUES "
                    + "('" + song.getTitle()
                    + "','" + song.getArtist()
                    + "','" + song.getGenre()
                    + "','" + song.getLength()
                    + ")";
            database.executeUpdate(sql);
            System.out.println(("Song Created"));
            return true;
        } catch (SQLException e) {
            System.out.println("Cannot Add Song");
            return false;
        }

    }
}

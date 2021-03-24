package com.codecool.database;

import java.sql.*;

public class RadioCharts {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public RadioCharts(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public String getMostPlayedSong() {
        String ret = "";
        String sql = "SELECT song, SUM(times_aired) FROM music_broadcast " +
                "GROUP BY song " +
                "ORDER BY SUM(times_aired) DESC " +
                "LIMIT 100;";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ret = rs.getString("song");
                return ret;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String getMostActiveArtist() {
        String ret = "";
        String sql = "SELECT artist FROM ( " +
                "SELECT artist FROM music_broadcast " +
                "GROUP BY artist,song) " +
                "GROUP BY artist " +
                "ORDER BY COUNT(1) DESC " +
                "LIMIT 1;";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ret = rs.getString("artist");
                return ret;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}

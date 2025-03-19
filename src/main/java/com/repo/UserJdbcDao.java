package com.repo;

import com.entity.ShortenUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserJdbcDao {
    public List<ShortenUser> getAllUsers() throws Exception {
        List<ShortenUser> users = new ArrayList<>();
        String url = "jdbc:postgresql://localhost:5432/db_cuc_to";
        String user = "postgres";
        String password = "ducdaibang9c";

        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT user_id, age, gender, interested_in_gender, city_code, height, weight, longitude, latitude, expirience FROM public.users");

        while (rs.next()) {
            ShortenUser u = new ShortenUser();
            u.setUserId(rs.getLong("user_id"));
            u.setAge(rs.getInt("age"));
            u.setGender(rs.getInt("gender"));
            u.setInterestedInGender(rs.getInt("interested_in_gender"));
            u.setCityCode(rs.getInt("city_code"));
            u.setHeight(rs.getInt("height"));
            u.setWeight(rs.getInt("weight"));
            u.setLongitude(rs.getDouble("longitude"));
            u.setLatitude(rs.getDouble("latitude"));
            u.setExpirience(rs.getInt("expirience"));
            users.add(u);
        }
        rs.close();
        stmt.close();
        conn.close();

        return users;
    }
}

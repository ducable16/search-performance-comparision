package com.service;

import com.entity.SQLUser;
import com.entity.ShortenUser;
import com.repo.UserJdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RamSearchService {

    List<ShortenUser> list = new ArrayList<>();
    private long[] userId = new long[10500000];
    private int[] age = new int[10500000];
    private int[] gender = new int[10500000];
    private int[] interestedInGender = new int[10500000];
    private int[] cityCode = new int[10500000];

    // Thông tin chi tiết
    private int[] height = new int[10500000];
    private int[] weight = new int[10500000];
    private double[] longitude = new double[10500000];
    private double[] latitude = new double[10500000];
    private int[] expirience = new int[10500000];
    private int size = 0;

    @Autowired
    UserJdbcDao userJdbcDao;

    public void loadToRam() throws SQLException {
        int i = -1;
        List<ShortenUser> users = new ArrayList<>();
        String url = "jdbc:postgresql://localhost:5432/db_cuc_to";
        String user = "postgres";
        String password = "ducdaibang9c";

        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT user_id, age, gender, interested_in_gender, city_code, height, weight, longitude, latitude, expirience FROM public.users");

        while (rs.next()) {
            userId[++i] = rs.getLong("user_id");
            age[i] = rs.getInt("age");
            gender[i] = rs.getInt("gender");
            interestedInGender[i] = rs.getInt("interested_in_gender");
            cityCode[i] = rs.getInt("city_code");
            height[i] = rs.getInt("height");
            weight[i] = rs.getInt("weight");
            longitude[i] = rs.getDouble("longitude");
            latitude[i] = rs.getDouble("latitude");
            expirience[i] = rs.getInt("expirience");
            System.out.println(i);
        }
        size = i;
        rs.close();
        stmt.close();
        conn.close();
    }

    public List<SQLUser> ramSearch(
            Double o_longitude,
            Double o_latitude,
            Double o_distanceInMeters,
            Integer o_gender,
            Integer o_interestedInGender,
            Integer[] o_cityCodes,
            Integer o_minAge,
            Integer o_maxAge,
            Integer o_minWeight,
            Integer o_maxWeight,
            Integer o_minHeight,
            Integer o_maxHeight,
            Integer o_minExpirience,
            Integer o_maxExpirience
    ) throws Exception {
        List<Long> lst = new ArrayList<>();
        int limit = 100;
        for (int i = 0; i <= size; i++) {
            if (o_gender == null || gender[i] == o_gender) {
                if (o_interestedInGender == null || interestedInGender[i] == o_interestedInGender) {
                    if ((o_minAge == null || age[i] >= o_minAge) && (o_maxAge == null || age[i] <= o_maxAge)) {
                        if ((o_minWeight == null || weight[i] >= o_minWeight) && (o_maxWeight == null || weight[i] <= o_maxWeight)) {
                            if ((o_minHeight == null || height[i] >= o_minHeight) && (o_maxHeight == null || height[i] <= o_maxHeight)) {
                                if ((o_minExpirience == null || expirience[i] >= o_minExpirience) &&
                                        (o_maxExpirience == null || expirience[i] <= o_maxExpirience)) {

                                    boolean matchCity = true;
                                    if (o_cityCodes != null && o_cityCodes.length > 0) {
                                        matchCity = false;
                                        for (int j = 0; j < o_cityCodes.length; j++) {
                                            if (cityCode[i] == o_cityCodes[j]) {
                                                matchCity = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (matchCity) {
                                        if (o_distanceInMeters == null ||
                                                o_distanceInMeters >= GeoUtils.haversine(o_latitude, o_longitude, latitude[i], longitude[i])) {
                                            lst.add(userId[i]);
                                            if (--limit == 0) {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return userJdbcDao.queryUsersWithUserId(lst);
    }
}

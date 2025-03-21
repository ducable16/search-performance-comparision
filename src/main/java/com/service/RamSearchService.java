package com.service;

import com.entity.SQLUser;
import com.entity.ShortenUser;
import com.repo.UserJdbcDao;
import com.repo.jpa.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RamSearchService {

    List<ShortenUser> list = new ArrayList<>();
    private int[] userId = new int[10500000];
    private int[] age = new int[10500000];
    private int[] gender = new int[10500000];
    private int[] interestedInGender = new int[10500000];
    private int[] cityCode = new int[10500000];
    Map<Long, int[]> map = new HashMap<>();
    // Thông tin chi tiết
    private int[] height = new int[10500000];
    private int[] weight = new int[10500000];
    private double[] longitude = new double[10500000];
    private double[] latitude = new double[10500000];
    private int[] expirience = new int[10500000];
    private int[] mapToData = new int[10500000];
    private int size = 0;

    @Autowired
    UserJdbcDao userJdbcDao;
    @Autowired
    UserRepository userRepository;

    public long genKey(int age, int gender, int interestedInGender, int cityCode, int height, int weight, int expirience) {
        long key = age;
        key *= 10 + gender;
        key *= 10 + interestedInGender;
        key *= 100 + cityCode;
        key *= 100 + (height - 100);
        key *= 100 + (weight - 30);
        key *= 100 + expirience;
        return key;
    }

    public void loadToRam() throws SQLException {
        int i = -1;
        String url = "jdbc:postgresql://localhost:5432/db_cuc_to";
        String user = "postgres";
        String password = "ducdaibang9c";

        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT user_id, age, gender, interested_in_gender, city_code, height, weight, longitude, latitude, expirience FROM public.users");

        while (rs.next()) {
            userId[++i] = rs.getInt("user_id");
            age[i] = rs.getInt("age");
            gender[i] = rs.getInt("gender");
            interestedInGender[i] = rs.getInt("interested_in_gender");
            cityCode[i] = rs.getInt("city_code");
            height[i] = rs.getInt("height");
            weight[i] = rs.getInt("weight");
            longitude[i] = rs.getDouble("longitude");
            latitude[i] = rs.getDouble("latitude");
            expirience[i] = rs.getInt("expirience");
            long key = genKey(age[i], gender[i], interestedInGender[i], cityCode[i], height[i], weight[i], expirience[i]);
            if(map.containsKey(key)) {
                int[] data = map.get(key);
                data[++data[0]] = userId[i];
            }
            else {
                int[] data = new int[150];
                data[1] = userId[i];
                data[0] = 1;
                map.put(key, data);
            }
            mapToData[(int)userId[i]] = i;
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
        if(o_cityCodes == null) {
            o_cityCodes = new Integer[50];
            for (int i = 1; i <= 50; i++) {
                o_cityCodes[i - 1] = i;
            }
        }
        if(o_minAge == null) o_minAge = 18;
        if(o_maxAge == null) o_maxAge = 60;
        if(o_minWeight == null) o_minWeight = 30;
        if(o_maxWeight == null) o_maxWeight = 100;
        if(o_minHeight == null) o_minHeight = 100;
        if(o_maxHeight == null) o_maxHeight = 199;
        if(o_minExpirience == null) o_minExpirience = 1;
        if(o_maxExpirience == null) o_maxExpirience = 20;
        for(int i : o_cityCodes) {
            for(int j = o_minAge; j <= o_maxAge; j++) {
                for(int k = o_minHeight; k <= o_maxHeight; k++) {
                    for(int l = o_minWeight; l <= o_maxWeight; l++) {
                        for(int p = o_minExpirience; p <= o_maxExpirience; p++) {
                            long key = genKey(j, o_gender, o_interestedInGender, i, k, l, p);
                            if(map.containsKey(key)) {
                                int[] data = map.get(key);
                                for(int u = 0; u < data.length; u++) {
                                    if(!lst.contains((long)data[u])) {
                                        lst.add((long)data[u]);
                                        limit--;
                                    }
                                    if(limit == 0) {
                                        return userRepository.findByUserIdIn(lst);
//                                        return userJdbcDao.queryUsersWithUserId(lst);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return userRepository.findByUserIdIn(lst);
//        return userJdbcDao.queryUsersWithUserId(lst);
    }
}

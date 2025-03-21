package com.repo;

import com.entity.SQLUser;
import com.entity.ShortenUser;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Repository
public class UserJdbcDao {
    public List<SQLUser> queryUsersWithCondition(
            Double longitude,
            Double latitude,
            Double distanceInMeters,
            Integer gender,
            Integer interestedInGender,
            Integer[] cityCodes,
            Integer minAge,
            Integer maxAge,
            Integer minWeight,
            Integer maxWeight,
            Integer minHeight,
            Integer maxHeight,
            Integer minExpirience,
            Integer maxExpirience
    ) throws Exception {
        List<SQLUser> users = new ArrayList<>();
        String url = "jdbc:postgresql://localhost:5432/db_cuc_to";
        String user = "postgres";
        String password = "ducdaibang9c";

        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        StringBuilder sql = new StringBuilder("SELECT * FROM users WHERE 1 = 1");

        if (gender != null) {
            sql.append(" AND gender = ").append(gender);
        }
        if (interestedInGender != null) {
            sql.append(" AND interested_in_gender = ").append(interestedInGender);
        }
        if (minAge != null) {
            sql.append(" AND age >= ").append(minAge);
        }
        if (maxAge != null) {
            sql.append(" AND age <= ").append(maxAge);
        }
        if (minWeight != null) {
            sql.append(" AND weight >= ").append(minWeight);
        }
        if (maxWeight != null) {
            sql.append(" AND weight <= ").append(maxWeight);
        }
        if (minHeight != null) {
            sql.append(" AND height >= ").append(minHeight);
        }
        if (maxHeight != null) {
            sql.append(" AND height <= ").append(maxHeight);
        }
        if (minExpirience != null) {
            sql.append(" AND expirience >= ").append(minExpirience);
        }
        if (maxExpirience != null) {
            sql.append(" AND expirience <= ").append(maxExpirience);
        }
        if (longitude != null && latitude != null && distanceInMeters != null) {
            sql.append(" AND ST_DWithin(location, ST_MakePoint(")
                    .append(longitude).append(", ")
                    .append(latitude).append(")::geography, ")
                    .append(distanceInMeters).append(")");
        }
        if (cityCodes != null && cityCodes.length > 0) {
            sql.append(" AND city_code IN (");
            for (int i = 0; i < cityCodes.length; i++) {
                sql.append(cityCodes[i]);
                if (i < cityCodes.length - 1) sql.append(",");
            }
            sql.append(")");
        }

        sql.append(" LIMIT 100");
        ResultSet rs = stmt.executeQuery(sql.toString());
        while (rs.next()) {
            SQLUser u = new SQLUser();
            u.setUserId(rs.getLong("user_id"));
            u.setFirstName(rs.getString("first_name"));
            u.setLastName(rs.getString("last_name"));
            u.setNickname(rs.getString("nickname"));
            u.setEmail(rs.getString("email"));
            u.setPhoneNumber(rs.getString("phone_number"));
            u.setBirthDate(rs.getObject("birth_date", LocalDate.class));
            u.setAge(rs.getInt("age"));
            u.setGender(rs.getInt("gender"));
            u.setInterestedInGender(rs.getInt("interested_in_gender"));
            u.setBio(rs.getString("bio"));
            u.setCityCode(rs.getInt("city_code"));

            // Detail Info
            u.setHeight(rs.getInt("height"));
            u.setWeight(rs.getInt("weight"));
            u.setLongitude(rs.getDouble("longitude"));
            u.setLatitude(rs.getDouble("latitude"));
            u.setEthnicity(rs.getString("ethnicity"));
            u.setNationality(rs.getString("nationality"));
            u.setZodiacSign(rs.getString("zodiac_sign"));
            u.setPersonalityType(rs.getString("personality_type"));
            u.setReligion(rs.getString("religion"));
            u.setUniversity(rs.getString("university"));
            u.setOccupation(rs.getString("occupation"));
            u.setExpirience(rs.getInt("expirience"));
            u.setIncomeRange(rs.getLong("income_range"));

            // Hobbies & Interests
            u.setHobbies(rs.getString("hobbies"));
            u.setExerciseFrequency(rs.getString("exercise_frequency"));
            u.setPets(rs.getBoolean("pets"));
            u.setLanguagesSpoken(rs.getString("languages_spoken"));
            u.setSocialMediaApps(rs.getString("social_media_apps"));

            // Preferences
            u.setPreferredAgeMin(rs.getInt("preferred_age_min"));
            u.setPreferredAgeMax(rs.getInt("preferred_age_max"));
            u.setPreferredHeightMin(rs.getInt("preferred_height_min"));
            u.setPreferredHeightMax(rs.getInt("preferred_height_max"));
            u.setPreferredDistanceKm(rs.getObject("preferred_distance_km", Boolean.class));

            // Account & Activity
            u.setAccountCreatedAt(rs.getObject("account_created_at", LocalDateTime.class));
            u.setLastLogin(rs.getObject("last_login", LocalDateTime.class));
            u.setAccountStatus(rs.getString("account_status"));
            u.setPhoneVerified(rs.getBoolean("phone_verified"));
            u.setLastSubscriptionPaymentDate(rs.getObject("last_subscription_payment_date", LocalDateTime.class));
            u.setSubscriptionStartDate(rs.getObject("subscription_start_date", LocalDate.class));
            u.setSubscriptionExpiryDate(rs.getObject("subscription_expiry_date", LocalDate.class));

            // Social Stats
            u.setLikesGivenCount(rs.getLong("likes_given_count"));
            u.setLikesReceivedCount(rs.getLong("likes_received_count"));
            u.setMatchesCount(rs.getLong("matches_count"));
            u.setUnmatchedCount(rs.getLong("unmatched_count"));
            u.setMatchRate(rs.getDouble("match_rate"));
            u.setReportsReceivedCount(rs.getLong("reports_received_count"));
            u.setProfileViewsCount(rs.getLong("profile_views_count"));

            users.add(u);
        }
        rs.close();
        stmt.close();
        conn.close();
        return users;
    }

    public List<SQLUser> queryUsersWithUserId(List<Integer> userIds) throws Exception {
        List<SQLUser> users = new ArrayList<>();
        String url = "jdbc:postgresql://localhost:5432/db_cuc_to";
        String user = "postgres";
        String password = "ducdaibang9c";

        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        StringBuilder sql = new StringBuilder("SELECT * FROM users WHERE 1 = 1");

        if (userIds != null && !userIds.isEmpty()) {
            sql.append(" AND user_id IN (");
            for (int i = 0; i < userIds.size(); i++) {
                sql.append(userIds.get(i));
                if (i < userIds.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");
        }

        sql.append(" LIMIT 100");
        ResultSet rs = stmt.executeQuery(sql.toString());
        while (rs.next()) {
            SQLUser u = new SQLUser();
            u.setUserId(rs.getLong("user_id"));
            u.setFirstName(rs.getString("first_name"));
            u.setLastName(rs.getString("last_name"));
            u.setNickname(rs.getString("nickname"));
            u.setEmail(rs.getString("email"));
            u.setPhoneNumber(rs.getString("phone_number"));
            u.setBirthDate(rs.getObject("birth_date", LocalDate.class));
            u.setAge(rs.getInt("age"));
            u.setGender(rs.getInt("gender"));
            u.setInterestedInGender(rs.getInt("interested_in_gender"));
            u.setBio(rs.getString("bio"));
            u.setCityCode(rs.getInt("city_code"));

            // Detail Info
            u.setHeight(rs.getInt("height"));
            u.setWeight(rs.getInt("weight"));
            u.setLongitude(rs.getDouble("longitude"));
            u.setLatitude(rs.getDouble("latitude"));
            u.setEthnicity(rs.getString("ethnicity"));
            u.setNationality(rs.getString("nationality"));
            u.setZodiacSign(rs.getString("zodiac_sign"));
            u.setPersonalityType(rs.getString("personality_type"));
            u.setReligion(rs.getString("religion"));
            u.setUniversity(rs.getString("university"));
            u.setOccupation(rs.getString("occupation"));
            u.setExpirience(rs.getInt("expirience"));
            u.setIncomeRange(rs.getLong("income_range"));

            // Hobbies & Interests
            u.setHobbies(rs.getString("hobbies"));
            u.setExerciseFrequency(rs.getString("exercise_frequency"));
            u.setPets(rs.getBoolean("pets"));
            u.setLanguagesSpoken(rs.getString("languages_spoken"));
            u.setSocialMediaApps(rs.getString("social_media_apps"));

            // Preferences
            u.setPreferredAgeMin(rs.getInt("preferred_age_min"));
            u.setPreferredAgeMax(rs.getInt("preferred_age_max"));
            u.setPreferredHeightMin(rs.getInt("preferred_height_min"));
            u.setPreferredHeightMax(rs.getInt("preferred_height_max"));
            u.setPreferredDistanceKm(rs.getObject("preferred_distance_km", Boolean.class));

            // Account & Activity
            u.setAccountCreatedAt(rs.getObject("account_created_at", LocalDateTime.class));
            u.setLastLogin(rs.getObject("last_login", LocalDateTime.class));
            u.setAccountStatus(rs.getString("account_status"));
            u.setPhoneVerified(rs.getBoolean("phone_verified"));
            u.setLastSubscriptionPaymentDate(rs.getObject("last_subscription_payment_date", LocalDateTime.class));
            u.setSubscriptionStartDate(rs.getObject("subscription_start_date", LocalDate.class));
            u.setSubscriptionExpiryDate(rs.getObject("subscription_expiry_date", LocalDate.class));

            // Social Stats
            u.setLikesGivenCount(rs.getLong("likes_given_count"));
            u.setLikesReceivedCount(rs.getLong("likes_received_count"));
            u.setMatchesCount(rs.getLong("matches_count"));
            u.setUnmatchedCount(rs.getLong("unmatched_count"));
            u.setMatchRate(rs.getDouble("match_rate"));
            u.setReportsReceivedCount(rs.getLong("reports_received_count"));
            u.setProfileViewsCount(rs.getLong("profile_views_count"));

            users.add(u);
        }
        rs.close();
        stmt.close();
        conn.close();
        return users;
    }
}

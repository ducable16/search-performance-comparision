package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Document(indexName = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class ELSUser {


    // Thông tin cơ bản
    @Id
    @Field(type = FieldType.Keyword)
    private Long userId;

    // Thông tin cơ bản
    @Field(type = FieldType.Text)
    private String firstName;

    @Field(type = FieldType.Text)
    private String lastName;

    @Field(type = FieldType.Text)
    private String nickname;

    @Field(type = FieldType.Keyword)
    private String email;

    @Field(type = FieldType.Keyword)
    private String phoneNumber;

    @Field(type = FieldType.Text)
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate birthDate;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Integer)
    private Integer gender;

    @Field(type = FieldType.Integer)
    private Integer interestedInGender;

    @Field(type = FieldType.Text)
    private String bio;

    @Field(type = FieldType.Integer)
    private Integer cityCode;

    // Thông tin chi tiết
    @Field(type = FieldType.Integer)
    private Integer height;

    @Field(type = FieldType.Integer)
    private Integer weight;

    @Field(type = FieldType.Double)
    private Double longitude;

    @Field(type = FieldType.Double)
    private Double latitude;


    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Keyword)
    private String ethnicity;

    @Field(type = FieldType.Keyword)
    private String nationality;

    @Field(type = FieldType.Keyword)
    private String zodiacSign;

    @Field(type = FieldType.Keyword)
    private String personalityType;

    @Field(type = FieldType.Keyword)
    private String religion;

    @Field(type = FieldType.Text)
    private String university;

    @Field(type = FieldType.Keyword)
    private String occupation;

    @Field(type = FieldType.Integer)
    private Integer expirience;

    @Field(type = FieldType.Long)
    private Long incomeRange;

    // Thói quen và sở thích
    @Field(type = FieldType.Text)
    private String hobbies;

    @Field(type = FieldType.Keyword)
    private String exerciseFrequency;

    @Field(type = FieldType.Boolean)
    private Boolean pets;

    @Field(type = FieldType.Text)
    private String languagesSpoken;

    @Field(type = FieldType.Text)
    private String socialMediaApps;

    // Cài đặt người dùng
    @Field(type = FieldType.Integer)
    private Integer preferredAgeMin;

    @Field(type = FieldType.Integer)
    private Integer preferredAgeMax;

    @Field(type = FieldType.Integer)
    private Integer preferredHeightMin;

    @Field(type = FieldType.Integer)
    private Integer preferredHeightMax;

    @Field(type = FieldType.Boolean)
    private Boolean preferredDistanceKm;

    // Tương tác ứng dụng
    private LocalDateTime accountCreatedAt;

    private LocalDateTime lastLogin;

    @Field(type = FieldType.Keyword)
    private String accountStatus;

    @Field(type = FieldType.Boolean)
    private Boolean phoneVerified;

    private LocalDateTime lastSubscriptionPaymentDate;

    private LocalDate subscriptionStartDate;

    private LocalDate subscriptionExpiryDate;

    // Thống kê tương tác xã hội
    @Field(type = FieldType.Long)
    private Long likesGivenCount;

    @Field(type = FieldType.Long)
    private Long likesReceivedCount;

    @Field(type = FieldType.Long)
    private Long matchesCount;

    @Field(type = FieldType.Long)
    private Long unmatchedCount;

    @Field(type = FieldType.Double)
    private Double matchRate;

    @Field(type = FieldType.Long)
    private Long reportsReceivedCount;

    @Field(type = FieldType.Long)
    private Long profileViewsCount;

    public ELSUser(SQLUser user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.birthDate = user.getBirthDate();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.interestedInGender = user.getInterestedInGender();
        this.bio = user.getBio();
        this.cityCode = user.getCityCode();
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.longitude = user.getLongitude();
        this.latitude = user.getLatitude();
        this.ethnicity = user.getEthnicity();
        this.nationality = user.getNationality();
        this.zodiacSign = user.getZodiacSign();
        this.personalityType = user.getPersonalityType();
        this.religion = user.getReligion();
        this.university = user.getUniversity();
        this.occupation = user.getOccupation();
        this.expirience = user.getExpirience();
        this.incomeRange = user.getIncomeRange();
        this.hobbies = user.getHobbies();
        this.exerciseFrequency = user.getExerciseFrequency();
        this.pets = user.getPets();
        this.languagesSpoken = user.getLanguagesSpoken();
        this.socialMediaApps = user.getSocialMediaApps();
        this.preferredAgeMin = user.getPreferredAgeMin();
        this.preferredAgeMax = user.getPreferredAgeMax();
        this.preferredHeightMin = user.getPreferredHeightMin();
        this.preferredHeightMax = user.getPreferredHeightMax();
        this.preferredDistanceKm = user.getPreferredDistanceKm();
        this.accountCreatedAt = user.getAccountCreatedAt();
        this.lastLogin = user.getLastLogin();
        this.accountStatus = user.getAccountStatus();
        this.phoneVerified = user.getPhoneVerified();
        this.lastSubscriptionPaymentDate = user.getLastSubscriptionPaymentDate();
        this.subscriptionStartDate = user.getSubscriptionStartDate();
        this.subscriptionExpiryDate = user.getSubscriptionExpiryDate();
        this.likesGivenCount = user.getLikesGivenCount();
        this.likesReceivedCount = user.getLikesReceivedCount();
        this.matchesCount = user.getMatchesCount();
        this.unmatchedCount = user.getUnmatchedCount();
        this.matchRate = user.getMatchRate();
        this.reportsReceivedCount = user.getReportsReceivedCount();
        this.profileViewsCount = user.getProfileViewsCount();
        this.location = new GeoPoint(latitude, longitude);
    }
}

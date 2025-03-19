package com.entity;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class SQLUser {

    // Thông tin cơ bản
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;
    private String lastName;
    private String nickname;
    private String email;
    private String phoneNumber;

    private LocalDate birthDate;
    private Integer age;
    private Integer gender;
    private Integer interestedInGender;
    @Column(columnDefinition = "TEXT")
    private String bio;
    private Integer cityCode;

    // Thông tin chi tiết
    private Integer height; // cm
    private Integer weight; // kg
    private Double longitude;
    private Double latitude;
    private String ethnicity;
    private String nationality = "Vietnamese";
    private String zodiacSign;
    private String personalityType;
    private String religion;
    private String university;
    private String occupation;
    private Integer expirience; // years
    private Long incomeRange;

    // Thói quen và sở thích
    @Column(columnDefinition = "TEXT")
    private String hobbies;
    private String exerciseFrequency;
    private Boolean pets;
    @Column(columnDefinition = "TEXT")
    private String languagesSpoken;
    @Column(columnDefinition = "TEXT")
    private String socialMediaApps;

    // Cài đặt người dùng
    private Integer preferredAgeMin;
    private Integer preferredAgeMax;
    private Integer preferredHeightMin;
    private Integer preferredHeightMax;
    private Boolean preferredDistanceKm;

    // Tương tác ứng dụng
    private LocalDateTime accountCreatedAt;
    private LocalDateTime lastLogin;
    private String accountStatus;
    private Boolean phoneVerified;
    private LocalDateTime lastSubscriptionPaymentDate;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionExpiryDate;

    // Thống kê tương tác xã hội
    private Long likesGivenCount;
    private Long likesReceivedCount;
    private Long matchesCount;
    private Long unmatchedCount;
    private Double matchRate;
    private Long reportsReceivedCount;
    private Long profileViewsCount;
}

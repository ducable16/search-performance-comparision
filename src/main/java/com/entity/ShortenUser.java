package com.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortenUser {
    private long userId;
    private int age;
    private int gender;
    private int interestedInGender;
    private int cityCode;

    // Thông tin chi tiết
    private int height;
    private int weight;
    private double longitude;
    private double latitude;
    private int expirience;

    public ShortenUser(long userId, int age, int gender, int interestedInGender, int cityCode, int height, int weight, double longitude,  double latitude, int expirience) {
        this.userId = userId;
        this.age = age;
        this.gender = gender;
        this.interestedInGender = interestedInGender;
        this.cityCode = cityCode;
        this.height = height;
        this.longitude = longitude;
        this.latitude = latitude;
        this.weight = weight;
        this.expirience = expirience;

    }
    public ShortenUser() {}
}

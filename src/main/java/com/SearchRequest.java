package com;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchRequest {
    private Double longitude;
    private Double latitude;
    private Double distanceInMeters;

    private Integer gender;
    private Integer interestedInGender;

    private Integer minAge;
    private Integer maxAge;

    private Integer[] cityCodes;

    private Integer minWeight;
    private Integer maxWeight;

    private Integer minHeight;
    private Integer maxHeight;

    private Integer minExp;
    private Integer maxExp;
}


package com.service;

import com.entity.ELSUser;
import com.entity.SQLUser;
import com.entity.ShortenUser;
import com.repo.UserJdbcDao;
import com.repo.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserJdbcDao userJdbcDao;
    @Autowired
    UserRepository userRepository;

    public List<SQLUser> dbSearch(
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

        return userRepository.searchNearbyUsers(
                longitude,
                latitude,
                distanceInMeters,
                gender,
                interestedInGender,
                cityCodes,
                minAge,
                maxAge,
                minWeight,
                maxWeight,
                minHeight,
                maxHeight,
                minExpirience,
                maxExpirience
        );
    }




}

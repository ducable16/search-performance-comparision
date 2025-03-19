package com.repo.jpa;

import com.entity.SQLUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface UserRepository extends JpaRepository<SQLUser, Long> {


    @Query(value = "SELECT * FROM users u " +
            "WHERE (:gender IS NULL OR u.gender = :gender) " +
            "AND (:interestedInGender IS NULL OR u.interested_in_gender = :interestedInGender) " +
            "AND (:cityCodes IS NULL OR u.city_code = ANY(:cityCodes)) " +
            "AND (:minAge IS NULL OR u.age >= :minAge) " +
            "AND (:maxAge IS NULL OR u.age <= :maxAge) " +
            "AND (:minWeight IS NULL OR u.weight >= :minWeight) " +
            "AND (:maxWeight IS NULL OR u.weight <= :maxWeight) " +
            "AND (:minHeight IS NULL OR u.height >= :minHeight) " +
            "AND (:maxHeight IS NULL OR u.height <= :maxHeight) " +
            "AND (:minExp IS NULL OR u.expirience >= :minExp) " +
            "AND (:maxExp IS NULL OR u.expirience <= :maxExp) " +
            "AND ST_DWithin(u.location, ST_MakePoint(:longitude, :latitude)::geography, :distanceInMeters) " +
            "LIMIT 100",
            nativeQuery = true)
    List<SQLUser> searchNearbyUsers(@Param("longitude") Double longitude,
                                    @Param("latitude") Double latitude,
                                    @Param("distanceInMeters") Double distanceInMeters,
                                    @Param("gender") Integer gender,
                                    @Param("interestedInGender") Integer interestedInGender,
                                    @Param("cityCodes") Integer[] cityCodes,
                                    @Param("minAge") Integer minAge,
                                    @Param("maxAge") Integer maxAge,
                                    @Param("minWeight") Integer minWeight,
                                    @Param("maxWeight") Integer maxWeight,
                                    @Param("minHeight") Integer minHeight,
                                    @Param("maxHeight") Integer maxHeight,
                                    @Param("minExp") Integer minExp,
                                    @Param("maxExp") Integer maxExp);


    List<SQLUser> findByUserIdIn(List<Long> ids);



}

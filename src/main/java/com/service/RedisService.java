//package com.service;
//
//import com.entity.ShortenUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RedisService {
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    // Lưu dữ liệu vào Redis
//    public void save(ShortenUser user) {
//        redisTemplate.opsForValue().set(String.valueOf(user.getUserId()), user);
//    }
//
//    // Lấy dữ liệu từ Redis
//    public String get(String key) {
//        return (String) redisTemplate.opsForValue().get(key);
//    }
//}

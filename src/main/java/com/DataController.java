package com;

import com.entity.SQLUser;
import com.service.ElasticService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/")
public class DataController {

    @Autowired
    UserService userService;
    @Autowired
    ElasticService elasticService;
    int r = 0;
    @GetMapping("filter")
    public ResponseEntity<List<SQLUser>> filteredSearch(@RequestBody SearchRequest request) {
      Long start = System.currentTimeMillis();
      System.out.println(" - Thread: " + Thread.currentThread().getName() + "REQUEST IN at: " + LocalDateTime.now());

        ResponseEntity<List<SQLUser>> res = ResponseEntity.ok(userService.filteredSearch(
                request.getLongitude(),
                request.getLatitude(),
                request.getDistanceInMeters(),
                request.getGender(),
                request.getInterestedInGender(),
                request.getCityCodes(),
                request.getMinAge(),
                request.getMaxAge(),
                request.getMinWeight(),
                request.getMaxWeight(),
                request.getMinHeight(),
                request.getMaxHeight(),
                request.getMinExp(),
                request.getMaxExp()
        ));
        Long end = System.currentTimeMillis();
        System.out.println(" - Thread: " + Thread.currentThread().getName() + "REQUEST OUT in: " + (end - start) + "ms");
        return res;
    }
    @GetMapping("sync-db")
    public ResponseEntity<?> syncDb() {
        return ResponseEntity.ok(elasticService.initialSyncToElasticsearch());
    }

    @PostMapping("search")
    public ResponseEntity<?> elSearch(@RequestBody SearchRequest request) {
        Long start = System.currentTimeMillis();
        System.out.println(" - Thread: " + Thread.currentThread().getName() + "REQUEST IN at: " + LocalDateTime.now());
        ResponseEntity<?> response = ResponseEntity.ok(elasticService.search(
                request.getLongitude(),
                request.getLatitude(),
                request.getDistanceInMeters(),
                request.getGender(),
                request.getInterestedInGender(),
                request.getCityCodes(),
                request.getMinAge(),
                request.getMaxAge(),
                request.getMinWeight(),
                request.getMaxWeight(),
                request.getMinHeight(),
                request.getMaxHeight(),
                request.getMinExp(),
                request.getMaxExp()
        ));
        Long end = System.currentTimeMillis();
        System.out.println(" - Thread: " + Thread.currentThread().getName() + "REQUEST OUT in: " + (end - start) + "ms");
        return response;
    }
    @PostMapping("load")
    public ResponseEntity<?> loadRam() throws SQLException {
        userService.loadToRam();
        return ResponseEntity.ok("Load Ram");
    }
    @PostMapping("ram-search")
    public ResponseEntity<?> searchRam(@RequestBody SearchRequest request) {
        Long start = System.currentTimeMillis();
        System.out.println(" - Thread: " + Thread.currentThread().getName() + "REQUEST IN at: " + LocalDateTime.now());
        ResponseEntity<?> response = ResponseEntity.ok(userService.ramSearch(
                request.getLongitude(),
                request.getLatitude(),
                request.getDistanceInMeters(),
                request.getGender(),
                request.getInterestedInGender(),
                request.getCityCodes(),
                request.getMinAge(),
                request.getMaxAge(),
                request.getMinWeight(),
                request.getMaxWeight(),
                request.getMinHeight(),
                request.getMaxHeight(),
                request.getMinExp(),
                request.getMaxExp()
        ));
        Long end = System.currentTimeMillis();
        System.out.println(" - Thread: " + Thread.currentThread().getName() + "REQUEST OUT in: " + (end - start) + "ms");
        return response;
    }

}

package com.points.fetchrewards.routes;

import org.json.simple.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.points.fetchrewards.processing.PointsProcessing.calculateBalacePoints;

@RestController
public class PointsBalance {
    @GetMapping("/pointBalance")
    public ResponseEntity<String> getPointBalance() {
        JSONArray result = calculateBalacePoints();
        if(result != null) {
            return ResponseEntity.ok(result.toJSONString());
        } else {
            return ResponseEntity.ok("Invalid Request");
        }
    }
}

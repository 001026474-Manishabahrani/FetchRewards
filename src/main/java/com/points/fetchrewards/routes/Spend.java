package com.points.fetchrewards.routes;

import org.json.simple.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import static com.points.fetchrewards.processing.PointsProcessing.spend;


@RestController
@ResponseBody
public class Spend {
    @PostMapping("/spendPoints")
    public ResponseEntity<String> spendPoints(@RequestBody String body) {
        JSONArray result = spend(body);
        if(result != null) {
            return ResponseEntity.ok(result.toJSONString());
        } else {
            return ResponseEntity.ok("Invalid Request");
        }
    }
}

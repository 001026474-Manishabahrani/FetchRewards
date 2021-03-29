package com.points.fetchrewards.routes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.points.fetchrewards.processing.PointsProcessing.verifyAndAddPoints;

@RestController
@ResponseBody
public class AddTransaction {
    @RequestMapping("/")
    public String index() {
        return "Hello!";
    }

    @PostMapping("/addTransaction")
    public ResponseEntity<String>  addTransation(@RequestBody String body) {
        boolean added = verifyAndAddPoints(body);
        if(added) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.ok("Invalid Request");
        }

    }
}

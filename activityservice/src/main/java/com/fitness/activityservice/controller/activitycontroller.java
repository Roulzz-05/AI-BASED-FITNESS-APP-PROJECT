package com.fitness.activityservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.activityservice.Service.ActivityService;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
// import org.springframework.web.bind.annotation.RequestParam;






@RestController
@RequestMapping("/api/activities")
public class activitycontroller {
    @Autowired
    private ActivityService activityservice;
    
    
    @PostMapping
    public ResponseEntity<ActivityResponse> trackactivity(@RequestBody ActivityRequest request){
        return ResponseEntity.ok(activityservice.trackActivity(request)); 
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(@RequestHeader("X-User-Id") String userId){
        return ResponseEntity.ok(activityservice.getUserActivities(userId));

    }
    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable String activityId){
        return ResponseEntity.ok(activityservice.getActivityById(activityId));

    }
    
   
    
    

    
}

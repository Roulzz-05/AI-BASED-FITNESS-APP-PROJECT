package com.fitness.aiservice.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.aiservice.Model.Recomendation;
import com.fitness.aiservice.Service.ServiceRecomendations;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recomendations")
public class RecommendationController {
    private final ServiceRecomendations serviceRecomendations;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recomendation>> getUserRecomendation(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(serviceRecomendations.getRecomendationsForUser(userId));

    }
     @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recomendation> getActivityRecomendation(@PathVariable("activityId") String activityId) {
        return ResponseEntity.ok(serviceRecomendations.getRecomendationsForActivity(activityId));

    }


}

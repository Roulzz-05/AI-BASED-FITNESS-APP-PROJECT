package com.fitness.aiservice.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.fitness.aiservice.Model.Recomendation;
import com.fitness.aiservice.repo.RecommendationRepo;

@Service
@RequiredArgsConstructor
public class ServiceRecomendations {
    private final RecommendationRepo recommendationRepo;

    public List<Recomendation> getRecomendationsForUser(String userId) {
        return recommendationRepo.findByUserId(userId);
       
    }
    public Recomendation getRecomendationsForActivity(String activityId) {
        return recommendationRepo.findByActivityId(activityId).orElseThrow(() -> {
            throw new RuntimeException("No recomendation found for activity id: " + activityId);
        });
       
    }

    
}

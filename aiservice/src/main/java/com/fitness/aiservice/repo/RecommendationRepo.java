package com.fitness.aiservice.repo;
import com.fitness.aiservice.Model.Recomendation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface RecommendationRepo extends MongoRepository<Recomendation, String> {
    public List<Recomendation> findByUserId(String userId);
    public Optional<Recomendation> findByActivityId(String activityId);
    
}

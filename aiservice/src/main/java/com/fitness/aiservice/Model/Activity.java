package com.fitness.aiservice.Model;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.Id;



import lombok.Data;

@Data
public class Activity {
    @Id
    private String id;
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned ;
    private LocalDateTime startTime;


    private Map<String,Object> additionalMetrics;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}

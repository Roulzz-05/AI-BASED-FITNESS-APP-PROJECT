package com.fitness.aiservice.Service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fitness.aiservice.Model.Activity;
import com.fitness.aiservice.Model.Recomendation;
import com.fitness.aiservice.repo.RecommendationRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {
    private final Activityaiservice activityaiservice;
    private final RecommendationRepo recommendationRepo;
  
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processActivity(Activity activity) {
        log.info("Received activity: {}", activity.getId());
        Recomendation recommendation = activityaiservice.generateRecomendation(activity);
        recommendationRepo.save(recommendation);

        log.info("Generated recommendation: {}", recommendation);
       
}
}

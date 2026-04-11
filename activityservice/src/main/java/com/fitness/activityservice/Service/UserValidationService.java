package com.fitness.activityservice.Service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationService {
    private final WebClient userServiceWebClient;

    public Boolean validateUser(String userId) {
        log.info("Validating user with id: {}", userId);
        try {
            return userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("User not found with id: {}", userId);
                throw new RuntimeException("User not found with id: " + userId);
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                log.error("Invalid request for user with id: {}", userId);
                throw new RuntimeException("Invalid request for user with id: " + userId);

            }
            log.error("Error calling USER-SERVICE: {}", e.getMessage());
            throw new RuntimeException("Error calling USER-SERVICE: " + e.getMessage());
        }

    }
}

package com.fitness.aiservice.Service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.beans.factory.annotation.Value;
// import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor
@Service
public class Geminiservice {
    private final WebClient webClient;
    
     @Value("${gemini.api.url}")
     private String geminiapiurl;

     @Value("${gemini.api.key}")
     private String geminiapikey;

    public Geminiservice(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getAnswer(String prompt){
        Map<String,Object> requestbody=Map.of(
            "contents",new Object[]{
                Map.of("parts",new Object[]{
                    Map.of("text",prompt)
                })
            }
        );
        String response=webClient.post()
                    .uri(geminiapiurl+geminiapikey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestbody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        return response;

    }

}

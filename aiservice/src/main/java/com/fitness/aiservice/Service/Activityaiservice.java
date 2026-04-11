package com.fitness.aiservice.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.Model.Activity;
import com.fitness.aiservice.Model.Recomendation;
import com.fasterxml.jackson.databind.JsonNode;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class Activityaiservice {
    private final Geminiservice geminiservice;
    
    public Recomendation generateRecomendation(Activity activity){
        String prompt=createprompt(activity);
        String response=geminiservice.getAnswer(prompt);
        log.info("AI RESPONCE: {}", response);
        return  processairesponse(activity,response);
            
    }
        private Recomendation processairesponse(Activity activity, String response) {
            try{
                ObjectMapper mapper=new ObjectMapper();
                JsonNode rootNode=mapper.readTree(response);
                JsonNode textnode=rootNode.path("candidates")
                                        .get(0)
                                        .path("content")
                                        .path("parts")
                                        .get(0)
                                        .path("text");
                String jsonContent=textnode.asText()
                                        .replaceAll("```json\\n", "")
                                        .replaceAll("\\n```", "")
                                        .trim();
                log.info("Extracted JSON: {}", jsonContent);

                //Extract the analysis part from the JSON
                JsonNode analysisJson=mapper.readTree(jsonContent);
                JsonNode analysisnode=analysisJson.path("analysis");
                StringBuilder fullanalysis=new StringBuilder();

                // Extract and build the full analysis text
                addAnalysisSection(fullanalysis,analysisnode,"overall","Overall:");
                addAnalysisSection(fullanalysis,analysisnode,"pace","Pace:");
                addAnalysisSection(fullanalysis,analysisnode,"heartRate","Heart Rate:");
                addAnalysisSection(fullanalysis,analysisnode,"caloriesBurned","Calories Burned:");

                List<String> improvements=extractImprovements(analysisJson.path("improvements"));
                List<String> suggestions=extractSuggestions(analysisJson.path("suggestions"));
                List<String> safety=extractSafety(analysisJson.path("safety"));

                return Recomendation.builder()
                        .activityId(activity.getId())
                        .userId(activity.getUserId())
                        .type(activity.getType())
                        .recomendation(fullanalysis.toString().trim())
                        .improvements(improvements)
                        .suggestions(suggestions)
                        .safety(safety)
                        .createdAt(LocalDateTime.now())
                        .build();

                        

                        

            }catch(Exception e){
                e.printStackTrace();
                return  CreateDefaultRecomendation(activity);

            }

            
        }
 

    private Recomendation CreateDefaultRecomendation(Activity activity) {
         return Recomendation.builder()
                        .activityId(activity.getId())
                        .userId(activity.getUserId())
                        .type(activity.getType())
                        .recomendation("Unable to generate detailed recommendation at this time. Please try again later.")
                        .improvements(List.of("No specific improvements identified."))
                        .suggestions(List.of("No specific suggestions identified."))
                        .safety(List.of("Follow general safety guidelines."))
                        .createdAt(LocalDateTime.now())
                        .build();
        }


    private List<String> extractSafety(JsonNode safetyNode) {
          List<String> safety=new java.util.ArrayList<>();
          if(safetyNode.isArray()){
            safetyNode.forEach(safetyPoint->{
              safety.add(safetyPoint.asText());
            });
        }
        return safety.isEmpty() ? List.of("Follow general safety guidelines.") : safety ;
    }

    private List<String> extractSuggestions(JsonNode SuggestionsNode) {
          List<String> suggestions=new java.util.ArrayList<>();
          if(SuggestionsNode.isArray()){
            SuggestionsNode.forEach(suggestion->{
              String workout=suggestion.path("workout").asText();
              String description=suggestion.path("description").asText();
              suggestions.add(workout + ": " + description);
            });
        }
        return suggestions.isEmpty() ? List.of("No specific suggestions identified.") : suggestions ;
    }
        
    private List<String> extractImprovements(JsonNode improvementsNode) {
        List<String> improvements=new java.util.ArrayList<>();
        if(improvementsNode.isArray()){
          improvementsNode.forEach(improvement -> {
                String area=improvement.path("area").asText();
                String recommendation=improvement.path("recommendation").asText();
                improvements.add(area + ": " + recommendation);
            });
        }

        return improvements.isEmpty() ? List.of("No specific improvements identified.") : improvements ;
    }

    private void addAnalysisSection(StringBuilder fullanalysis, JsonNode analysisnode, String key,String prefix) {
            if(!analysisnode.path(key).isMissingNode()){
                fullanalysis.append(prefix).append(" ").append(analysisnode.path(key).asText()).append("\n");
            }
            
        }








    private String createprompt(Activity activity) {
        return String.format("""
        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
        {
          "analysis": {
            "overall": "Overall analysis here",
            "pace": "Pace analysis here",
            "heartRate": "Heart rate analysis here",
            "caloriesBurned": "Calories analysis here"
          },
          "improvements": [
            {
              "area": "Area name",
              "recommendation": "Detailed recommendation"
            }
          ],
          "suggestions": [
            {
              "workout": "Workout name",
              "description": "Detailed workout description"
            }
          ],
          "safety": [
            "Safety point 1",
            "Safety point 2"
          ]
        }

        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Metrics: %s
        
        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above.
        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
       
    }
}


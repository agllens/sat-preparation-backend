package com.sat_preparation_backend.attempts;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttemptRequest {

    private String testNum;
    private String mode;

    private Integer satScore;
    private Integer totalCorrect;
    private Integer totalAnswered;
    private Integer totalQuestions;
    private Double accuracy;
    private Integer timeTaken;

    private Map<String, SectionDto> sectionResults;
}
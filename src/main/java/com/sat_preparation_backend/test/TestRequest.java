package com.sat_preparation_backend.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestRequest {
    private String title;
    private String testNum;
    private String section;
    private List<QuestionDto> questions;
}

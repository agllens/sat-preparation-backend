package com.sat_preparation_backend.analytics;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalyticsResponse {

    private double averageScore;
    private boolean empty;

    public static AnalyticsResponse empty() {
        return new AnalyticsResponse(0, true);
    }
}
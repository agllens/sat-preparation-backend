package com.sat_preparation_backend.analytics;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "analytics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Analytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long attemptId;

//    @Column(columnDefinition = "jsonb")
//    private String weakTopics; // JSON
//
//    @Column(columnDefinition = "jsonb")
//    private String sectionScores; // JSON

    private LocalDateTime createdAt = LocalDateTime.now();
}

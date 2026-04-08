package com.sat_preparation_backend.attempts;

import com.sat_preparation_backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String testNum;
    private String testMode; // MOCK, PRACTICE, DUEL

    private Integer satScore;
    private Integer totalCorrect;
    private Integer totalAnswered;
    private Integer totalQuestions;

    private Double accuracy;
    private Integer timeTaken;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL)
    private List<AttemptSection> sections;

    private LocalDateTime createdAt = LocalDateTime.now();
}
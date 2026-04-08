package com.sat_preparation_backend.match;

import lombok.*;

//@Value
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResult {
    String winnerEmail;
    int player1Score;
    int player2Score;
}
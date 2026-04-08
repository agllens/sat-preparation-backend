package com.sat_preparation_backend.match;

import com.sat_preparation_backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Match {

    private String id;
    private User player1;
    private User player2;
    private MatchStatus status;
    private MatchType type;

    private Integer player1Score;
    private Integer player2Score;
}

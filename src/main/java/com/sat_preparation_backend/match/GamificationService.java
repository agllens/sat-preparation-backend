package com.sat_preparation_backend.match;

import com.sat_preparation_backend.user.User;
import com.sat_preparation_backend.user.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GamificationService {

    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final MatchQueueRepository queueRepository;


    public Match createDirectMatch(String challengerEmail, String opponentEmail) {

        User challenger = userRepository.findByEmail(challengerEmail)
                .orElseThrow(() -> new RuntimeException("Challenger not found"));

        User opponent = userRepository.findByEmail(opponentEmail)
                .orElseThrow(() -> new RuntimeException("Opponent not found"));

        Match match = new Match();
        match.setId(UUID.randomUUID().toString());
        match.setPlayer1(challenger);
        match.setPlayer2(opponent);
        match.setStatus(MatchStatus.ACTIVE);
        match.setType(MatchType.DIRECT);

        return matchRepository.save(match);
    }

    public Match searchRandomMatch(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return queueRepository.findRandomOpponent(user)
                .map(opponent -> {
                    Match match = new Match();
                    match.setId(UUID.randomUUID().toString());
                    match.setPlayer1(user);
                    match.setPlayer2(opponent);
                    match.setStatus(MatchStatus.ACTIVE);
                    match.setType(MatchType.RANDOM);
                    return matchRepository.save(match);
                })
                .orElseGet(() -> {
                    queueRepository.addToQueue(user);
                    return null;
                });
    }

    public MatchResult finishMatch(String matchId, int player1Score, int player2Score) {

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        match.setPlayer1Score(player1Score);
        match.setPlayer2Score(player2Score);
        match.setStatus(MatchStatus.FINISHED);

        matchRepository.save(match);

        String winner;

        if (player1Score > player2Score) {
            winner = match.getPlayer1().getEmail();
        } else if (player2Score > player1Score) {
            winner = match.getPlayer2().getEmail();
        } else {
            winner = "DRAW";
        }

        return new MatchResult(winner, player1Score, player2Score);
    }
}

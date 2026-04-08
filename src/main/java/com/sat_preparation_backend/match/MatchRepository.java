package com.sat_preparation_backend.match;

import java.util.Optional;

public interface MatchRepository {
    Match save(Match match);
    Optional<Match> findById(String id);
}

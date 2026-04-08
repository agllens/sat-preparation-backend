package com.sat_preparation_backend.match;

import com.sat_preparation_backend.user.User;

import java.util.Optional;

public interface MatchQueueRepository {
    Optional<User> findRandomOpponent(User user);
    void addToQueue(User user);
}
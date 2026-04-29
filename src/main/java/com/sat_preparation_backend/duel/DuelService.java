package com.sat_preparation_backend.duel;

import com.sat_preparation_backend.user.User;
import com.sat_preparation_backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DuelService {

    private final UserRepository userRepository;

    public OpponentDto getRandomOpponent(Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();

        List<User> users = userRepository.findAll();

        List<User> opponents = users.stream()
                .filter(u -> !u.getId().equals(currentUser.getId()))
                .toList();

        if (opponents.isEmpty()) {
            throw new RuntimeException("No opponents available");
        }

        User random = opponents.get(new Random().nextInt(opponents.size()));

        return new OpponentDto(
                random.getId(),
                random.getFirstname(),
                random.getLastname()
        );
    }
}
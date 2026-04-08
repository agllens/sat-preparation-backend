package com.sat_preparation_backend.service;

import com.sat_preparation_backend.match.*;
import com.sat_preparation_backend.user.Role;
import com.sat_preparation_backend.user.User;
import com.sat_preparation_backend.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GamificationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private MatchQueueRepository queueRepository;

    @InjectMocks
    private GamificationService service;

    @Test
    void shouldCreateDirectMatchSuccessfully() {

        User user1 = new User(
                1,
                "Aglen",
                "Zhanat",
                "aglen@test.com",
                "123",
                Role.USER);

        User user2 = new User(
                2,
                "Meruyert",
                "Zhumabay",
                "meru@test.com",
                "1234",
                Role.USER);

        when(userRepository.findByEmail("aglen@test.com"))
                .thenReturn(Optional.of(user1));

        when(userRepository.findByEmail("meru@test.com"))
                .thenReturn(Optional.of(user2));

        when(matchRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Match match = service.createDirectMatch("aglen@test.com", "meru@test.com");

        assertEquals(MatchType.DIRECT, match.getType());
        assertEquals(MatchStatus.ACTIVE, match.getStatus());
    }

    @Test
    void shouldCreateRandomMatch_whenOpponentExists() {

        User user1 = new User(
                1,
                "Aglen",
                "Zhanat",
                "aglen@test.com",
                "123",
                Role.USER);

        User user2 = new User(
                2,
                "Meruyert",
                "Zhumabay",
                "meru@test.com",
                "1234",
                Role.USER);

        when(userRepository.findByEmail("a@test.com"))
                .thenReturn(Optional.of(user1));

        when(queueRepository.findRandomOpponent(user1))
                .thenReturn(Optional.of(user2));

        when(matchRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Match match = service.searchRandomMatch("a@test.com");

        assertNotNull(match);
        assertEquals(MatchType.RANDOM, match.getType());
        assertEquals(MatchStatus.ACTIVE, match.getStatus());
    }

    @Test
    void shouldAddUserToQueue_whenNoOpponentFound() {

        User user = new User(1,
                "Aglen",
                "Zhanat",
                "aglen@test.com",
                "123",
                Role.USER);

        when(userRepository.findByEmail("khansulu@test.com"))
                .thenReturn(Optional.of(user));

        when(queueRepository.findRandomOpponent(user))
                .thenReturn(Optional.empty());

        Match match = service.searchRandomMatch("khansulu@test.com");

        assertNull(match);
        verify(queueRepository).addToQueue(user);
    }

    @Test
    void shouldFinishMatchAndReturnWinner() {

        User user1 = new User(
                1,
                "Aglen",
                "Zhanat",
                "aglen@test.com",
                "123",
                Role.USER);

        User user2 = new User(
                2,
                "Meruyert",
                "Zhumabay",
                "meru@test.com",
                "1234",
                Role.USER);

        Match match = new Match();
        match.setPlayer1(user1);
        match.setPlayer2(user2);

        when(matchRepository.findById("123"))
                .thenReturn(Optional.of(match));

        MatchResult result = service.finishMatch("123", 1400, 1300);

        assertEquals("aglen@test.com", result.getWinnerEmail());
        assertEquals(1400, result.getPlayer1Score());
        assertEquals(1300, result.getPlayer2Score());
    }
}

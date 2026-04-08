package com.sat_preparation_backend.service;


import com.sat_preparation_backend.subscription.Subscription;
import com.sat_preparation_backend.subscription.SubscriptionRepository;
import com.sat_preparation_backend.subscription.SubscriptionService;
import com.sat_preparation_backend.subscription.SubscriptionStatus;
import com.sat_preparation_backend.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    void shouldReturnTrue_whenSubscriptionIsActive() {

        User user = new User();

        Subscription subscription = Subscription.builder()
                .status(SubscriptionStatus.ACTIVE)
                .expiryDate(LocalDateTime.now().plusDays(10))
                .build();

        when(subscriptionRepository.findByUser(user))
                .thenReturn(Optional.of(subscription));

        boolean result = subscriptionService.hasActiveSubscription(user);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_whenExpired() {

        User user = new User();
        Subscription subscription = Subscription.builder()
                .status(SubscriptionStatus.ACTIVE)
                .expiryDate(LocalDateTime.now().minusDays(1))
                .build();

        when(subscriptionRepository.findByUser(user))
                .thenReturn(Optional.of(subscription));

        boolean result = subscriptionService.hasActiveSubscription(user);

        assertFalse(result);
    }
}



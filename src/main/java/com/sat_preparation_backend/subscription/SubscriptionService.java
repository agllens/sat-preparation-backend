package com.sat_preparation_backend.subscription;

import com.sat_preparation_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public boolean hasActiveSubscription(User user) {

        return subscriptionRepository.findByUser(user)
                .filter(subscription -> subscription.getStatus() == SubscriptionStatus.ACTIVE)
                .filter(sub -> sub.getExpiryDate().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    public Subscription activateSubscription(User user) {

        //note: subscription per month (will expire after 30 days)
        Subscription subscription = Subscription.builder()
                .user(user)
                .status(SubscriptionStatus.ACTIVE)
                .expiryDate(LocalDateTime.now().plusDays(30))
                .build();

        return subscriptionRepository.save(subscription);
    }
}

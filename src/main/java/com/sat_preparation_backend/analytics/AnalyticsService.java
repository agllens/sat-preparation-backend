package com.sat_preparation_backend.analytics;

import com.sat_preparation_backend.attempts.*;
import com.sat_preparation_backend.exception.SubscriptionRequiredException;
import com.sat_preparation_backend.subscription.SubscriptionService;
import com.sat_preparation_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final SubscriptionService subscriptionService;
    private final AttemptRepository attemptRepository;
    private final AnalyticsRepository analyticsRepository;
    private final AttemptSectionRepository attemptSectionRepository;

//    public AnalyticsResponse getAnalytics(User user) {
//
////        if (!subscriptionService.hasActiveSubscription(user)) {
////            throw new SubscriptionRequiredException();
////        }
//
//        List<TestAttempt> attempts = testRepository.findByUser(user);
//
//        if (attempts.isEmpty()) {
//            return AnalyticsResponse.empty();
//        }
//
//        double avgScore = attempts.stream()
//                .mapToInt(TestAttempt::getScore)
//                .average()
//                .orElse(0);
//
//        return new AnalyticsResponse(avgScore, false);
//    }

    public void saveAttemptResult(AttemptRequest request, Authentication authentication) {

        // Getting user from JWT
        User user = (User) authentication.getPrincipal();

        Attempt attempt = new Attempt();
        attempt.setUser(user);

        attempt.setTestNum(request.getTestNum());
        attempt.setTestMode(request.getMode());

        attempt.setSatScore(request.getSatScore());
        attempt.setTotalCorrect(request.getTotalCorrect());
        attempt.setTotalAnswered(request.getTotalAnswered());
        attempt.setTotalQuestions(request.getTotalQuestions());

        attempt.setAccuracy(request.getAccuracy());
        attempt.setTimeTaken(request.getTimeTaken());

        attemptRepository.save(attempt);

        request.getSectionResults().forEach((name, sec) -> {
            AttemptSection s = new AttemptSection();
            s.setAttempt(attempt);
            s.setSectionName(name);
            s.setCorrect(sec.correct);
            s.setTotal(sec.total);
            s.setAnswered(sec.answered);

            attemptSectionRepository.save(s);
        });
    }


    public Integer getLatestScore(User user) {

        List<Attempt> attempts = attemptRepository
                .findByUserOrderByCreatedAtDesc(user);

        if (attempts.isEmpty()) {
            return 0;
        }
        return attempts.get(0).getSatScore();
    }

}

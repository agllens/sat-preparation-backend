<<<<<<<< HEAD:src/main/java/com/sat_preparation_backend/attempts/AttemptController.java
package com.sat_preparation_backend.attempts;

import com.sat_preparation_backend.analytics.AnalyticsService;
import com.sat_preparation_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attempts")
@RequiredArgsConstructor
public class AttemptController {

    private final AnalyticsService analyticsService;

    @PostMapping("/save-result")
    public ResponseEntity<?> saveAttempt(
            @RequestBody AttemptRequest request,
            Authentication authentication
    ) {
        analyticsService.saveAttemptResult(request, authentication);
        return ResponseEntity.ok("Saved");
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestScore(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        var attempts = analyticsService.getLatestScore(user);
        return ResponseEntity.ok(attempts);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyAttempts(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(analyticsService.getUserAttempts(user));
    }

}
========
//package com.sat_preparation_backend.analytics;
//
//import com.sat_preparation_backend.attempts.Attempt;
//import com.sat_preparation_backend.attempts.AttemptRequest;
//import com.sat_preparation_backend.user.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/attempts")
//@RequiredArgsConstructor
//public class AnalyticsController {
//
//    private final AnalyticsService analyticsService;
//
//    @PostMapping("/save-result")
//    public ResponseEntity<?> saveAttempt(
//            @RequestBody AttemptRequest request,
//            Authentication authentication
//    ) {
//        analyticsService.saveAttemptResult(request, authentication);
//        return ResponseEntity.ok("Saved");
//    }
//
//    @GetMapping("/latest")
//    public ResponseEntity<?> getLatestScore(Authentication authentication) {
//
//        User user = (User) authentication.getPrincipal();
//
//        var attempts = analyticsService.getLatestScore(user);
//        return ResponseEntity.ok(attempts);
//    }
//}
>>>>>>>> origin/main:src/main/java/com/sat_preparation_backend/analytics/AnalyticsController.java

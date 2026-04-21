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

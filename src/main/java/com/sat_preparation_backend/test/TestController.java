package com.sat_preparation_backend.test;

import com.sat_preparation_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/questions")
    public ResponseEntity<?> getQuestions() {
        return ResponseEntity.ok(testService.getQuestions());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createTest(
            @RequestBody TestRequest request,
            @AuthenticationPrincipal User user
    ) {
        testService.createTest(request);
        return ResponseEntity.ok("Test saved");
    }

    @DeleteMapping("/questions/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        testService.deleteQuestion(id);
        return ResponseEntity.ok("Deleted");
    }

    @PutMapping("/questions/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody QuestionDto dto
    ) {
        testService.updateQuestion(id, dto);
        return ResponseEntity.ok("Updated");
    }
}

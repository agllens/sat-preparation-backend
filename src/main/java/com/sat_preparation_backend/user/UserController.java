package com.sat_preparation_backend.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<User> getMe(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(user);
    }
}

package com.sat_preparation_backend.duel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/duel")
@RequiredArgsConstructor
public class DuelController {

    private final DuelService duelService;

    @GetMapping("/opponent")
    public ResponseEntity<OpponentDto> getOpponent(Authentication authentication) {
        return ResponseEntity.ok(duelService.getRandomOpponent(authentication));
    }
}
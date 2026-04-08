package com.sat_preparation_backend.check;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/check-controller")
public class CheckController {

    @GetMapping
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("You passed the registered endpoint");
    }

}

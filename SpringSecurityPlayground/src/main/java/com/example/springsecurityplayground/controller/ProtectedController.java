package com.example.springsecurityplayground.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RestController
@RequestMapping("/protected")
public class ProtectedController {
    @GetMapping("/user/hi")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Map<String, String>> sayHi() {
        return ResponseEntity.ok(Map.of("Message", "Hi user"));
    }

    @GetMapping("/admin/hi")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> sayHiToAdmin() {
        return ResponseEntity.ok(Map.of("Message", "Hi admin"));
    }

    @GetMapping("/read/A")
    @PreAuthorize("hasAuthority('READ_A')")
    public ResponseEntity<Map<String, String>> readA() {
        return ResponseEntity.ok(Map.of("A", "A is for apple"));
    }

    @GetMapping("/read/B")
    @PreAuthorize("hasAuthority('READ_B')")
    public ResponseEntity<Map<String, String>> readB() {
        return ResponseEntity.ok(Map.of("B", "B is for b*tches"));
    }
}

package com.example.springsecurityplayground.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/protected")
public class ProtectedController {
    @GetMapping("/user/hi")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String sayHi() {
        return "Hi man";
    }

    @GetMapping("/admin/hi")
    @PreAuthorize("hasRole('ADMIN')")
    public String sayHiToAdmin() {
        return "Hi Admin";
    }

    @GetMapping("/read/A")
    @PreAuthorize("hasAuthority('READ_A')")
    public String readA() {
        return "Reading A";
    }

    @GetMapping("/read/B")
    @PreAuthorize("hasAuthority('READ_B')")
    public String readB() {
        return "Reading B";
    }
}

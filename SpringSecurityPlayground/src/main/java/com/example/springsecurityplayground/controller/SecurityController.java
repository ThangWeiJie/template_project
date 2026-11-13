package com.example.springsecurityplayground.controller;

import com.example.springsecurityplayground.config.JWTConfiguration;
import com.example.springsecurityplayground.dto.LoginRequest;
import com.example.springsecurityplayground.dto.LoginResponse;
import com.example.springsecurityplayground.dto.UserDTO;
import com.example.springsecurityplayground.service.SecurityService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/")
class SecurityController {

    private final JwtEncoder jwtEncoder;
    private final SecurityService securityService;

    @Autowired
    SecurityController(JwtEncoder jwtEncoder, SecurityService securityService) {
        this.jwtEncoder = jwtEncoder;
        this.securityService = securityService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Authentication authentication = securityService.login(loginRequest.username(), loginRequest.password());

            // Collect all authorities (permissions) of the user
            String scope = authentication
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));

            // Add the claims for the JWT token
            Instant now = Instant.now();
            JwtClaimsSet claimsSet = JwtClaimsSet
                    .builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(3600))
                    .subject(authentication.getName())
                    .claim(JWTConfiguration.ClaimsName, scope)
                    .build();

            // Sign the JWT token
            String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

            // Send the JWT token as a cookie
            ResponseCookie cookie = ResponseCookie.from(JWTConfiguration.TokenName, token)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(3600)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(new LoginResponse("Login successful"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Login unsuccessful"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        String currentName = authentication.getName();

        String[] authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);

        return ResponseEntity.ok(new UserDTO(currentName, authorities));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from(JWTConfiguration.TokenName, "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}

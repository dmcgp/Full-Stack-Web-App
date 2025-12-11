package com.example.taskmanager.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

record LoginRequest(@Email String email, @NotBlank String password) {}
record RegisterRequest(@Email String email, @NotBlank String password, Role role) {}
record TokenResponse(String accessToken, String refreshToken) {}

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
        return authService.authenticate(req.email(), req.password())
                .map(u -> ResponseEntity.ok(new TokenResponse(jwtService.generateAccessToken(u), jwtService.generateRefreshToken(u))))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) {
        try {
            authService.register(req.email(), req.password(), req.role());
            return ResponseEntity.ok("registered");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestParam("refreshToken") String refreshToken) {
        try {
            var claims = jwtService.validateAndParse(refreshToken);
            if (!"refresh".equals(claims.get("type"))) {
                return ResponseEntity.status(400).build();
            }
                var email = claims.getSubject();
                var userOpt = authService.findByEmail(email);
                // Since we don't have the password, just load user by email
            // A simple approach: issue new access token if user exists
            // In a more advanced setup, we'd track refresh tokens in DB.
            return userOpt
                    .map(u -> ResponseEntity.ok(new TokenResponse(jwtService.generateAccessToken(u), refreshToken)))
                    .orElseGet(() -> ResponseEntity.status(401).build());
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
}

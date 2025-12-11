package com.example.taskmanager.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String email, String rawPassword, Role role) {
        if (users.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }
        User u = new User();
        u.setEmail(email);
        u.setPasswordHash(passwordEncoder.encode(rawPassword));
        u.setRole(role == null ? Role.USER : role);
        return users.save(u);
    }

    public Optional<User> authenticate(String email, String rawPassword) {
        return users.findByEmail(email)
                .filter(u -> passwordEncoder.matches(rawPassword, u.getPasswordHash()));
    }

    public Optional<User> findByEmail(String email) {
        return users.findByEmail(email);
    }
}

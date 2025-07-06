package com.ecom_server.server.controller;

import com.ecom_server.server.model.User;
import com.ecom_server.server.service.AuthService;
import com.ecom_server.server.service.helper.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            String message = authService.register(user.getUserName(), user.getEmail(), user.getPassword());
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", message
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser, HttpServletResponse response) {
        try {
            User user = authService.login(loginUser.getEmail(), loginUser.getPassword(), response);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Logged in successfully",
                    "user", user
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Logged out successfully!"
        ));
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuth(HttpServletRequest request) {
        try {
            Optional<Cookie> tokenCookie = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("token"))
                    .findFirst();

            if (tokenCookie.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "success", false
                ));
            }

            Claims claims = jwtUtil.validateToken(tokenCookie.get().getValue());

            // Rebuild user from claims
            User user = User.builder()
                    .id(claims.get("id", String.class))
                    .email(claims.getSubject())
                    .userName(claims.get("userName", String.class))
                    .role(claims.get("role", String.class))
                    .build();

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "user", user
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "success", false
            ));
        }
    }
}

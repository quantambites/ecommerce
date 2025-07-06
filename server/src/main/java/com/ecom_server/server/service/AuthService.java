package com.ecom_server.server.service;

import com.ecom_server.server.model.User;
import com.ecom_server.server.repository.UserRepository;
import com.ecom_server.server.service.helper.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(String userName, String email, String password) throws Exception {
        Optional<User> existing = userRepository.findByEmail(email);
        if (existing.isPresent()) {
            throw new Exception("User already exists!");
        }

        User newUser = User.builder()
                .userName(userName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role("user")
                .build();

        userRepository.save(newUser);
        return "Registration successful";
    }

    public User login(String email, String password, HttpServletResponse response) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getRole(), user.getEmail(), user.getUserName());
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return user;
    }

    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

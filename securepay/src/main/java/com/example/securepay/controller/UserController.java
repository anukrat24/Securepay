package com.example.securepay.controller;

import com.example.securepay.Dto.UserDTO;
import com.example.securepay.Dto.LoginDTO;
import com.example.securepay.Dto.AuthResponse;
import com.example.securepay.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody UserDTO userDTO) {
        AuthResponse response = userService.registerUser(userDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        AuthResponse authResponse = userService.loginUser(loginDTO);

        if (authResponse.getToken() != null) {
            Cookie jwtCookie = new Cookie("jwt", authResponse.getToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); // 1 day
            response.addCookie(jwtCookie);
        }

        return ResponseEntity.ok(authResponse);
    }
}

package com.example.securepay.Service;

import com.example.securepay.Dto.UserDTO;
import com.example.securepay.Dto.LoginDTO;
import com.example.securepay.Dto.AuthResponse;
import com.example.securepay.Entity.User;
import com.example.securepay.Repository.UserRepository;
import com.example.securepay.Util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthResponse registerUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return new AuthResponse("Email already exists", null);
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("USER"); // Default role
        userRepository.save(user);

        return new AuthResponse("User registered successfully.", null);
    }

    @Override
    public AuthResponse loginUser(LoginDTO loginDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());

        if (optionalUser.isEmpty()) {
            return new AuthResponse("User Not Found", null);
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPasswordHash())) {
            return new AuthResponse("Invalid Password", null);
        }

        // Step 1: Send OTP if not yet sent
        if (loginDTO.getOtp() == null || loginDTO.getOtp().isEmpty()) {
            String otp = generateOtp();
            user.setOtpCode(otp);
            user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
            userRepository.save(user);
            emailService.sendOtpEmail(user.getEmail(), otp);
            return new AuthResponse("OTP sent to your email. Please login again with OTP.", null);
        }

        // Step 2: OTP Expiry Check
        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            return new AuthResponse("OTP Expired. Please login again to receive new OTP.", null);
        }

        // Step 3: OTP Match
        if (!user.getOtpCode().equals(loginDTO.getOtp())) {
            return new AuthResponse("Invalid OTP.", null);
        }

        // ✅ Step 4: Generate token with roles
        String role = user.getRole().toUpperCase(); // "ADMIN" or "USER"
        String token = JwtUtil.generateToken(user.getEmail(), List.of("ROLE_" + role));

        System.out.println("User logged in with role: " + role);
        System.out.println("Generated JWT: " + token);

        return new AuthResponse("Login successful", token);
    }

    private String generateOtp() {
        Random random = new Random();
        int otpInt = 100000 + random.nextInt(900000);
        return String.valueOf(otpInt);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

package com.example.securepay.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailService {

    @Autowired//It injects a Spring-managed bean (object) into this class automatically
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}") // fetches from application.properties
    private String fromEmail;

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail); // Uses the configured sender email
        message.setTo(toEmail);     // This is dynamic, comes from user input
        message.setSubject("Your OTP Code");//Sets the subject of the email (what the receiver sees in their inbox).
        message.setText("Your OTP Code is: " + otp + "\n\nThis OTP is valid for 5 minutes.");

        mailSender.send(message);
    }
}

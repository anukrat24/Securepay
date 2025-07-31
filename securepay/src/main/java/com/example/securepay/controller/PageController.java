package com.example.securepay.controller;

import com.example.securepay.Entity.User;
import com.example.securepay.Service.TransactionService;
import com.example.securepay.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;
    private final TransactionService transactionService;

    @GetMapping({"/register-page", "/registerpage"})
    public String registerPage() {
        return "register";
    }

    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            User user = userService.getUserByEmail(email);

            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("role", user.getRole().toUpperCase());

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                model.addAttribute("transactions", transactionService.getAllTransactionsFiltered(null, null, null));
                return "admin-dashboard";
            } else {
                model.addAttribute("transactions", transactionService.getUserTransactions(email));
            }
        }
        return "dashboard";
    }
}

package com.example.securepay.controller;

import com.example.securepay.Dto.TransactionRequestDTO;
import com.example.securepay.Entity.Transaction;
import com.example.securepay.Service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // ✅ Create transaction
    @PostMapping("/transaction")
    public Transaction createTransaction(@RequestBody TransactionRequestDTO request,
                                         Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Unauthenticated. Please login first.");
        }

        String userEmail = principal.getName();
        return transactionService.createTransaction(
                userEmail,
                request.getAmount(),
                request.getDescription(),
                request.getPayMode()
        );
    }

    // ✅ User's own transactions (with optional filters)
    @GetMapping("/transactions")
    public List<Transaction> getUserTransactions(
            Principal principal,
            @RequestParam(required = false) Boolean flagged,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        String userEmail = principal.getName();
        return transactionService.getUserTransactionsFiltered(userEmail, flagged, start, end);
    }

    // ✅ Admin: Get all transactions (with filters)
    @GetMapping("/admin/transactions")
    public List<Transaction> getAllTransactions(
            @RequestParam(required = false) Boolean flagged,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return transactionService.getAllTransactionsFiltered(flagged, start, end);
    }

    // ✅ Admin: Manually flag a transaction
    @PostMapping("/admin/flag")
    public Transaction flagTransaction(@RequestParam Long txnId,
                                       @RequestParam String reason) {
        return transactionService.flagTransactionManually(txnId, reason);
    }

    // ✅ Admin: Delete a transaction
    @DeleteMapping("/admin/transaction/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransactionById(id);
        return "Transaction with ID " + id + " deleted.";
    }
}

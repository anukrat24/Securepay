package com.example.securepay.Repository;

import com.example.securepay.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;//return multiple results.

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserEmailAndTimestampAfter(String email, LocalDateTime after);
    List<Transaction> findByUserEmailAndAmountAndTimestampAfter(String email, Double amount, LocalDateTime after);
    List<Transaction> findByUserEmail(String email);// Gets all transactions made by the given user, regardless of time or amount.
    //Gets the entire transaction history of that user
}

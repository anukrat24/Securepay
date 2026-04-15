package com.example.securepay.Service;

import com.example.securepay.Entity.Transaction;
import com.example.securepay.Repository.TransactionRepository;
import com.example.securepay.Router.PaymentRouter;
import com.example.securepay.Bank.Bank;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final PaymentRouter paymentRouter;

    public Transaction createTransaction(String userEmail,
                                         Double amount,
                                         String description,
                                         String payMode) {

        Transaction transaction = new Transaction();

        transaction.setUserEmail(userEmail);

        transaction.setAmount(amount);

        transaction.setDescription(description);

        transaction.setTimestamp(LocalDateTime.now());

        // fraud detection
        String reason = detectFraud(userEmail, amount);

        if(reason != null){

            transaction.setFlagged(true);

            transaction.setFraudReason(reason);

        }
        else{

            transaction.setFlagged(false);

        }

        // routing logic
        Bank bank = paymentRouter.route(payMode);

        transaction.setPayMode(payMode);

        transaction.setBankName(bank.getBankName());

        return transactionRepository.save(transaction);
    }

    private String detectFraud(String email, Double amount){

        LocalDateTime now = LocalDateTime.now();

        if(amount > 100000){

            return "Amount exceeds ₹100,000";

        }

        List<Transaction> recentTxns =
                transactionRepository
                        .findByUserEmailAndTimestampAfter(
                                email,
                                now.minusMinutes(1)
                        );

        if(recentTxns.size() >= 3){

            return "More than 3 transactions within 1 minute";

        }

        List<Transaction> sameAmountTxns =
                transactionRepository
                        .findByUserEmailAndAmountAndTimestampAfter(
                                email,
                                amount,
                                now.minusMinutes(5)
                        );

        if(sameAmountTxns.size() >= 3){

            return "Same amount used 3+ times in 5 minutes";

        }

        return null;
    }

    public List<Transaction> getUserTransactions(String userEmail){

        return transactionRepository.findByUserEmail(userEmail);

    }

    public List<Transaction> getUserTransactionsFiltered(
            String userEmail,
            Boolean flagged,
            LocalDate start,
            LocalDate end){

        List<Transaction> allTransactions =
                transactionRepository.findByUserEmail(userEmail);

        return allTransactions.stream()

                .filter(txn -> {

                    boolean matches = true;

                    if(flagged != null){

                        matches =
                                matches &&
                                        flagged.equals(txn.isFlagged());

                    }

                    if(start != null){

                        matches =
                                matches &&
                                        !txn.getTimestamp()
                                                .toLocalDate()
                                                .isBefore(start);

                    }

                    if(end != null){

                        matches =
                                matches &&
                                        !txn.getTimestamp()
                                                .toLocalDate()
                                                .isAfter(end);

                    }

                    return matches;

                })

                .collect(Collectors.toList());
    }

    public List<Transaction> getAllTransactionsFiltered(
            Boolean flagged,
            LocalDate start,
            LocalDate end){

        List<Transaction> allTransactions =
                transactionRepository.findAll();

        return allTransactions.stream()

                .filter(txn -> {

                    boolean matches = true;

                    if(flagged != null){

                        matches =
                                matches &&
                                        flagged.equals(txn.isFlagged());

                    }

                    if(start != null){

                        matches =
                                matches &&
                                        !txn.getTimestamp()
                                                .toLocalDate()
                                                .isBefore(start);

                    }

                    if(end != null){

                        matches =
                                matches &&
                                        !txn.getTimestamp()
                                                .toLocalDate()
                                                .isAfter(end);

                    }

                    return matches;

                })

                .collect(Collectors.toList());
    }

    public Transaction flagTransactionManually(Long id, String reason){

        Transaction txn =
                transactionRepository.findById(id)

                        .orElseThrow(() ->
                                new RuntimeException("Transaction not found"));

        txn.setFlagged(true);

        txn.setFraudReason(reason);

        return transactionRepository.save(txn);
    }

    public void deleteTransactionById(Long id){

        if(!transactionRepository.existsById(id)){

            throw new RuntimeException("Transaction not found");

        }

        transactionRepository.deleteById(id);
    }
}
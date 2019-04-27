package br.com.augustolucianetti.microservicostbfinal.repository;

import br.com.augustolucianetti.microservicostbfinal.model.Transaction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TransactionRepository {

    private Map<Long, Double> transactions = new HashMap<>();

    public void addTransaction(Double amount, Long timestamp) {
        transactions.put(timestamp, amount);
    }

    public Map getTransactions() {
        return transactions;
    }
}

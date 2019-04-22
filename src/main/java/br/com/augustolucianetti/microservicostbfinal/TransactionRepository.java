package br.com.augustolucianetti.microservicostbfinal;

import br.com.augustolucianetti.microservicostbfinal.model.Transaction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class TransactionRepository {

    private Map<Long, Double> transactions = new HashMap<>();

    public void addTransaction(Double amount, Long timestamp) {
        transactions.put(timestamp, amount);
    }
}

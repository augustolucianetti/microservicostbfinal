package br.com.augustolucianetti.microservicostbfinal.restcontroller;

import br.com.augustolucianetti.microservicostbfinal.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {

    @Autowired
    TransactionRepository transactionRepository;

    @PostMapping("/transactions")
    public ResponseEntity transactions(@RequestParam("timestamp") Long timestamp, @RequestParam("amount") Double amount) {

        Long systemTimestamp = System.currentTimeMillis();
        System.out.println("SystemTimestamp: " + systemTimestamp );
        Long difference = systemTimestamp - timestamp;
        if (difference.compareTo( 60000l ) == -1 || difference.compareTo( 60000l ) == 0) {
            return new ResponseEntity( HttpStatus.NO_CONTENT );
        }
        transactionRepository.addTransaction(amount, timestamp);
        return new ResponseEntity( HttpStatus.CREATED );
    }
}

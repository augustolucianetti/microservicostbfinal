package br.com.augustolucianetti.microservicostbfinal.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {

    @PostMapping("/transactions")
    public ResponseEntity transactions(@RequestParam("timestamp") Long timestamp, @RequestParam("amount") Double amount) {

        Long systemTimestamp = System.currentTimeMillis();
        System.out.println("SystemTimestamp: " + systemTimestamp );
        Long difference = systemTimestamp - timestamp;
        if (difference.compareTo( 60000l ) == -1 || difference.compareTo( 60000l ) == 0) {
            return new ResponseEntity( HttpStatus.NO_CONTENT );
        }
        return new ResponseEntity( HttpStatus.CREATED );
    }
}

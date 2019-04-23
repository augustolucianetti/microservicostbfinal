package br.com.augustolucianetti.microservicostbfinal.restcontroller;

import br.com.augustolucianetti.microservicostbfinal.exceptions.BadRequestException;
import br.com.augustolucianetti.microservicostbfinal.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BadRequestException badRequestException;

    @PostMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transactions(@RequestParam("timestamp") Long timestamp, @RequestParam("amount") Double amount) {

        if (timestamp == null) {
            ResponseEntity responseEntity = badRequestException.handleMissingParams( new MissingServletRequestParameterException( "timestamp", "Long" ) );
            return responseEntity;
        }

        if (amount == null) {
            return badRequestException.handleMissingParams( new MissingServletRequestParameterException( "amount", "Double" ) );
        }

        Long systemTimestamp = System.currentTimeMillis();
        System.out.println("SystemTimestamp: " + systemTimestamp );
        Long difference = systemTimestamp - timestamp;
        if (difference.compareTo( 60000l ) == 1 || difference.compareTo( 60000l ) == 0) {
            return new ResponseEntity( HttpStatus.NO_CONTENT );
        }
        transactionRepository.addTransaction(amount, timestamp);
        return new ResponseEntity( HttpStatus.CREATED );
    }
}

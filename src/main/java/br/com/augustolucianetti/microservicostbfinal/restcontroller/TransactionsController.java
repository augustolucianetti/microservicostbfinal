package br.com.augustolucianetti.microservicostbfinal.restcontroller;

import br.com.augustolucianetti.microservicostbfinal.exceptions.BadRequestException;
import br.com.augustolucianetti.microservicostbfinal.exceptions.BusinessException;
import br.com.augustolucianetti.microservicostbfinal.exceptions.NotFoundException;
import br.com.augustolucianetti.microservicostbfinal.model.Statistic;
import br.com.augustolucianetti.microservicostbfinal.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TransactionsController {

    @Autowired
    TransactionRepository transactionRepository;



    @PostMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transactions(@RequestParam("timestamp") Long timestamp, @RequestParam("amount") Double amount) throws BadRequestException, BusinessException {

        if (timestamp == null) {
            throw new BusinessException("error.business.exception.enter.atributes","O atributo timestamp não pode ser nulo", "timestamp");
            //ResponseEntity responseEntity = badRequestException.handleMissingParams( new MissingServletRequestParameterException( "timestamp", "Long" ) );
            //return responseEntity;
        }

        if (amount == null) {
            throw new BusinessException("error.business.exception.enter.atributes","O atributo amount não pode ser nulo", "amount");
        }

        Long systemTimestamp = System.currentTimeMillis();
        System.out.println("SystemTimestamp: " + systemTimestamp );
        Long difference = systemTimestamp - timestamp;
        if (difference.compareTo( 60000l ) == 1) {
            return new ResponseEntity( HttpStatus.NO_CONTENT );
        }
        transactionRepository.addTransaction(amount, timestamp);
        return new ResponseEntity( HttpStatus.CREATED );
    }

    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity statistics() {


        Map map = transactionRepository.getTransactions();
        if (map == null || map.size() == 0) {
            throw new NotFoundException( "Nenhuma transacão foi processada nos últimos sessenta segundos" );
        }
        Statistic statistic = new Statistic();
        
        statistic.setCount( map.values().stream().count() );
        statistic.setMax( map.values().stream()
                .mapToDouble( value -> (double) value ).max().orElse( Double.NaN ));
        statistic.setMin( map.values().stream()
                .mapToDouble( value -> (double) value ).min().orElse( Double.NaN ) );
        statistic.setAvg( map.values().stream()
                .mapToDouble( value -> (double) value ).average().orElse( Double.NaN ) );
        statistic.setCount( map.values().stream().count() );
        statistic.setSum( map.values().stream().mapToDouble( value -> (double) value ).sum() );

        return new ResponseEntity( statistic,HttpStatus.OK );
    }
}

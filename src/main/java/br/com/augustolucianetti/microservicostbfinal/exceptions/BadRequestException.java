package br.com.augustolucianetti.microservicostbfinal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadRequestException extends Exception{

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        System.out.println(name + " parameter is missing");
        return new ResponseEntity<ApiError>( new ApiError( HttpStatus.BAD_REQUEST, "O parâmetro ".concat( name ).concat( " é obrigatório" )), HttpStatus.BAD_REQUEST );
    }
}

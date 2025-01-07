package com.santander.flavio.api.adapters.exceptions.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.santander.flavio.api.adapters.exceptions.ApplicationException;

@ControllerAdvice
public class ApplicationExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

   // Tratamento de erros da aplicacao
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationExceptionResponse> handleApplicationException(ApplicationException ex) {
        logger.error(ex.getMessage(), ex);
        ApplicationExceptionResponse errorResponse = new ApplicationExceptionResponse(ex.getMessage(), ex.getStatusCode().value());
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

   // Tratamento de erros de campos invalidos
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ApplicationExceptionResponse> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
       logger.error(ex.getMessage(), ex);
       StringBuilder message = new StringBuilder();
       ex.getFieldErrors().forEach(error -> {
            message.append(String.format("[campo: %s , erro:%s]", error.getField(), error.getDefaultMessage()));
       });
       ApplicationExceptionResponse errorResponse = new ApplicationExceptionResponse(message.toString(), HttpStatus.BAD_REQUEST.value());
       return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
   }

    // Tratamento de erros desconhecidos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApplicationExceptionResponse> handleGeneralException(Exception ex) {
        logger.error("Erro desconhecido, message={}", ex.getMessage(), ex);
        ApplicationExceptionResponse errorResponse = new ApplicationExceptionResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.example.carBid.seller.sellerserivce.Exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //Global Exception handler for generating and handling apt response for application wide exceptions
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> allExceptions(ApplicationException e, HttpServletRequest request){
        ErrorResponse errResponse = new ErrorResponse(
                e.getErrorCode(),e.getMessage(),e.getHttpStatus().value(),
                e.getHttpStatus().name(),request.getContextPath(),request.getMethod(), LocalDateTime.now());
        log.info("Error Response {} :",errResponse);
        return new ResponseEntity<ErrorResponse>(errResponse, e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> otherException(Exception e, HttpServletRequest request){
        ErrorResponse errResponse = new ErrorResponse(
                e.getLocalizedMessage(),e.getMessage(),404,
                "Forbidden",request.getContextPath(),request.getMethod(), LocalDateTime.now());
        log.info("Error Response {} :",errResponse);
        return new ResponseEntity<ErrorResponse>(errResponse, HttpStatus.NOT_FOUND);
    }

}

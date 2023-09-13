package com.example.carBid.seller.sellerserivce.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationException extends RuntimeException{
    private  String errorCode;
    private  String message;
    private  HttpStatus httpStatus;
}

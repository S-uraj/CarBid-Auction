package com.example.carBid.buyer.buyerserivce.Exception;


import java.time.LocalDateTime;


public record ErrorResponse(String errorCode, String message, Integer statusCode, String statusName,
                            String path, String method, LocalDateTime timestamp) {
}


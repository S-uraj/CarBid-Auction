package com.example.carBid.carserivce.Exception;


import java.time.LocalDateTime;


public record ErrorResponse(String errorCode, String message, Integer statusCode, String statusName,
                            String path, String method, LocalDateTime timestamp) {
}


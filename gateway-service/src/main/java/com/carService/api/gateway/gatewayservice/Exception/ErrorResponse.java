package com.carService.api.gateway.gatewayservice.Exception;


import java.time.LocalDateTime;


public record ErrorResponse(String errorCode, String message, Integer statusCode, String statusName,
                            String path, String method, LocalDateTime timestamp) {
}


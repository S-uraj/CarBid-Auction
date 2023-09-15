package com.example.carBid.buyer.buyerserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
    private long bidId;
    private String buyerName;
    private double bidAmount;
    private LocalDateTime bidDateTime;
}


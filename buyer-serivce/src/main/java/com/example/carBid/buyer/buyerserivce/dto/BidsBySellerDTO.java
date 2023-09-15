package com.example.carBid.buyer.buyerserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidsBySellerDTO {
    private long bidId;
    private long carId;
    private String carName;
    private double bidAmount;
    private LocalDateTime bidDateTime;
}

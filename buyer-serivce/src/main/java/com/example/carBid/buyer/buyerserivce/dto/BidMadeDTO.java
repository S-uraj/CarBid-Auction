package com.example.carBid.buyer.buyerserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidMadeDTO {
    private double bidAmount;
    private String buyerUserName;
    private long carId;
}

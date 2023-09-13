package com.example.carBid.carserivce.dto;

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

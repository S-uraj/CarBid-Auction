package com.example.carBid.seller.sellerserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidDTO {
    private long buyerId;
    private String buyerName;
    private String buyerEmail;
    private double bidAmount;
    private LocalDateTime bidDateTime;
}

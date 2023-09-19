package com.example.carBid.seller.sellerserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoldCarDetail {
    private String name;
    private double minimumBidAmount;
    private LocalDateTime listedDateTime;
    private SellerDTO seller;
    private double bidAmount;
    private String buyerName;
}

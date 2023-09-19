package com.example.carBid.buyer.buyerserivce.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SoldCarDetail {
    private String name;
    private double minimumBidAmount;
    private LocalDateTime listedDateTime;
    private SellerDTO seller;
    private double bidAmount;
    private String buyerName;
}

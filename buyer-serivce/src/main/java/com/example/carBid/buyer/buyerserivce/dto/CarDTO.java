package com.example.carBid.buyer.buyerserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
    private String name;
    private String category;
    private  String color;
    private String ownerType;
    private String modelYear;
    private String transmissionType;
    private double minimumBidAmount;
    private LocalDateTime listedDateTime;
    private SellerDTO seller;
}

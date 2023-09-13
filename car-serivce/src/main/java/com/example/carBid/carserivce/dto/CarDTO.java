package com.example.carBid.carserivce.dto;

import com.example.carBid.carserivce.entity.Bid;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CarDTO {
    private long carId;
    private String name;
    private String category;
    private String model;
    private  String color;
    private String ownerType;
    private double minimumBidAmount;
    private LocalDateTime listedDateTime;
    private SellerDTO seller;
    private List<Bid> bidsMade;

}

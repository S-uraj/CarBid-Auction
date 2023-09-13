package com.example.carBid.seller.sellerserivce.dto;



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
    private String model;
    private  String color;
    private String ownerType;
    private double minimumBidAmount;
    private LocalDateTime listedDateTime;
    private List<BidDTO> bidsMade;
}

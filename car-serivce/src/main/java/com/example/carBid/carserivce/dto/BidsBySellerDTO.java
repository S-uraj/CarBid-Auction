package com.example.carBid.carserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidsBySellerDTO {
    private long bidId;
    private long carId;
    private String carName;
    private double bidAmount;
    private LocalDateTime bidDateTime;
}

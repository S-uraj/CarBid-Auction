package com.example.carBid.carserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidRequestDTO {
    private BidMadeDTO bidMadeDTO;
    private BuyerDTO buyerDTO;
}

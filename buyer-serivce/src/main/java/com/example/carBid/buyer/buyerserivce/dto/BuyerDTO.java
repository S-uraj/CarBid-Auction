package com.example.carBid.buyer.buyerserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerDTO {
    private long id;
    private String userName;
    private String buyerEmail;
    private String buyerName;
}

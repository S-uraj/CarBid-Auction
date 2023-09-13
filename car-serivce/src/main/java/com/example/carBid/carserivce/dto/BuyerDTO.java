package com.example.carBid.carserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerDTO {
    private long id;
    private String buyerEmail;
    private String buyerName;
}

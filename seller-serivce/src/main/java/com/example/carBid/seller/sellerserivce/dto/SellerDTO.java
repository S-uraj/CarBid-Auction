package com.example.carBid.seller.sellerserivce.dto;

import com.example.carBid.seller.sellerserivce.entity.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO extends Seller {
    private long id;
    private String userName;
    private String name;
    private String lastName;
    private String email;
    private String phone;
}

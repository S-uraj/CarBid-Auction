package com.example.carBid.seller.sellerserivce.models;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {

    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String message;
}

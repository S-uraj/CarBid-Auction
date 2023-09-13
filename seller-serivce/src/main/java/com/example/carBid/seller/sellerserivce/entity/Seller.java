package com.example.carBid.seller.sellerserivce.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Seller")
public class Seller  {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userName;
    private String name;
    private String lastName;
    @Column(unique = true)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String phone;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  String role;
}

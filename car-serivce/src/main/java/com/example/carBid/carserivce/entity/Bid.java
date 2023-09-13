package com.example.carBid.carserivce.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "Bids")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "bidId")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bidId;

    @ManyToOne
    @JoinColumn(name = "fk_car")
    @JsonIgnore
    private Car car;

    private long buyerId;
    private String buyerName;

    private String buyerEmail;

    private double bidAmount;
    private LocalDateTime bidDateTime;

     public Bid(Car car, long buyerId, String buyerName, String buyerEmail, double bidAmount, LocalDateTime bidDateTime){
         this.car=car;
         this.buyerId = buyerId;
         this.buyerName = buyerName;
         this.buyerEmail = buyerEmail;
         this.bidAmount = bidAmount;
         this.bidDateTime = bidDateTime;
     }
}

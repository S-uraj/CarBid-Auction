package com.example.carBid.carserivce.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Car")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "carId")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carId;
    private String name;
    private  String color;
    private String ownerType;
    private String category;
    private String modelYear;
    private String transmissionType;
    private double minimumBidAmount;
    private LocalDateTime listedDateTime;
    private String status;
    private long sellerId;
    private long buyerId;
    @OneToMany(mappedBy = "car")
    private List<Bid> bidsMade;
    @OneToMany(mappedBy = "car")
    private List<Comment>  comments;

}

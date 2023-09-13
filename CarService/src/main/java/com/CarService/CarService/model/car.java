package com.CarService.CarService.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Car")
public class car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carId;
    private String name;
    private String color;
    private String model;
    private String SellingPrice;
    private String kilometer;
    private int ownerType;
    private String company;
    private LocalDateTime listedDateTime;



}

package com.example.carBid.buyer.buyerserivce.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CarDetailsDTO {
    private String name;
    private String category;
    private  String color;
    private String ownerType;
    private long carId;
    private double minimumBidAmount;
    private LocalDateTime listedDateTime;
    private SellerDTO seller;
    private List<CommentDTO> comments;
    private List<Bid> bidsMade;


}

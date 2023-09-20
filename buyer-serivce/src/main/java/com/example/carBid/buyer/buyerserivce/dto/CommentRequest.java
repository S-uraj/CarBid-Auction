package com.example.carBid.buyer.buyerserivce.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentRequest {
    private long carId;
    private String comment;
    private String commentBy;
}

package com.example.carBid.seller.sellerserivce.dto;


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

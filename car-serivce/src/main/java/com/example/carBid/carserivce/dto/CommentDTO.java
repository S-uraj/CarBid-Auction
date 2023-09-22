package com.example.carBid.carserivce.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentDTO {
    private long carId;
    private String comment;
    private String commentBy;

}

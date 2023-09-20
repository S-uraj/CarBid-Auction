package com.example.carBid.carserivce.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "comment")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "commentId")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long commentId;

    @ManyToOne
    @JoinColumn(name = "fk_car")
    @JsonIgnore
    private Car car;

    private String comment;

    private String commentBy;

}

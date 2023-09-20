package com.example.carBid.carserivce.repository;

import com.example.carBid.carserivce.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Long> {
}

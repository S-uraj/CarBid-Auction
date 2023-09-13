package com.example.carBid.seller.sellerserivce.repository;

import com.example.carBid.seller.sellerserivce.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepo extends JpaRepository<Seller,Long> {

    public Optional<Seller> findByUserName(String userName);
}

package com.carbid.auth.authservice.repository;

import com.carbid.auth.authservice.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepo extends JpaRepository<Seller,Long> {
    public Optional<Seller> findByUserName(String userName);
}

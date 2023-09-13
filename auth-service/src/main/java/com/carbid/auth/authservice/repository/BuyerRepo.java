package com.carbid.auth.authservice.repository;

import com.carbid.auth.authservice.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuyerRepo extends JpaRepository<Buyer,Long> {
    public Optional<Buyer> findByUserName(String userName);
}

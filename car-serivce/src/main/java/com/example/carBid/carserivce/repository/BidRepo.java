package com.example.carBid.carserivce.repository;

import com.example.carBid.carserivce.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepo extends JpaRepository<Bid,Long> {

    @Query(value = "Select * from bids where fk_car=:id order by bid_date_time desc", nativeQuery = true)
    public Optional<List<Bid>> fetchBidForCar(@Param("id") long id);

    @Query(value = "Select * from bids where fk_car=:carId and buyer_id=:buyerId", nativeQuery = true)
    public Optional<Bid> bidAlreadyExist(@Param("carId") long carId, @Param("buyerId") long buyerId);

    @Query(value = "Select * from bids where buyer_id=:buyerId", nativeQuery = true)
    public Optional<List<Bid>> fetchAllBidsForBuyer(@Param("buyerId") long buyerId);

    public Bid findBidByBuyerId(long buyerId);


}


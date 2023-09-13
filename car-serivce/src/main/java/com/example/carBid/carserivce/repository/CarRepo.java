package com.example.carBid.carserivce.repository;

import com.example.carBid.carserivce.entity.Car;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepo extends JpaRepository<Car,Long> {

    @Query(value = "Select * from car where seller_id=:id", nativeQuery = true)
    public Optional<List<Car>> fetchCarBySellerId(@Param("id") long id, Pageable pageable);

    @Query(value = "Select * from car where seller_id=:id order by listed_date_time desc", nativeQuery = true)
    public Optional<List<Car>> fetchCarBySellerId(@Param("id") long id);

    @Query(value = "Select * from car ", nativeQuery = true )
    public Optional<List<Car>> findAllPage(Pageable pageable);
}
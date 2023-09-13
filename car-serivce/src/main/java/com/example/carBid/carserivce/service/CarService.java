package com.example.carBid.carserivce.service;


import com.example.carBid.carserivce.dto.*;
import com.example.carBid.carserivce.entity.Car;


import java.util.List;

public interface CarService {
    void addCar(Car carInfo, long sellerId);

    List<Car> fetchAllCarForSeller(long sellerId, int pgNo, int size);

    List<Car> fetchAllCars(int pgNo, int size);

    CarDTO fetchCarDetails(long productId);

    SellerDTO fetchSellerForCar(long id);

    void addBid(BidMadeDTO bidMadeDTO, BuyerDTO buyer);

    List<BidsBySellerDTO> fetchAllBids(long id);

    void deleteCarById(long productId);
}

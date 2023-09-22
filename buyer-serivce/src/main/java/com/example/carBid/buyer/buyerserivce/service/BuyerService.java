package com.example.carBid.buyer.buyerserivce.service;

import com.example.carBid.buyer.buyerserivce.dto.*;
import com.example.carBid.buyer.buyerserivce.entity.Buyer;

import java.util.List;

public interface BuyerService {
    Buyer getBuyerByUserName(String userName);
    List<CarDTO> fetchAllCars(int pgNo, int size);
    CarDetailsDTO fetchCarDetails(long carId);
    String addBid(BidMadeDTO bidBody);
     List<BidsBySellerDTO> fetchAllBids(long id);
     List<CarDTO> fetchCarByTransmissionType(String transmissionType);
    List<CarDTO> fetchCarByModelYear(String modelYear);
     List<CarDTO> fetchCarByCategory(String category);

    List<SoldCarDetail> soldCarList();

    CommentDTO addComment(CommentRequest commentRequest);
}

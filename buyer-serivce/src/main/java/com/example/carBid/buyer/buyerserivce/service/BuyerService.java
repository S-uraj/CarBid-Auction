package com.example.carBid.buyer.buyerserivce.service;

import com.example.carBid.buyer.buyerserivce.dto.BidMadeDTO;
import com.example.carBid.buyer.buyerserivce.dto.BidsBySellerDTO;
import com.example.carBid.buyer.buyerserivce.dto.CarDTO;
import com.example.carBid.buyer.buyerserivce.dto.CarDetailsDTO;
import com.example.carBid.buyer.buyerserivce.entity.Buyer;

import java.util.List;

public interface BuyerService {
    public Buyer getBuyerByUserName(String userName);
    public List<CarDTO> fetchAllCars(int pgNo, int size);
    public CarDetailsDTO fetchCarDetails(long carId);
    public String addBid(BidMadeDTO bidBody);
    public List<BidsBySellerDTO> fetchAllBids(long id);

    public List<CarDTO> fetchCarByTransmissionType(String transmissionType);
    public List<CarDTO> fetchCarByModelYear(String modelYear);
    public List<CarDTO> fetchCarByCategory(String category);

}

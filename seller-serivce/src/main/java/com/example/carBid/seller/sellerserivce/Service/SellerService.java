package com.example.carBid.seller.sellerserivce.Service;


import com.example.carBid.seller.sellerserivce.dto.CarDTO;
import com.example.carBid.seller.sellerserivce.dto.CommentRequest;
import com.example.carBid.seller.sellerserivce.dto.SellerDTO;
import com.example.carBid.seller.sellerserivce.dto.SoldCarDetail;
import com.example.carBid.seller.sellerserivce.entity.Seller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellerService {

    List<CarDTO> fetchAllCarsForSeller(String userName, int pgNo, int size);

    Seller fetchSellerProfile(String userName);

    String addCar(CarDTO carDTO, String email);

    SellerDTO fetchSellerDetails(long id);

    SoldCarDetail sellCar(long carId,long buyerId);
     List<SoldCarDetail> soldCarList();

    CarDTO addComment(CommentRequest commentRequest);



}

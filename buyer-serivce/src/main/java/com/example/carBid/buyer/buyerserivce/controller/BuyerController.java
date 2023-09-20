package com.example.carBid.buyer.buyerserivce.controller;

import com.example.carBid.buyer.buyerserivce.dto.*;
import com.example.carBid.buyer.buyerserivce.entity.Buyer;
import com.example.carBid.buyer.buyerserivce.serviceimpl.BuyerServiceImpl;
import com.example.carBid.seller.sellerserivce.dto.SoldCarDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

    @Autowired
    public BuyerServiceImpl buyerService;


    @GetMapping("/profile")
    public ResponseEntity<Buyer> fetchUserDetails(@RequestParam("userName") String userName) {
        Buyer buyer = buyerService.getBuyerByUserName(userName);
        return new ResponseEntity(buyer, HttpStatus.OK);
    }

    @GetMapping("/fetchCars")
    public ResponseEntity fetchAllCars(@RequestParam("pageNo") int pgNo, @RequestParam("size") int size) {
        List<CarDTO> listOfCar = buyerService.fetchAllCars(pgNo, size);
        if (listOfCar.isEmpty())
            return new ResponseEntity<>("No cars available", HttpStatus.OK);
        return new ResponseEntity(listOfCar, HttpStatus.OK);
    }

    @GetMapping("car/fetchDetails/{id}")
    public ResponseEntity fetchCarDetails(@PathVariable("id") long carId){
        CarDetailsDTO product = buyerService.fetchCarDetails(carId) ;
        return new ResponseEntity(product,HttpStatus.OK);
    }

    @PostMapping("/placeBid")
    public ResponseEntity<String> placeBid(@RequestBody BidMadeDTO bidBody){
        System.out.println(bidBody.toString());
        String response = buyerService.addBid(bidBody);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("fetch/allBids")
    public ResponseEntity<List<BidsBySellerDTO>> fetchAllBids(@RequestParam("buyerId") long id) {
        List<BidsBySellerDTO> bidList = buyerService.fetchAllBids(id);
        return new ResponseEntity(bidList, HttpStatus.OK);
    }

    @GetMapping("/fetchCarByTransmissionType")
    public  ResponseEntity<List<CarDTO>> fetchCarByTransmissionType(@RequestParam("transmissionType") String transmissionType){
        List<CarDTO> cars=buyerService.fetchCarByTransmissionType(transmissionType);
        return new ResponseEntity<>(cars,HttpStatus.OK);
    }

    @GetMapping("/fetchCarByModelYear")
    public  ResponseEntity<List<CarDTO>> fetchCarByModelYear(@RequestParam("modelYear") String modelYear){
        List<CarDTO> cars=buyerService.fetchCarByModelYear(modelYear);
        return new ResponseEntity<>(cars,HttpStatus.OK);
    }
    @GetMapping("/fetchCarByCategory")
    public  ResponseEntity<List<CarDTO>> fetchCarByCategory(@RequestParam("category") String category){
        List<CarDTO> cars=buyerService.fetchCarByCategory(category);
        return new ResponseEntity<>(cars,HttpStatus.OK);
    }

    @GetMapping("/soldCar")
    public ResponseEntity<List<SoldCarDetail>> soldCarList(){
        List<SoldCarDetail> soldCarDetailList=buyerService.soldCarList();
        return new ResponseEntity<>(soldCarDetailList,HttpStatus.OK);
    }

    @PostMapping("/addComment")
    public ResponseEntity addComment(@RequestBody CommentRequest commentRequest){
        CarDetailsDTO car=buyerService.addComment(commentRequest);
        return new ResponseEntity<>(car,HttpStatus.OK);
    }
}


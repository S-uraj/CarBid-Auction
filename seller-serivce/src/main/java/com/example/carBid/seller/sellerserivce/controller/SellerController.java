package com.example.carBid.seller.sellerserivce.controller;

import com.example.carBid.seller.sellerserivce.dto.*;
import com.example.carBid.seller.sellerserivce.serviceimpl.SellerServiceImpl;
import com.example.carBid.seller.sellerserivce.entity.Seller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerServiceImpl sellerServiceImpl;


    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<String>("Connection Working", HttpStatus.OK);
    }


    @GetMapping("/fetchCars")
    public  ResponseEntity fetchAllCarsForSeller(@RequestParam("userName") String userName ,@RequestParam(value = "pageNo", defaultValue ="0") int pgNo, @RequestParam(value = "size", defaultValue = "0") int size) {
        log.info("Inside /fetchCars mapping");
        List<CarDTO> productList = sellerServiceImpl.fetchAllCarsForSeller(userName,pgNo,size);
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }

    @PostMapping("/addCar")
    public ResponseEntity<String> addCar(@RequestBody CarDTO carDTO, @RequestParam("userName") String userName){
                String response = sellerServiceImpl.addCar(carDTO,userName);

                return new ResponseEntity(response,HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity fetchSellerProfile(@RequestParam("userName") String userName) {
        log.info("Inside /profile mapping");
        Seller seller = sellerServiceImpl.fetchSellerProfile(userName);
        return new ResponseEntity(seller,HttpStatus.OK);
    }

    @GetMapping("/fetchDetails")
    public ResponseEntity fetchSellerDetails(@RequestParam("id") long id) {
        log.info("Inside /profile mapping");
        SellerDTO seller = sellerServiceImpl.fetchSellerDetails(id);
        return new ResponseEntity(seller,HttpStatus.OK);
    }

    @PostMapping("/sellCar")
    public ResponseEntity sellCar(@RequestParam("carId") long carId,@RequestParam("buyerId") long buyerId){
        log.info("Inside /sellCar mapping");
        SoldCarDetail soldCarDetail=sellerServiceImpl.sellCar(carId,buyerId);
        return  new ResponseEntity(soldCarDetail,HttpStatus.OK);
    }
    @GetMapping("/soldCar")
    public ResponseEntity<List<SoldCarDetail>> soldCarList(){
        List<SoldCarDetail> soldCarDetailList=sellerServiceImpl.soldCarList();
        return new ResponseEntity<>(soldCarDetailList,HttpStatus.OK);
    }

    @PostMapping("/addComment")
    public ResponseEntity<String> addComment(@RequestBody CommentRequest commentRequest){
       String car=sellerServiceImpl.addComment(commentRequest);
        return new ResponseEntity<>(car,HttpStatus.OK);
    }
}

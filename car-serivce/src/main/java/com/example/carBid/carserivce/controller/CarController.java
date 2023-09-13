package com.example.carBid.carserivce.controller;

import com.example.carBid.carserivce.ServiceImpl.CarServiceImpl;
import com.example.carBid.carserivce.dto.BidRequestDTO;
import com.example.carBid.carserivce.dto.BidsBySellerDTO;
import com.example.carBid.carserivce.dto.CarDTO;
import com.example.carBid.carserivce.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarServiceImpl carService;

    @PostMapping("/add")
    public ResponseEntity<String> addCar(@RequestBody Car carInfo, @RequestParam("sellerId") long id){
        carService.addCar(carInfo,id);
        return new ResponseEntity("Car added successfully", HttpStatus.OK);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity fetchAllCars(@RequestParam("pageNo") int pgNo, @RequestParam("size") int size) {
        List<Car> listOfProd = carService.fetchAllCars(pgNo, size);
        return  new ResponseEntity(listOfProd,HttpStatus.OK);
    }


    @GetMapping("/fetchDetails/{carId}")
    public ResponseEntity fetchCarDetails(@PathVariable("carId") long carId) {
        CarDTO listOfProd = carService.fetchCarDetails(carId);
        return  new ResponseEntity(listOfProd,HttpStatus.OK);
    }

    @PostMapping("/placeBid")
    public ResponseEntity<String> placeBid(@RequestBody BidRequestDTO bidBody){
        System.out.println(bidBody.toString());
        carService.addBid(bidBody.getBidMadeDTO(),bidBody.getBuyerDTO());
        return new ResponseEntity<>("Bid Placed successfully for product "+bidBody.getBidMadeDTO().getCarId(),HttpStatus.OK);
    }

    @GetMapping("/fetchAllForSeller")
    public ResponseEntity fetchProductDetails(@RequestParam("sellerId") long sellerId,@RequestParam("pageNo") int pgNo, @RequestParam("size") int size ) {
        List<Car> products = carService.fetchAllCarForSeller(sellerId, pgNo, size);
        return new ResponseEntity(products, HttpStatus.OK);
    }

    @GetMapping("/fetchAllBids")
    public ResponseEntity fetchAllBids(@RequestParam("buyerId") long id) {
          List<BidsBySellerDTO> bids = carService.fetchAllBids(id);
          return new ResponseEntity(bids,HttpStatus.OK);
        }
}
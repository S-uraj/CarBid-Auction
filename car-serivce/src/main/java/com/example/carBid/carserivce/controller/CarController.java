package com.example.carBid.carserivce.controller;

import com.example.carBid.carserivce.ServiceImpl.CarServiceImpl;
import com.example.carBid.carserivce.dto.*;
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
    public CarServiceImpl carService;

    @PostMapping("/add")
    public ResponseEntity<String> addCar(@RequestBody Car carInfo, @RequestParam("sellerId") long id){
        carService.addCar(carInfo,id);
        return new ResponseEntity("http://localhost:8084/buyer/car/fetchDetails/"+carInfo.getCarId(), HttpStatus.OK);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity fetchAllCars(@RequestParam("pageNo") int pgNo, @RequestParam("size") int size) {
        List<CarDTO> listOfProd = carService.fetchAllCars(pgNo, size);
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
        return new ResponseEntity<>("Bid Placed successfully for car "+bidBody.getBidMadeDTO().getCarId(),HttpStatus.OK);
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

    @GetMapping("/fetchCarByTransmissionType")
    public  ResponseEntity<List<CarDTO>> fetchCarByTransmissionType(@RequestParam("transmissionType") String transmissionType){
        List<CarDTO> cars=carService.fetchCarByTransmissionType(transmissionType);
        return new ResponseEntity<>(cars,HttpStatus.OK);
    }

    @GetMapping("/fetchCarByModelYear")
    public  ResponseEntity<List<CarDTO>> fetchCarByModelYear(@RequestParam("modelYear") String modelYear){
        List<CarDTO> cars=carService.fetchCarByModelYear(modelYear);
        return new ResponseEntity<>(cars,HttpStatus.OK);
    }

    @GetMapping("/fetchCarByCategory")
    public  ResponseEntity<List<CarDTO>> fetchCarByCategory(@RequestParam("category") String category){
        List<CarDTO> cars=carService.fetchCarByModelYear(category);
        return new ResponseEntity<>(cars,HttpStatus.OK);
    }

    @PostMapping("/sellCar")
    public ResponseEntity<SoldCarDetail> sellCar(@RequestBody SellRequest sellRequest){
        SoldCarDetail soldCarDetail=carService.sellCar(sellRequest);
        return new ResponseEntity<>(soldCarDetail,HttpStatus.OK);
    }

    @GetMapping("/soldCar")
    public ResponseEntity<List<SoldCarDetail>> soldCarList(){
        List<SoldCarDetail> soldCarList=carService.soldCarList();
        return  new ResponseEntity<>(soldCarList,HttpStatus.OK);
    }

    @PostMapping("/addComment")
    public ResponseEntity<CarDTO> addComment(@RequestBody CommentRequest commentRequest){
        CarDTO car=carService.addComment(commentRequest);
        return new ResponseEntity<>(car,HttpStatus.OK);

    }
}
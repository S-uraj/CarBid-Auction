package com.example.carBid.carserivce.ServiceImpl;

import com.example.carBid.carserivce.dto.*;
import com.example.carBid.carserivce.entity.Bid;
import com.example.carBid.carserivce.repository.BidRepo;
import com.example.carBid.carserivce.repository.CarRepo;
import com.example.carBid.carserivce.entity.Car;
import com.example.carBid.carserivce.Exception.ApplicationException;
import com.example.carBid.carserivce.service.CarService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private BidRepo bidRepo;


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addCar(Car carInfo, long sellerId){
        log.info("inside addCar function");
//        Seller currSeller = sellerRepo.findByEmail(email).get();
        carInfo.setSellerId(sellerId);
        carRepo.save(carInfo);
    }

    @Override
    public List<Car> fetchAllCarForSeller(long sellerId, int pgNo, int size){
        try {
            log.info("Inside fetchAllCar function");
            Pageable firstPageWithTwoElements = PageRequest.of(pgNo - 1, size, Sort.by("listed_date_time").descending());
            return carRepo.findAllPage(firstPageWithTwoElements).get();
        }catch(Exception e){
            throw new ApplicationException(e.toString(),e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<Car> fetchAllCars(int pgNo,int size) {
        try {
            log.info("Inside fetchAllCars function");
            Pageable firstPageWithTwoElements = PageRequest.of(pgNo - 1, size, Sort.by("listed_date_time").descending());
            return carRepo.findAllPage(firstPageWithTwoElements).get();
        }catch(Exception e){
            throw new ApplicationException(e.toString(),e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    public SellerDTO fetchSellerForCar(long id){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id",id);
        SellerDTO sellerDTO = restTemplate.exchange("http://localhost:8081/seller/fetchDetails?id="+id,
                HttpMethod.GET, entity, new ParameterizedTypeReference<SellerDTO>(){}).getBody();
        return  sellerDTO;
    }

    @Override
    public CarDTO fetchCarDetails(long carId){
        log.info("Inside fetchcarDetails function");
       Car car= carRepo.findById(carId).get();
        SellerDTO sellerDTO = fetchSellerForCar(car.getSellerId());
        System.out.println(sellerDTO.toString());
        List<Bid> bids = bidRepo.fetchBidForCar(carId).get();
        CarDTO carDTO = CarDTO.builder().carId(carId).name(car.getName())
                .model(car.getModel()).color(car.getColor()).ownerType(car.getOwnerType())
                .category(car.getCategory()).minimumBidAmount(car.getMinimumBidAmount())
                .listedDateTime(car.getListedDateTime()).seller(sellerDTO).bidsMade(bids).build();
        return  carDTO;
    }



    @Transactional
    public void addBid(BidMadeDTO bidMadeDTO, BuyerDTO buyer){
        Car bidCar = carRepo.getReferenceById(bidMadeDTO.getCarId());
        Optional<Bid> previousBid = bidRepo.bidAlreadyExist(bidMadeDTO.getCarId(),buyer.getId());
        if(!previousBid.isEmpty()){
            bidRepo.deleteById(previousBid.get().getBidId());
        }
        log.info("inside add Bid function");
        Bid bid = Bid.builder().car(bidCar).buyerId(buyer.getId()).buyerEmail(buyer.getBuyerEmail())
                .buyerName(buyer.getBuyerName()).bidAmount(bidMadeDTO.getBidAmount()).bidDateTime(LocalDateTime.now())
                .build();
        List<Bid> bidList = new ArrayList<>();
        bidList = bidCar.getBidsMade();
        bidList.add(bid);
        bidCar.setBidsMade(bidList);
        bidRepo.save(bid);
        carRepo.save(bidCar);
    }

    public List<BidsBySellerDTO> fetchAllBids(long id) {
        try {
            log.info("Inside fetchAllBids function");
            List<Bid> bids = bidRepo.fetchAllBidsForBuyer(id).get();
            List<BidsBySellerDTO> bidBySeller = bids.stream().map(bid -> BidsBySellerDTO.builder().bidId(bid.getBidId())
                    .carName(bid.getCar().getName()).carId(bid.getCar().getCarId()).bidAmount(bid.getBidAmount()).bidDateTime(bid.getBidDateTime()).build()).toList();
            return bidBySeller;
        }catch (Exception e){
            throw new ApplicationException("NO-Bids-placed",e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    public void deleteCarById(long carId) {
        carRepo.deleteById(carId);
    }
}

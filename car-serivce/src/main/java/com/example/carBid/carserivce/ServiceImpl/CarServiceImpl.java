package com.example.carBid.carserivce.ServiceImpl;

import com.example.carBid.carserivce.dto.*;
import com.example.carBid.carserivce.entity.Bid;
import com.example.carBid.carserivce.entity.Comment;
import com.example.carBid.carserivce.repository.BidRepo;
import com.example.carBid.carserivce.repository.CarRepo;
import com.example.carBid.carserivce.entity.Car;
import com.example.carBid.carserivce.Exception.ApplicationException;
import com.example.carBid.carserivce.repository.CommentRepo;
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
    private CommentRepo commentRepo;


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addCar(Car carInfo, long sellerId){
        log.info("inside addCar function");
        carInfo.setStatus("Auctioned");
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
    public List<CarDTO> fetchAllCars(int pgNo,int size) {
        try {
            log.info("Inside fetchAllCars function");
            Pageable firstPageWithTwoElements = PageRequest.of(pgNo - 1, size, Sort.by("listed_date_time").descending());
            List<Car> cars=carRepo.findAllPage(firstPageWithTwoElements).get();

            List<CarDTO> carDTOList = cars.stream().map(car -> CarDTO.builder().carId(car.getCarId()).name(car.getName()).category(car.getCategory()).status(car.getStatus())
                    .ownerType(car.getOwnerType()).transmissionType(car.getTransmissionType()).modelYear(car.getModelYear()).seller( fetchSellerForCar(car.getSellerId())).
                    listedDateTime(car.getListedDateTime()).minimumBidAmount(car.getMinimumBidAmount()).color(car.getColor()).build()).toList();
            return carDTOList;

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
                .color(car.getColor()).ownerType(car.getOwnerType())
                .category(car.getCategory()).minimumBidAmount(car.getMinimumBidAmount())
                .transmissionType(car.getTransmissionType()).modelYear(car.getModelYear())
                .listedDateTime(car.getListedDateTime()).seller(sellerDTO).bidsMade(bids).build();
        return  carDTO;
    }



    @Transactional
    public void addBid(BidMadeDTO bidMadeDTO, BuyerDTO buyer){

        Car bidCar=carRepo.getReferenceById(bidMadeDTO.getCarId());
        List<Bid> previousBids=bidCar.getBidsMade();
        if (bidCar.getStatus()=="Sold"){
            throw new ApplicationException("Car Already Sold", null, HttpStatus.BAD_REQUEST);
        }

        if ( bidMadeDTO.getBidAmount() < bidCar.getMinimumBidAmount()){
            log.info("Please Enter High Bid Amount");
            throw new ApplicationException("Please Enter High Bid Amount", null, HttpStatus.BAD_REQUEST);

        }
        if( !previousBids.isEmpty()) {
            for (Bid previousBid : previousBids) {
                if (bidMadeDTO.getBidAmount() < previousBid.getBidAmount() ) {
                    log.info("Please Enter High Bid Amount");
                    throw new ApplicationException("Please Enter High Bid Amount", null, HttpStatus.BAD_REQUEST);
                }
            }
        }
        Bid bid = Bid.builder().car(bidCar).buyerId(buyer.getId()).buyerEmail(buyer.getBuyerEmail())
                .buyerName(buyer.getBuyerName()).bidAmount(bidMadeDTO.getBidAmount()).bidDateTime(LocalDateTime.now())
                .build();
        List<Bid> bidList = bidCar.getBidsMade();
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

    @Override
    public List<CarDTO> fetchCarByTransmissionType(String transmissionType) {
        try{
            log.info(("Inside fetchCarByTransmissionType function"));
            List<Car> cars=carRepo.findCarByTransmissionType(transmissionType);

            List<CarDTO> carDTOList = cars.stream().map(car -> CarDTO.builder().carId(car.getCarId()).name(car.getName()).category(car.getCategory()).status(car.getStatus())
                    .ownerType(car.getOwnerType()).transmissionType(car.getTransmissionType()).modelYear(car.getModelYear()).seller( fetchSellerForCar(car.getSellerId())).
                    listedDateTime(car.getListedDateTime()).minimumBidAmount(car.getMinimumBidAmount()).color(car.getColor()).build()).toList();
            return carDTOList;

        }catch (Exception e){
            throw  new ApplicationException("no Cars with transmission Type"+transmissionType,e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public List<CarDTO> fetchCarByModelYear(String modelYear) {
        try{
            log.info(("Inside fetchCarByModelYear function"));
            List<Car> cars=carRepo.findCarByModelYear(modelYear);

            List<CarDTO> carDTOList = cars.stream().map(car -> CarDTO.builder().carId(car.getCarId()).name(car.getName()).category(car.getCategory()).status(car.getStatus())
                    .ownerType(car.getOwnerType()).transmissionType(car.getTransmissionType()).modelYear(car.getModelYear()).seller( fetchSellerForCar(car.getSellerId())).
                    listedDateTime(car.getListedDateTime()).minimumBidAmount(car.getMinimumBidAmount()).color(car.getColor()).build()).toList();
            return carDTOList;

        }catch (Exception e){
            throw  new ApplicationException("no Cars with Model Year"+modelYear,e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<CarDTO> fetchCarByCategory(String category) {
        try{
            log.info("Inside fetchCarByCategory function");
            List<Car> cars=carRepo.findCarByCategory(category);

            List<CarDTO> carDTOList = cars.stream().map(car -> CarDTO.builder().carId(car.getCarId()).name(car.getName()).category(car.getCategory()).status(car.getStatus())
                    .ownerType(car.getOwnerType()).transmissionType(car.getTransmissionType()).modelYear(car.getModelYear()).seller( fetchSellerForCar(car.getSellerId())).
                    listedDateTime(car.getListedDateTime()).minimumBidAmount(car.getMinimumBidAmount()).color(car.getColor()).build()).toList();
            return carDTOList;

        }catch (Exception e){
            throw  new ApplicationException("no Cars with category"+category,e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public SoldCarDetail sellCar(SellRequest sellRequest) {
        try{
            log.info("sellCar Function");
            Car car=carRepo.getReferenceById(sellRequest.getCarId());

            SoldCarDetail soldCar=new SoldCarDetail();
            soldCar.setName(car.getName());
            soldCar.setBuyerName(bidRepo.findBidByBuyerId(sellRequest.getBuyerId()).getBuyerName());
            soldCar.setSeller(fetchSellerForCar(car.getSellerId()));
            soldCar.setMinimumBidAmount(car.getMinimumBidAmount());
            soldCar.setBidAmount(bidRepo.findBidByBuyerId(car.getBuyerId()).getBidAmount());
            car.setStatus("Sold");
            car.setBuyerId(sellRequest.getBuyerId());
            carRepo.save(car);
            return soldCar;
        }catch (Exception e){
            throw new ApplicationException("carId not find ",e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<SoldCarDetail> soldCarList() {
        try{
            log.info("Inside SoldCarList");
            List<Car> carList=carRepo.findAll();
            List<SoldCarDetail> soldCarDetailList=new ArrayList<>();
            carList.stream().filter(car -> car.getStatus().equals("Sold")).forEach(car -> {
                SoldCarDetail soldCar=new SoldCarDetail();
                soldCar.setName(car.getName());
                soldCar.setListedDateTime(car.getListedDateTime());
                soldCar.setMinimumBidAmount(car.getMinimumBidAmount());
                soldCar.setBuyerName(bidRepo.findBidByBuyerId(car.getBuyerId()).getBuyerName());
                soldCar.setSeller(fetchSellerForCar(car.getSellerId()));
                soldCar.setBidAmount(bidRepo.findBidByBuyerId(car.getBuyerId()).getBidAmount());
                soldCarDetailList.add(soldCar);
            });
            return soldCarDetailList;
        } catch(Exception e){
            throw  new ApplicationException("No Car Sold Yet",e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public CarDTO addComment(CommentRequest commentRequest) {
        try{
            log.info("Inside addComment");
            Car car=carRepo.getReferenceById(commentRequest.getCarId());
            if (car==null){
                throw new ApplicationException("carId not found","CarId not Found"+commentRequest.getCarId(),HttpStatus.NOT_FOUND);
            }
            if (car.getStatus().equals("Sold")){
                throw new ApplicationException("Car Already Sold cannot add comment","Car Already Sold cannot add comment",HttpStatus.BAD_REQUEST);
            }
            Comment comment= Comment.builder().comment(commentRequest.getComment()).car(car).commentBy(commentRequest.getCommentBy()).build();


            List<Comment> commentList=car.getComments();
            commentList.add(comment);
            CarDTO carDTO=CarDTO.builder().carId(car.getCarId()).status(car.getStatus())
                    .comments(commentList).seller(fetchSellerForCar(car.getSellerId())).color(car.getColor()).category(car.getCategory()).minimumBidAmount(car.getMinimumBidAmount())
                    .listedDateTime(car.getListedDateTime()).transmissionType(car.getTransmissionType()).ownerType(car.getOwnerType()).bidsMade(car.getBidsMade())
                    .modelYear(car.getModelYear()).name(car.getName()).build();

            car.setComments(commentList);
            commentRepo.save(comment);
            carRepo.save(car);
            return carDTO;

        }catch (Exception e){
            throw new ApplicationException("something wrong happpened",e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}

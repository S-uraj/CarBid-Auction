package com.example.carBid.buyer.buyerserivce.serviceimpl;

import com.example.carBid.buyer.buyerserivce.dto.*;
import com.example.carBid.buyer.buyerserivce.service.BuyerService;
import com.example.carBid.buyer.buyerserivce.Exception.ApplicationException;
import com.example.carBid.buyer.buyerserivce.entity.Buyer;
import com.example.carBid.buyer.buyerserivce.repository.BuyerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private BuyerRepo buyerRepo;

    @Autowired
    private RestTemplate restTemplate;

    public Buyer getBuyerByUserName(String userName) {
        try {
            return buyerRepo.findByUserName(userName).get();
        }catch (Exception e){
            throw new ApplicationException("email-not-registered-sign-up",e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    public List<CarDTO> fetchAllCars(int pgNo, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String >(headers);
        Map<String, Integer> uriVariables = new HashMap<>();
        uriVariables.put("pageNo", pgNo);
        uriVariables.put("size", size);
        log.info("http://localhost:8082/car/fetchAll?pageNo="+pgNo+"&size="+size,"url");
       List<CarDTO> products = restTemplate.exchange("http://localhost:8082/car/fetchAll?pageNo="+pgNo+"&size="+size,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<CarDTO>>(){}, uriVariables).getBody();
       return products;
    }

    public CarDetailsDTO fetchCarDetails(long carId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String >(headers);
        log.info("http://localhost:8082/car/fetchDetails/"+carId,"url");
        CarDetailsDTO productDet = restTemplate.exchange("http://localhost:8082/car/fetchDetails/"+carId,
                HttpMethod.GET, entity, new ParameterizedTypeReference<CarDetailsDTO>(){}).getBody();
        return productDet;
    }

    public String addBid(BidMadeDTO bidBody) {
        Buyer buyer = getBuyerByUserName(bidBody.getBuyerUserName());
        System.out.println(buyer.toString());
        BuyerDTO buyerDTO = BuyerDTO.builder().id(buyer.getId()).userName(buyer.getUserName()).buyerEmail(buyer.getEmail()).buyerName(buyer.getName()).build();
        BidRequestDTO bidRequestDTO = BidRequestDTO.builder().bidMadeDTO(bidBody).buyerDTO(buyerDTO)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<BidRequestDTO> entity = new HttpEntity<  >(bidRequestDTO,headers);
        String response = restTemplate.exchange("http://localhost:8082/car/placeBid",
                HttpMethod.POST, entity, String.class).getBody();
        return response;
    }

    public List<BidsBySellerDTO> fetchAllBids(long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String >(headers);
        List<BidsBySellerDTO> bidsBySeller = restTemplate.exchange("http://localhost:8082/car/fetchAllBids?buyerId="+id,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<BidsBySellerDTO>>(){}).getBody();
        return bidsBySeller;
    }

    @Override
    public List<CarDTO> fetchCarByTransmissionType(String transmissionType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String >(headers);
        List<CarDTO> carsList=restTemplate.exchange("http://localhost:8082/car/fetchCarByTransmissionType?transmissionType=" + transmissionType,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<CarDTO>>(){}).getBody();
        return carsList;
    }

    @Override
    public List<CarDTO> fetchCarByModelYear(String modelYear) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String >(headers);
        List<CarDTO> carsList=restTemplate.exchange("http://localhost:8082/car/fetchCarByModelYear?modelYear=" + modelYear,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<CarDTO>>(){}).getBody();
        return carsList;
    }

    @Override
    public List<CarDTO> fetchCarByCategory(String category) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String >(headers);
        List<CarDTO> carsList=restTemplate.exchange("http://localhost:8082/car/fetchCarByCategory?category=" +category,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<CarDTO>>(){}).getBody();
        return carsList;
    }
}

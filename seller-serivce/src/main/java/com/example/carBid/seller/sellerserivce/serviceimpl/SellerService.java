package com.example.carBid.seller.sellerserivce.serviceimpl;


import com.example.carBid.seller.sellerserivce.Exception.ApplicationException;
import com.example.carBid.seller.sellerserivce.dto.CarDTO;
import com.example.carBid.seller.sellerserivce.dto.SellerDTO;
import com.example.carBid.seller.sellerserivce.entity.Seller;
import com.example.carBid.seller.sellerserivce.repository.SellerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SellerService {

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    RestTemplate restTemplate;
    ;

    public List<CarDTO> fetchAllProductsForSeller(String userName, int pgNo, int size) {
        try {
            log.info("Inside fetchAllProducts function");
            Seller seller = sellerRepo.findByUserName(userName).get();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String >(headers);
            Map<String, Integer> uriVariables = new HashMap<>();
            uriVariables.put("pageNo", pgNo);
            uriVariables.put("size", size);
            log.info("http://localhost:8082/product/fetchAllForSeller?sellerId="+seller.getId()+"&pageNo="+pgNo+"&size="+size,"url");
            List<CarDTO> products = restTemplate.exchange("http://localhost:8082/product/fetchAllForSeller?sellerId="+seller.getId()+"&pageNo="+pgNo+"&size="+size,
                    HttpMethod.GET, entity, new ParameterizedTypeReference<List<CarDTO>>(){}, uriVariables).getBody();
            return products;
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage(), e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    public Seller fetchSellerProfile(String userName) {
        try {
            return sellerRepo.findByUserName(userName).get();
        } catch (Exception e) {
            throw new ApplicationException("email-not-registered-sign-up", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public String addproduct(CarDTO productDTO, String email) {
        try {
            long id = sellerRepo.findByUserName(email).get().getId();
            LocalDateTime now = LocalDateTime.now();
            productDTO.setListedDateTime(now);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<CarDTO> entity = new HttpEntity<CarDTO>(productDTO, headers);
            return restTemplate.exchange("http://localhost:8082/car/add?sellerId=" + id,
                    HttpMethod.POST, entity, String.class).getBody();
        }catch (Exception e){
            throw  new ApplicationException(e.getLocalizedMessage(),e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    public SellerDTO fetchSellerDetails(long id) {
        Seller seller =  sellerRepo.findById(id).get();
        SellerDTO sellerDTO = new SellerDTO(seller.getId(),seller.getUserName(),seller.getName(),seller.getLastName(),seller.getEmail(),seller.getPhone());
        return sellerDTO;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Seller reqSeller = fetchSellerProfile(username);
//        if(reqSeller == null) throw new UsernameNotFoundException("Buyer Not registered");
//        return new User(reqSeller.getEmail(), reqSeller.getPassword(),
//                Arrays.asList(new SimpleGrantedAuthority(reqSeller.getRole())));
//    }
}

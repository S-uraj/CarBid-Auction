package com.example.carBid.seller.sellerserivce.Service;

import com.example.carBid.seller.sellerserivce.dto.*;
import com.example.carBid.seller.sellerserivce.entity.Seller;
import com.example.carBid.seller.sellerserivce.repository.SellerRepo;
import com.example.carBid.seller.sellerserivce.Service.SellerService;
import com.example.carBid.seller.sellerserivce.Exception.ApplicationException;
import com.example.carBid.seller.sellerserivce.serviceimpl.SellerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class SellerServiceImplTest {

    @InjectMocks
    private SellerServiceImpl sellerService;

    @Mock
    private SellerRepo sellerRepo;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFetchAllCarsForSeller() {
        // Arrange
        String userName = "testUser";
        int pgNo = 1;
        int size = 10;

        // Create a mock Seller
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setUserName(userName);

        // Create a list of CarDTOs
        List<CarDTO> carDTOList = new ArrayList<>();
        // Add some test data to carDTOList

        // Mock the sellerRepo findByUserName method
        when(sellerRepo.findByUserName(userName)).thenReturn(Optional.of(seller));

        // Create HttpHeaders and HttpEntity for RestTemplate
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Create URI variables for RestTemplate
        Map<String, Integer> uriVariables = new HashMap<>();
        uriVariables.put("pageNo", pgNo);
        uriVariables.put("size", size);

        // Create a mock response for restTemplate.exchange
        ResponseEntity<List<CarDTO>> responseEntity = new ResponseEntity<>(carDTOList, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(entity), eq(new ParameterizedTypeReference<List<CarDTO>>(){}), eq(uriVariables)))
                .thenReturn(responseEntity);

        // Act
        List<CarDTO> result = sellerService.fetchAllCarsForSeller(userName, pgNo, size);

        // Assert
        assertEquals(carDTOList, result);
    }

    @Test
    public void testAddCar() {
        // Arrange
        CarDTO carDTO = new CarDTO();
        String email = "test@test.com";
        long sellerId = 1L;
        String responseMessage = "Car Added Successfully";

        // Create a mock Seller
        Seller seller = new Seller();
        seller.setId(sellerId);

        // Create a LocalDateTime object
        LocalDateTime now = LocalDateTime.now();
        carDTO.setListedDateTime(now);

        // Create HttpHeaders and HttpEntity for RestTemplate
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<CarDTO> entity = new HttpEntity<>(carDTO, headers);

        // Mock the sellerRepo findByUserName method
        when(sellerRepo.findByUserName(email)).thenReturn(Optional.of(seller));

        // Create a mock response for restTemplate.exchange
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseMessage, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), eq(entity), eq(String.class)))
                .thenReturn(responseEntity);

        // Act
        String result = sellerService.addCar(carDTO, email);

        // Assert
        assertEquals(responseMessage, result);
    }

    @Test
    public void testFetchSellerDetails() {
        // Arrange
        long sellerId = 1L;

        // Create a mock Seller
        Seller seller = new Seller();
        seller.setId(sellerId);
        seller.setUserName("testUser");
        seller.setName("John Doe");

        // Mock the sellerRepo findById method
        when(sellerRepo.findById(sellerId)).thenReturn(Optional.of(seller));

        // Act
        SellerDTO result = sellerService.fetchSellerDetails(sellerId);

        // Assert
        assertEquals(seller.getId(), result.getId());
        assertEquals(seller.getUserName(), result.getUserName());
        assertEquals(seller.getName(), result.getName());
    }



    @Test
    public void testAddComment() {
        // Arrange
        CommentRequest commentRequest = new CommentRequest();
        CarDTO carDTO = new CarDTO();
        // Set some properties for commentRequest and carDTO

        // Create HttpHeaders and HttpEntity for RestTemplate
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<CommentRequest> entity = new HttpEntity<>(commentRequest, headers);

        // Create a mock response for restTemplate.exchange
        ResponseEntity<CarDTO> responseEntity = new ResponseEntity<>(carDTO, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), eq(entity), eq(CarDTO.class)))
                .thenReturn(responseEntity);

        // Act
        CarDTO result = sellerService.addComment(commentRequest);

        // Assert
        assertEquals(carDTO, result);
    }


}

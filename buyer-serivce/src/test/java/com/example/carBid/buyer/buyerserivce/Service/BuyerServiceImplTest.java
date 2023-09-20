package com.example.carBid.buyer.buyerserivce.Service;
import com.example.carBid.buyer.buyerserivce.dto.*;
import com.example.carBid.buyer.buyerserivce.entity.Buyer;
import com.example.carBid.buyer.buyerserivce.repository.BuyerRepo;
import com.example.carBid.buyer.buyerserivce.serviceimpl.BuyerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class BuyerServiceImplTest {

    @InjectMocks
    private BuyerServiceImpl buyerService;

    @Mock
    private BuyerRepo buyerRepo;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetBuyerByUserName() {
        // Arrange
        String userName = "testUser";
        Buyer buyer = new Buyer();
        buyer.setId(1L);
        buyer.setUserName(userName);

        Mockito.when(buyerRepo.findByUserName(userName)).thenReturn(Optional.of(buyer));

        // Act
        Buyer result = buyerService.getBuyerByUserName(userName);

        // Assert
        assertEquals(userName, result.getUserName());
    }

    @Test
    public void testFetchCarDetails() {
        // Arrange
        long carId = 1L;
        CarDetailsDTO carDetailsDTO = new CarDetailsDTO();
        carDetailsDTO.setCarId(carId);
        carDetailsDTO.setName("Test Car");

        ResponseEntity<CarDetailsDTO> responseEntity = ResponseEntity.ok(carDetailsDTO);
        Mockito.when(restTemplate.exchange(
                eq("http://localhost:8082/car/fetchDetails/1"),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<CarDetailsDTO>() {}))
        ).thenReturn(responseEntity);

        // Act
        CarDetailsDTO result = buyerService.fetchCarDetails(carId);

        // Assert
        assertEquals(carId, result.getCarId());
        assertEquals("Test Car", result.getName());
    }

    @Test
    public void testAddBid() {
        // Arrange
        BidMadeDTO bidBody = new BidMadeDTO();
        bidBody.setBuyerUserName("testBuyer");
        // Add more properties to bidBody as needed

        Buyer buyer = new Buyer();
        buyer.setId(1L);
        buyer.setUserName("testBuyer");
        buyer.setEmail("test@example.com");
        // Add more properties to buyer as needed

        BuyerDTO buyerDTO = BuyerDTO.builder()
                .id(buyer.getId())
                .userName(buyer.getUserName())
                .buyerEmail(buyer.getEmail())
                // Add more properties to buyerDTO as needed
                .build();

        Mockito.when(buyerService.getBuyerByUserName("testBuyer")).thenReturn(buyer);

        ResponseEntity<String> responseEntity = ResponseEntity.ok("Bid placed successfully");
        Mockito.when(restTemplate.exchange(
                eq("http://localhost:8082/car/placeBid"),
                eq(HttpMethod.POST),
                any(),
                eq(String.class))
        ).thenReturn(responseEntity);

        // Act
        String response = buyerService.addBid(bidBody);

        // Assert
        assertEquals("Bid placed successfully", response);
    }

    @Test
    public void testFetchAllBids() {
        // Arrange
        long buyerId = 1L;
        List<BidsBySellerDTO> bidsList = new ArrayList<>();
        // Add some test data to the bidsList

        ResponseEntity<List<BidsBySellerDTO>> responseEntity = ResponseEntity.ok(bidsList);
        Mockito.when(restTemplate.exchange(
                eq("http://localhost:8082/car/fetchAllBids?buyerId=1"),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<BidsBySellerDTO>>() {}))
        ).thenReturn(responseEntity);

        // Act
        List<BidsBySellerDTO> result = buyerService.fetchAllBids(buyerId);

        // Assert
        assertEquals(bidsList, result);
    }

    @Test
    public void testFetchCarByTransmissionType() {
        // Arrange
        String transmissionType = "Automatic";
        List<CarDTO> carsList = new ArrayList<>();
        // Add some test data to the carsList

        ResponseEntity<List<CarDTO>> responseEntity = ResponseEntity.ok(carsList);
        Mockito.when(restTemplate.exchange(
                eq("http://localhost:8082/car/fetchCarByTransmissionType?transmissionType=Automatic"),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<CarDTO>>() {}))
        ).thenReturn(responseEntity);

        // Act
        List<CarDTO> result = buyerService.fetchCarByTransmissionType(transmissionType);

        // Assert
        assertEquals(carsList, result);
    }


    @Test
    public void testFetchCarByModelYear() {
        // Arrange
        String modelYear = "2023";
        List<CarDTO> carsList = new ArrayList<>();
        // Add some test data to the carsList

        ResponseEntity<List<CarDTO>> responseEntity = ResponseEntity.ok(carsList);
        Mockito.when(restTemplate.exchange(
                eq("http://localhost:8082/car/fetchCarByModelYear?modelYear=2023"),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<CarDTO>>() {}))
        ).thenReturn(responseEntity);

        // Act
        List<CarDTO> result = buyerService.fetchCarByModelYear(modelYear);

        // Assert
        assertEquals(carsList, result);
    }


    @Test
    public void testAddComment() {
        // Arrange
        CommentRequest commentRequest = new CommentRequest();
        // Add data to commentRequest as needed

        CarDetailsDTO carDetailsDTO = new CarDetailsDTO();
        // Add data to carDetailsDTO as needed

        ResponseEntity<CarDetailsDTO> responseEntity = ResponseEntity.ok(carDetailsDTO);
        Mockito.when(restTemplate.exchange(
                eq("http://localhost:8082/car/addComment"),
                eq(HttpMethod.POST),
                any(),
                eq(CarDetailsDTO.class))
        ).thenReturn(responseEntity);

        // Act
        CarDetailsDTO result = buyerService.addComment(commentRequest);

        // Assert
        assertEquals(carDetailsDTO, result);
    }


}


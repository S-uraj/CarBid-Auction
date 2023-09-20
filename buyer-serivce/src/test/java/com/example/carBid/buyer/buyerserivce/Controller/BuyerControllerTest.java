package com.example.carBid.buyer.buyerserivce.Controller;

import com.example.carBid.buyer.buyerserivce.controller.BuyerController;
import com.example.carBid.buyer.buyerserivce.dto.*;
        import com.example.carBid.buyer.buyerserivce.entity.Buyer;
        import com.example.carBid.buyer.buyerserivce.serviceimpl.BuyerServiceImpl;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.Mock;
        import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;

        import java.util.ArrayList;
        import java.util.List;

        import static org.mockito.Mockito.*;

public class BuyerControllerTest {

    @Autowired
    private BuyerController buyerController;


    @Mock
    private BuyerServiceImpl buyerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        buyerController = new BuyerController();
        buyerController.buyerService = buyerService;
    }

    @Test
    public void testFetchUserDetails() {
        // Arrange
        String userName = "testUser";
        Buyer buyer = new Buyer(); // Create a sample Buyer object
        when(buyerService.getBuyerByUserName(userName)).thenReturn(buyer);

        // Act
        ResponseEntity<Buyer> responseEntity = buyerController.fetchUserDetails(userName);

        // Assert
        verify(buyerService).getBuyerByUserName(userName); // Verify that the service method was called
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == buyer);
    }

    // Unit Test for fetchCarDetails
    @Test
    public void testFetchCarDetails() {
        // Arrange
        long carId = 123L; // Replace with a valid car ID
        CarDetailsDTO carDetailsDTO = new CarDetailsDTO(); // Create a sample CarDetailsDTO
        when(buyerService.fetchCarDetails(carId)).thenReturn(carDetailsDTO);

        // Act
        ResponseEntity<CarDetailsDTO> responseEntity = buyerController.fetchCarDetails(carId);

        // Assert
        verify(buyerService).fetchCarDetails(carId); // Verify that the service method was called
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == carDetailsDTO);
    }

    // Unit Test for placeBid
    @Test
    public void testPlaceBid() {
        // Arrange
        BidMadeDTO bidBody = new BidMadeDTO(); // Create a sample BidMadeDTO
        when(buyerService.addBid(bidBody)).thenReturn("BidPlaced");

        // Act
        ResponseEntity<String> responseEntity = buyerController.placeBid(bidBody);

        // Assert
        verify(buyerService).addBid(bidBody); // Verify that the service method was called
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody().equals("BidPlaced"));
    }

    // Unit Test for fetchAllBids
    @Test
    public void testFetchAllBids() {
        // Arrange
        long buyerId = 456L; // Replace with a valid buyer ID
        List<BidsBySellerDTO> bidList = new ArrayList<>(); // Create a sample list of bids
        when(buyerService.fetchAllBids(buyerId)).thenReturn(bidList);

        // Act
        ResponseEntity<List<BidsBySellerDTO>> responseEntity = buyerController.fetchAllBids(buyerId);

        // Assert
        verify(buyerService).fetchAllBids(buyerId); // Verify that the service method was called
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == bidList);
    }

    // Unit Test for fetchCarByTransmissionType
    @Test
    public void testFetchCarByTransmissionType() {
        // Arrange
        String transmissionType = "Automatic"; // Replace with a valid transmission type
        List<CarDTO> cars = new ArrayList<>(); // Create a sample list of cars
        when(buyerService.fetchCarByTransmissionType(transmissionType)).thenReturn(cars);

        // Act
        ResponseEntity<List<CarDTO>> responseEntity = buyerController.fetchCarByTransmissionType(transmissionType);

        // Assert
        verify(buyerService).fetchCarByTransmissionType(transmissionType); // Verify that the service method was called
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == cars);
    }

    // Unit Test for fetchCarByModelYear
    @Test
    public void testFetchCarByModelYear() {
        // Arrange
        String modelYear = "2022"; // Replace with a valid model year
        List<CarDTO> cars = new ArrayList<>(); // Create a sample list of cars
        when(buyerService.fetchCarByModelYear(modelYear)).thenReturn(cars);

        // Act
        ResponseEntity<List<CarDTO>> responseEntity = buyerController.fetchCarByModelYear(modelYear);

        // Assert
        verify(buyerService).fetchCarByModelYear(modelYear); // Verify that the service method was called
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == cars);
    }

    // Unit Test for fetchCarByCategory
    @Test
    public void testFetchCarByCategory() {
        // Arrange
        String category = "SUV"; // Replace with a valid category
        List<CarDTO> cars = new ArrayList<>(); // Create a sample list of cars
        when(buyerService.fetchCarByCategory(category)).thenReturn(cars);

        // Act
        ResponseEntity<List<CarDTO>> responseEntity = buyerController.fetchCarByCategory(category);

        // Assert
        verify(buyerService).fetchCarByCategory(category); // Verify that the service method was called
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == cars);
    }

}



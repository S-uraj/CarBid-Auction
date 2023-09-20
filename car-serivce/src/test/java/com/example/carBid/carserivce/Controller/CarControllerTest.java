package com.example.carBid.carserivce.Controller;

import com.example.carBid.carserivce.ServiceImpl.CarServiceImpl;
import com.example.carBid.carserivce.controller.CarController;
import com.example.carBid.carserivce.dto.*;
import com.example.carBid.carserivce.entity.Car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CarControllerTest {

    private CarController carController;

    @Mock
    private CarServiceImpl carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        carController = new CarController();
        carController.carService = carService;
    }

    // Unit Test for fetchAllCars
    @Test
    public void testFetchAllCars() {
        // Arrange
        int pageNo = 1;
        int size = 10;
        List<CarDTO> listOfCars = new ArrayList<>(); // Create a sample list of cars
        when(carService.fetchAllCars(pageNo, size)).thenReturn(listOfCars);

        // Act
        ResponseEntity<?> responseEntity = carController.fetchAllCars(pageNo, size);

        // Assert
        verify(carService).fetchAllCars(pageNo, size); // Verify that the service method was called
        assert (responseEntity.getStatusCode() == HttpStatus.OK);
        assert (responseEntity.getBody() == listOfCars);
    }

    // Unit Test for fetchAllBids
    @Test
    public void testFetchAllBids() {
        // Arrange
        long buyerId = 123L; // Replace with a valid buyer ID
        List<BidsBySellerDTO> listOfBids = new ArrayList<>(); // Create a sample list of bids
        when(carService.fetchAllBids(buyerId)).thenReturn(listOfBids);

        // Act
        ResponseEntity<?> responseEntity = carController.fetchAllBids(buyerId);

        // Assert
        verify(carService).fetchAllBids(buyerId); // Verify that the service method was called
        assert (responseEntity.getStatusCode() == HttpStatus.OK);
        assert (responseEntity.getBody() == listOfBids);
    }

    // Unit Test for fetchCarByTransmissionType
    @Test
    public void testFetchCarByTransmissionType() {
        // Arrange
        String transmissionType = "Automatic"; // Replace with a valid transmission type
        List<CarDTO> listOfCars = new ArrayList<>(); // Create a sample list of cars
        when(carService.fetchCarByTransmissionType(transmissionType)).thenReturn(listOfCars);

        // Act
        ResponseEntity<?> responseEntity = carController.fetchCarByTransmissionType(transmissionType);

        // Assert
        verify(carService).fetchCarByTransmissionType(transmissionType); // Verify that the service method was called
        assert (responseEntity.getStatusCode() == HttpStatus.OK);
        assert (responseEntity.getBody() == listOfCars);
    }
    @Test
    public void testFetchCarByModelYear() {
        // Arrange
        String modelYear = "2022"; // Replace with a valid model year
        List<CarDTO> listOfCars = new ArrayList<>(); // Create a sample list of cars
        when(carService.fetchCarByModelYear(modelYear)).thenReturn(listOfCars);

        // Act
        ResponseEntity<?> responseEntity = carController.fetchCarByModelYear(modelYear);

        // Assert
        verify(carService).fetchCarByModelYear(modelYear); // Verify that the service method was called
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == listOfCars);
    }

    // Unit Test for fetchCarByCategory
    @Test
    public void testFetchCarByCategory() {
        // Arrange
        String category = "Sedan"; // Replace with a valid category
        List<CarDTO> listOfCars = new ArrayList<>(); // Create a sample list of cars
        when(carService.fetchCarByModelYear(category)).thenReturn(listOfCars);

        // Act
        ResponseEntity<?> responseEntity = carController.fetchCarByCategory(category);

        // Assert
        verify(carService).fetchCarByModelYear(category); // Verify that the service method was called
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == listOfCars);
    }

    // Unit Test for sellCar
    @Test
    public void testSellCar() {
        // Arrange
        SellRequest sellRequest = new SellRequest(); // Create a sample SellRequest
        SoldCarDetail soldCarDetail = new SoldCarDetail(); // Create a sample SoldCarDetail
        when(carService.sellCar(sellRequest)).thenReturn(soldCarDetail);

        // Act
        ResponseEntity<SoldCarDetail> responseEntity = carController.sellCar(sellRequest);

        // Assert
        verify(carService).sellCar(sellRequest); // Verify that the service method was called
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == soldCarDetail);
    }

    // Unit Test for soldCarList
    @Test
    public void testSoldCarList() {
        // Arrange
        List<SoldCarDetail> soldCarList = new ArrayList<>(); // Create a sample list of sold cars
        when(carService.soldCarList()).thenReturn(soldCarList);

        // Act
        ResponseEntity<List<SoldCarDetail>> responseEntity = carController.soldCarList();

        // Assert
        verify(carService).soldCarList(); // Verify that the service method was called
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody() == soldCarList);
    }



}

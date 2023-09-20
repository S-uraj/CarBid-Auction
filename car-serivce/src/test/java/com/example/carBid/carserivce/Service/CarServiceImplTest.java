package com.example.carBid.carserivce.Service;

import com.example.carBid.carserivce.ServiceImpl.CarServiceImpl;
import com.example.carBid.carserivce.dto.*;
import com.example.carBid.carserivce.entity.Bid;
import com.example.carBid.carserivce.entity.Car;
import com.example.carBid.carserivce.entity.Comment;
import com.example.carBid.carserivce.repository.BidRepo;
import com.example.carBid.carserivce.repository.CarRepo;
import com.example.carBid.carserivce.repository.CommentRepo;
import com.example.carBid.carserivce.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepo carRepo;

    @Mock
    private BidRepo bidRepo;

    @Mock
    private CommentRepo commentRepo;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // Test for addCar
    @Test
    public void testAddCar() {
        // Arrange
        Car carInfo = new Car();
        carInfo.setName("TestCar");
        carInfo.setSellerId(1L);

        // Act
        carService.addCar(carInfo, 1L);

        Mockito.verify(carRepo).save(carInfo);
    }

    @Test
    public void testDeleteCarById() {
        // Arrange
        long carId = 1L;

        // Act
        carService.deleteCarById(carId);

        Mockito.verify(carRepo).deleteById(carId);
    }

    @Test
    public void testFetchCarByTransmissionType() {
        // Arrange
        String transmissionType = "Automatic";
        List<Car> cars = new ArrayList<>();
        // Add some test data to cars

        // Mock the behavior of carRepo.findCarByTransmissionType
        Mockito.when(carRepo.findCarByTransmissionType(transmissionType)).thenReturn(cars);

        // Act
        List<CarDTO> result = carService.fetchCarByTransmissionType(transmissionType);


    }

    @Test
    public void testFetchCarByModelYear() {
        // Arrange
        String modelYear = "2023";
        List<Car> cars = new ArrayList<>();
        // Add some test data to cars

        // Mock the behavior of carRepo.findCarByModelYear
        Mockito.when(carRepo.findCarByModelYear(modelYear)).thenReturn(cars);

        // Act
        List<CarDTO> result = carService.fetchCarByModelYear(modelYear);


    }

    @Test
    public void testFetchCarByCategory() {
        // Arrange
        String category = "Sedan";
        List<Car> cars = new ArrayList<>();
        // Add some test data to cars

        // Mock the behavior of carRepo.findCarByCategory
        Mockito.when(carRepo.findCarByCategory(category)).thenReturn(cars);

        // Act
        List<CarDTO> result = carService.fetchCarByCategory(category);

    }


}

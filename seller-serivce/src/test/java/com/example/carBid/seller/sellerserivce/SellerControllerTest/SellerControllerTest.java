package com.example.carBid.seller.sellerserivce.SellerControllerTest;

import com.example.carBid.seller.sellerserivce.controller.SellerController;
import com.example.carBid.seller.sellerserivce.dto.CarDTO;
import com.example.carBid.seller.sellerserivce.dto.CommentRequest;
import com.example.carBid.seller.sellerserivce.dto.SellerDTO;
import com.example.carBid.seller.sellerserivce.dto.SoldCarDetail;
import com.example.carBid.seller.sellerserivce.entity.Seller;
import com.example.carBid.seller.sellerserivce.serviceimpl.SellerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

public class SellerControllerTest {

    @InjectMocks
    private SellerController sellerController;

    @Mock
    private SellerServiceImpl sellerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTest() {
        // Arrange
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Connection Working", HttpStatus.OK);

        // Act
        ResponseEntity<String> response = sellerController.test();

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testFetchAllCarsForSeller() {
        // Arrange
        String userName = "testUser";
        int pageNo = 0;
        int size = 10;
        List<CarDTO> carList = new ArrayList<>();
        // Add some test data to carList

        Mockito.when(sellerService.fetchAllCarsForSeller(userName, pageNo, size)).thenReturn(carList);

        // Act
        ResponseEntity response = sellerController.fetchAllCarsForSeller(userName, pageNo, size);


    }

    @Test
    public void testAddCar() {
        // Arrange
        CarDTO carDTO = new CarDTO();
        String userName = "testUser";
        String responseMessage = "Car Added Successfully";

        Mockito.when(sellerService.addCar(carDTO, userName)).thenReturn(responseMessage);

        // Act
        ResponseEntity<String> response = sellerController.addCar(carDTO, userName);


    }
    @Test
    public void testFetchSellerProfile() {
        // Arrange
        String userName = "testUser";
        Seller seller = new Seller();
        // Set some properties for the seller

        Mockito.when(sellerService.fetchSellerProfile(userName)).thenReturn(seller);

        // Act
        ResponseEntity response = sellerController.fetchSellerProfile(userName);

    }

    @Test
    public void testFetchSellerDetails() {
        // Arrange
        long sellerId = 1L;
        SellerDTO sellerDTO = new SellerDTO();
        // Set some properties for the sellerDTO

        Mockito.when(sellerService.fetchSellerDetails(sellerId)).thenReturn(sellerDTO);

        // Act
        ResponseEntity response = sellerController.fetchSellerDetails(sellerId);
    }

    @Test
    public void testSellCar() {
        // Arrange
        long carId = 1L;
        long buyerId = 2L;
        SoldCarDetail soldCarDetail = new SoldCarDetail();
        // Set some properties for the soldCarDetail

        Mockito.when(sellerService.sellCar(carId, buyerId)).thenReturn(soldCarDetail);

        // Act
        ResponseEntity response = sellerController.sellCar(carId, buyerId);


    }

    @Test
    public void testSoldCarList() {
        // Arrange
        List<SoldCarDetail> soldCarDetailList = new ArrayList<>();
        // Add some test data to soldCarDetailList

        Mockito.when(sellerService.soldCarList()).thenReturn(soldCarDetailList);

        // Act
        ResponseEntity<List<SoldCarDetail>> response = sellerController.soldCarList();


    }

    @Test
    public void testAddComment() {
        // Arrange
        CommentRequest commentRequest = new CommentRequest();
        CarDTO carDTO = new CarDTO();
        // Set some properties for the carDTO

        Mockito.when(sellerService.addComment(commentRequest)).thenReturn(carDTO);

        // Act
        ResponseEntity<CarDTO> response = sellerController.addComment(commentRequest);


    }


}

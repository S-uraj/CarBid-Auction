package com.carbid.auth.authservice.controller;

import com.carbid.auth.authservice.entity.Buyer;
import com.carbid.auth.authservice.serviceimpl.RegisterServiceImpl;
import com.carbid.auth.authservice.entity.Seller;
import com.carbid.auth.authservice.models.RegisterUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth/register")
public class RegisterController {

   @Autowired
    private RegisterServiceImpl registerService;

   @PostMapping("/seller")
    public ResponseEntity addSeller(@RequestBody Seller seller){
      RegisterUser registerUser = registerService.signUpSeller(seller);
      return new ResponseEntity(registerUser,HttpStatus.OK);
   }

    @PostMapping("/buyer")
    public ResponseEntity addBuyer(@RequestBody Buyer buyer){
        RegisterUser registerUser = registerService.signUpBuyer(buyer);
        return new ResponseEntity(registerUser,HttpStatus.OK);
    }

}

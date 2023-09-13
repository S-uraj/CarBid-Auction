package com.carbid.auth.authservice.serviceimpl;

import com.carbid.auth.authservice.entity.Buyer;
import com.carbid.auth.authservice.repository.SellerRepo;
import com.carbid.auth.authservice.repository.UserDataRepo;
import com.carbid.auth.authservice.Exception.ApplicationException;
import com.carbid.auth.authservice.entity.Seller;
import com.carbid.auth.authservice.models.RegisterUser;
import com.carbid.auth.authservice.repository.BuyerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegisterServiceImpl {

    @Autowired
    private UserDataRepo userDataRepo ;

    @Autowired
    private SellerRepo sellerRepo ;

    @Autowired
    private BuyerRepo buyerRepo ;

    public RegisterUser signUpSeller(Seller newSeller) {
        try {
            log.info("Inside addSeller function");
            log.info("Encrypting the password in transit");
            if(!sellerRepo.findByUserName(newSeller.getEmail()).isEmpty()) throw new Exception("Email Already Registered");
            //using bcrypt encoder to encrypt the password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            newSeller.setPassword(passwordEncoder.encode(newSeller.getPassword()));
            newSeller.setRole("SELLER");
            Seller curr_user = sellerRepo.save(newSeller);
            curr_user.setUserName(curr_user.getName()+curr_user.getId());
            sellerRepo.save(curr_user);
            return new RegisterUser(curr_user.getUserName(),curr_user.getName(), curr_user.getLastName(),
                    curr_user.getEmail(), curr_user.getPhone(), "Seller Registered successfully");
        } catch (Exception e) {
            if(e.getMessage().contains("duplicate key value"))
                throw new ApplicationException("Email Already Registered","Use different Email",HttpStatus.BAD_REQUEST);
            throw new ApplicationException(e.getLocalizedMessage(), e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public RegisterUser signUpBuyer(Buyer newBuyer) {
        try{
            log.info("Inside addBuyer function");
            if(!buyerRepo.findByUserName(newBuyer.getUserName()).isEmpty()) throw new Exception("Email Already Registered");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            newBuyer.setPassword(passwordEncoder.encode(newBuyer.getPassword()));
            newBuyer.setRole("BUYER");
            Buyer curr_user = buyerRepo.save(newBuyer);
            curr_user.setUserName(curr_user.getName()+curr_user.getId());
            buyerRepo.save(newBuyer);
            return new RegisterUser(curr_user.getUserName(),curr_user.getName(), curr_user.getLastName(),
                    curr_user.getEmail(), curr_user.getPhone(), "Buyer Registered successfully");
        }catch (Exception e){
            throw  new ApplicationException(e.getLocalizedMessage(),e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

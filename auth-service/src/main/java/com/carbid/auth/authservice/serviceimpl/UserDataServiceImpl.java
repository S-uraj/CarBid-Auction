package com.carbid.auth.authservice.serviceimpl;

import com.carbid.auth.authservice.repository.UserDataRepo;
import com.carbid.auth.authservice.entity.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDataServiceImpl implements UserDetailsService {

    @Autowired
    private UserDataRepo userDataRepo;

    public UserData getUserByUserName(String username) {
        return userDataRepo.findByUserName(username).get();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData reqUser = userDataRepo.findByUserName(username).get();
        if(reqUser == null) throw new UsernameNotFoundException("Buyer Not registered");
//        List<SimpleGrantedAuthority> authorities = returnRoles(reqUser.getRole());
        return new User(reqUser.getUserName(), reqUser.getPassword(), Arrays.asList(new SimpleGrantedAuthority(reqUser.getRole())));
    }
}

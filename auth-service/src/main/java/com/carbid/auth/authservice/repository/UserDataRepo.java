package com.carbid.auth.authservice.repository;

import com.carbid.auth.authservice.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepo extends JpaRepository<UserData,Long> {

    public Optional<UserData> findByUserName(String userName);
}

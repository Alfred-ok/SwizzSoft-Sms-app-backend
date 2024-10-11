package com.example.SwizzSoft_Sms_app.SecurityAndJwt.repository;

import com.example.SwizzSoft_Sms_app.SecurityAndJwt.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);


}

package com.example.SwizzSoft_Sms_app.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AspNetUsers, String> {

}

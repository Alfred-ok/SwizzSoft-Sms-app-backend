package com.example.SwizzSoft_Sms_app.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository repo;

    @GetMapping("/get_User")
    public ResponseEntity<Object> getUser() {
        return ResponseEntity.ok(repo.findAll());
    }
}

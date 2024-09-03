package com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    private String username;

    private String password;

}

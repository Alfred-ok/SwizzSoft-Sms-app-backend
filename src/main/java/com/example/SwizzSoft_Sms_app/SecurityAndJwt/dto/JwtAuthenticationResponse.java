package com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String user;

    private String role;

    private String accessToken;

    private String refreshToken;
}

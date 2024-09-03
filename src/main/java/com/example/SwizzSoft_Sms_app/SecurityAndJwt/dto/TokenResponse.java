package com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto;

import lombok.Data;

@Data
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}

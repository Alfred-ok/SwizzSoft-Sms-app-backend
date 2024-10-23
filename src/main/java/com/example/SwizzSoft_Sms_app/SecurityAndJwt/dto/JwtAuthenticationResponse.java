package com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {


    private String user;

    private String role;

    private String responseCode;

    private String responseMessage;

    private String accessToken;

    private String refreshToken;

    private String groupId;
}

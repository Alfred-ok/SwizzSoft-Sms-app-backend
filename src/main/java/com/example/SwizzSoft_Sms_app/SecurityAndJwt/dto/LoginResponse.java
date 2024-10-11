package com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class LoginResponse {

    // Getters and setters
    @JsonProperty("ResponseCode")
    private String responseCode;

    @JsonProperty("ResponseMessage")
    private String responseMessage;

    @JsonProperty("Priviledge")
    private String privilege;

    // Default constructor
    public LoginResponse() {}

}


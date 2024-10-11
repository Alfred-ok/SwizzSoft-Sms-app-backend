package com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponse {

    @JsonProperty("ResponseCode")
    private String ResponseCode;

    @JsonProperty("ResponseMessage")
    private String ResponseMessage;

    public RegisterResponse() {}
}

package com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String Names;
    private String Email;
    private String Organisation;
    private String UserName;
    private String Password;
    private String Roles;
}

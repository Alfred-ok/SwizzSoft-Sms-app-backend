package com.example.SwizzSoft_Sms_app.SecurityAndJwt.controller;


import com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto.*;
import com.example.SwizzSoft_Sms_app.SecurityAndJwt.webtoken.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class authController {

    @Autowired
    private PasswordEncoder passwordEncoder;
   // @Autowired
   // private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;



    private static final String BASE_URL = "http://138.201.58.10:9001/api/Auth";
    private static final OkHttpClient client = new OkHttpClient();
    private static String token;


    @PostMapping("auth/register")
    public RegisterResponse register(@RequestBody RegisterRequest request){

        String createUrl = BASE_URL + "/Create";
        // Create the JSON body for the request




        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonRegisterRequest = objectMapper.createObjectNode();

        // Create the JSON body for the request
        jsonRegisterRequest.put("Names",request.getNames());
        jsonRegisterRequest.put("Email",request.getEmail());
        jsonRegisterRequest.put("Organisation",request.getOrganisation());
        jsonRegisterRequest.put("UserName",request.getUserName());
        jsonRegisterRequest.put("Password", request.getPassword());
        jsonRegisterRequest.put("Roles", request.getRoles());
        String json = jsonRegisterRequest.toString();

        okhttp3.RequestBody body = okhttp3.RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request requestData = new Request.Builder()
                .url(createUrl)
                //.addHeader("Authorization", "Bearer " + token)
                .post(body)
                .build();

        try (Response responseData = client.newCall(requestData).execute()) {
            if (responseData.isSuccessful()) {
                // Return the response body (e.g., confirmation message)
                assert responseData.body() != null;
                String jsonString = responseData.body().string();

                ObjectMapper mapper = new ObjectMapper();
                String parsedJson = mapper.readTree(jsonString).asText();
                System.out.println(parsedJson);


                return  mapper.readValue(parsedJson, RegisterResponse.class);

            } else {
                throw new IOException("Account creation failed: " + responseData.message());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("auth/authenticate")
    public JwtAuthenticationResponse authenticateAndGetToken(@RequestBody LoginForm loginForm) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonRequest = objectMapper.createObjectNode();



        String loginUrl = BASE_URL + "/Login";

        // Create the JSON body for the request
        jsonRequest.put("UserName",loginForm.getUsername());
        jsonRequest.put("Password",loginForm.getPassword());
        String json = jsonRequest.toString();
        //"{ \"UserName\":\"" + loginForm.getUsername() + "\", \"Password\":\"" + loginForm.getPassword() + "\" }";
        // Create the request
        //jackson
        okhttp3.RequestBody body = okhttp3.RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(loginUrl)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // Get the response body
                assert response.body() != null;
                String jsonString = response.body().string();

                // Parse the string to remove escape characters
                ObjectMapper tempMapper = new ObjectMapper();
                String parsedJson = tempMapper.readTree(jsonString).asText();

                System.out.println("Parsed JSON String: " + parsedJson);

                // Deserialize the cleaned JSON string into the LoginResponse class
                ObjectMapper mapper = new ObjectMapper();
                LoginResponse loginResponse = mapper.readValue(parsedJson, LoginResponse.class);


                // Extract the token from the response (assuming the response contains a "token" field)
                var jwt = jwtService.generateToken(loginForm.getUsername());
                JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

                jwtAuthenticationResponse.setAccessToken(jwt);
                jwtAuthenticationResponse.setUser(loginForm.getUsername());
                jwtAuthenticationResponse.setRole(loginResponse.getPrivilege());
                jwtAuthenticationResponse.setResponseCode(loginResponse.getResponseCode());
                jwtAuthenticationResponse.setResponseMessage(loginResponse.getResponseMessage());

                return jwtAuthenticationResponse;
            } else {
                throw new IOException("Login failed: " + response.message());
            }
        }
    }




/*


    @PostMapping("auth/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUsername(refreshToken);
        if (userName != null) {
           // AppUser user = this.repository.findByUsername(userName)
           //         .orElseThrow();
            if (jwtService.isTokenValid(refreshToken)) {
                var accessToken = jwtService.generateToken(user.getUsername());
                //revokeAllUserTokens(user);
                //saveUserToken(user, accessToken);

                TokenResponse tokenResponse = new TokenResponse();
                tokenResponse.setAccessToken(accessToken);
                tokenResponse.setRefreshToken(refreshToken);

                System.out.print("ok worked");
                new ObjectMapper().writeValue(response.getOutputStream(), tokenResponse);
            }
        }
    }
*/
}
package com.example.SwizzSoft_Sms_app.SecurityAndJwt.controller;

import com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto.JwtAuthenticationResponse;
import com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto.TokenResponse;
import com.example.SwizzSoft_Sms_app.SecurityAndJwt.entities.AppUser;
import com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto.LoginForm;
import com.example.SwizzSoft_Sms_app.SecurityAndJwt.repository.AppUserRepository;
import com.example.SwizzSoft_Sms_app.SecurityAndJwt.service.AppUserDetailsService;
import com.example.SwizzSoft_Sms_app.SecurityAndJwt.webtoken.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
public class authController {
    @Autowired
    private AppUserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AppUserDetailsService appUserDetailsService;


    @PostMapping("auth/register")
    public AppUser register(@RequestBody AppUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @PostMapping("auth/authenticate")
    public JwtAuthenticationResponse authenticateAndGetToken(@RequestBody LoginForm loginForm){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.getUsername(), loginForm.getPassword()
        ));
        if (authentication.isAuthenticated()){
            System.out.println("is authenticated");
           var jwt = jwtService.generateToken(appUserDetailsService.loadUserByUsername(loginForm.getUsername()));
           var refreshJwt = jwtService.generateRefreshToken(appUserDetailsService.loadUserByUsername(loginForm.getUsername()));
           Optional<AppUser> user = repository.findByUsername(loginForm.getUsername());

           JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
           jwtAuthenticationResponse.setAccessToken(jwt);
           jwtAuthenticationResponse.setRefreshToken(refreshJwt.toString());
           if (user.isPresent()){
               jwtAuthenticationResponse.setUser(user.get().getUsername());
               jwtAuthenticationResponse.setRole(user.get().getRole());
               
           }else {
               System.out.println("user is not present - failed ");
           }

           return jwtAuthenticationResponse; //user, roles, acesstoken, refreshtoken
        }else {
            throw new UsernameNotFoundException("Invalid Credentials");
        }
    }




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
            AppUser user = this.repository.findByUsername(userName)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken)) {
                var accessToken = jwtService.generateToken(appUserDetailsService.loadUserByUsername(user.getUsername()));
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

}

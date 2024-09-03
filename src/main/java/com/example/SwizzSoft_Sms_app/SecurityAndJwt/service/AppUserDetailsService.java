package com.example.SwizzSoft_Sms_app.SecurityAndJwt.service;

import com.example.SwizzSoft_Sms_app.SecurityAndJwt.entities.AppUser;
import com.example.SwizzSoft_Sms_app.SecurityAndJwt.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<AppUser> user = repository.findByUsername(username);
                if (user.isPresent()){
                    var userObj = user.get();
                    return User.builder()
                            .username(userObj.getUsername())
                            .password(userObj.getPassword())
                            .roles(getRoles(userObj))
                            .build();
                }else {
                    throw new UsernameNotFoundException(username);
                }
    }
    private String[] getRoles(AppUser user ){
        if (user.getRole() == null){
            return new String[]{"USER"};
        }
        return user.getRole().split(","); //return array of ["USER","ADMIN"]
    }
}

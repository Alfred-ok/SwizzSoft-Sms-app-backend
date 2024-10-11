package com.example.SwizzSoft_Sms_app.SecurityAndJwt.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AppUser", schema = "dbo") // Specify schema if necessary
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Names")
    private String names;
    @Column(name = "Email")
    private String Email;
    @Column(name = "Organisation")
    private String Organisation;
    @Column(name = "UserName")
    private String username;
    @Column(name = "Password")
    private String password;
    @Column(name = "Roles")
    private String role;

/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "UserName")
    private String username;
    @Column(name = "PasswordHash")
    private String password;
    @Column(name = "SecurityStamp")
    private String SecurityStamp;
    @Column(name = "Discriminator")
    private String Discriminator;
    @Column(name = "Names")
    private String Names;
    @Column(name = "Organisation")
    private Integer Organisation;
    @Column(name = "Role")
    private String role;
    @Column(name = "Email")
    private String Email;
    @Column(name = "priv")
    private Integer priv;
    @Column(name = "groupID")
    private Integer groupID;
*/

}


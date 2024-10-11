package com.example.SwizzSoft_Sms_app.Users;


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
@Table(name = "AspNetUsers", schema = "dbo")
public class AspNetUsers {

    @Column(name = "Id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "UserName")
    private String UserName;

    @Column(name = "Names")
    private String Names;

    @Column(name = "Organisation")
    private String Organisation;

    @Column(name = "Email")
    private String Email;

    @Column(name = "Role")
    private String Role;

    @Column(name = "groupID")
    private String groupID;

}

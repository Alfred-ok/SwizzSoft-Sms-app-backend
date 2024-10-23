package com.example.SwizzSoft_Sms_app.Organisation.dbo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "organisations")
public class Organisations {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Org_Code")
    private Integer Org_Code; //random 3 digit

    @Column(name = "url")//Sender Id
    private String url;

    @Column(name = "Org_Name")
    private String Org_Name;

    @Column(name = "Sms_Cost")
    private BigDecimal smsCost;

    @Column(name = "Sms_Units")
    private Integer Sms_Units;

    @Column(name = "Balance")
    private BigDecimal Balance;

    @Column(name = "MBCode")
    private Integer MBCode;

    @Column(name = "Token")
    private String Token;

    @Column(name = "AccessKey")
    private String AccessKey;

    @Column(name = "ApiKey")
    private String ApiKey;

    @Column(name = "groupID")
    private String groupID;

    @Column(name = "ClientId")
    private String ClientId;
}

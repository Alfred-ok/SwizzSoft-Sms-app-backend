package com.example.SwizzSoft_Sms_app.Messagein.dbo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messagesin", schema = "dbo") // Specify schema if necessary
public class Messagein {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Code")
    private Integer code;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "Message")
    private String message;

    @Column(name = "msgstatus")
    private String msgStatus;

    @Column(name = "SendStatus")
    private String sendStatus;

    @Column(name = "AuditDate")
    private LocalDateTime auditDate;

    @Column(name = "ResponseStatus")
    private String responseStatus ;

}


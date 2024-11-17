package com.example.SwizzSoft_Sms_app.Lipanampesamessagein.dbo;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lipanampesamessagein", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LipaNaMpesaMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "account_number", length = 50)
    private String accountNumber;

    @Column(name = "internal_transaction_id", length = 100)
    private String internalTransactionId;

    @Column(name = "business_number", length = 50)
    private String businessNumber;

    @Column(name = "sender", length = 100)
    private String sender;

    @Column(name = "receiver", length = 100)
    private String receiver;

    @Column(name = "transaction_reference", length = 100)
    private String transactionReference;

    @Column(name = "names", length = 200)
    private String names;

    @Column(name = "amount", precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "receivedtime")
    private LocalDateTime receivedTime;

    @Column(name = "message_Status", length = 20)
    private String messageStatus;
}

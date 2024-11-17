package com.example.SwizzSoft_Sms_app.Lipanampesamessagein.repo;

import com.example.SwizzSoft_Sms_app.Lipanampesamessagein.dbo.LipaNaMpesaMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LipaNaMpesaMessageRepository extends JpaRepository<LipaNaMpesaMessage, Long> {

    List<LipaNaMpesaMessage> findByTransactionReference(String transactionReference);

}


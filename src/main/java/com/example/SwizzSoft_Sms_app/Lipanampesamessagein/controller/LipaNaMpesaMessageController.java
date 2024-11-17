package com.example.SwizzSoft_Sms_app.Lipanampesamessagein.controller;

import com.example.SwizzSoft_Sms_app.Lipanampesamessagein.dbo.LipaNaMpesaMessage;
import com.example.SwizzSoft_Sms_app.Lipanampesamessagein.repo.LipaNaMpesaMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LipaNaMpesaMessageController {

    @Autowired
    private LipaNaMpesaMessageRepository repo;

    @PostMapping("/mpesapayment")
    public String createMessage(@RequestBody String transactionReference) {

        try {
          List<LipaNaMpesaMessage> data = repo.findByTransactionReference(transactionReference);

            // Check if the list is not empty
            if (!data.isEmpty()) {
                // Assuming you want to check the status of the first record
                LipaNaMpesaMessage message = data.get(0);

                // Use .equals() for string comparison
                if ("NOT USED".equals(message.getMessageStatus())) {
                    message.setMessageStatus("USED");

                    repo.save(message);
                    return "Success";
                } else {
                    return "Failed";
                }
            } else {
                // No record found with the given transactionReference
                return "Failed";
            }


        } catch (Exception e) {
            // Log the exception if needed
            e.printStackTrace();
            return "error";
        }
    }


}

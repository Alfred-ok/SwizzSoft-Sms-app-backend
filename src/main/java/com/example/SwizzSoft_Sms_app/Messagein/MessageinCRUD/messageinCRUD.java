package com.example.SwizzSoft_Sms_app.Messagein.MessageinCRUD;

import com.example.SwizzSoft_Sms_app.Messagein.dbo.Messagein;
import com.example.SwizzSoft_Sms_app.Messagein.repo.messageinRepo;
import com.example.SwizzSoft_Sms_app.Organisation.dbo.Organisations;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class messageinCRUD {

    @Autowired
    private messageinRepo repo;


    // POST endpoint to save a new message
    @PostMapping("/messagein")
    public ResponseEntity<?> createMessage(@RequestBody Messagein messagesIn) {
        //Random 3 digit code
        Random rand = new Random();
        int randomNum = rand.nextInt(900) + 100;

        //auditDate
        LocalDateTime now = LocalDateTime.now();

        // Convert comma-separated string to ArrayList
        List<String> arrayList = new ArrayList<>(Arrays.asList(messagesIn.getPhoneNumber().split(",")));

        // Optionally, trim whitespace from each element
        arrayList.replaceAll(String::trim);

        //post all data repeatedly after every phone number
        for (String eachPhoneNumber : arrayList) {

            Messagein message = new Messagein();
            message.setMessage(messagesIn.getMessage());
            //auditDate
            message.setAuditDate(now);
            //each
            message.setPhoneNumber(eachPhoneNumber);
            message.setSendStatus(messagesIn.getSendStatus());
            message.setMsgStatus(messagesIn.getMsgStatus());
            //organisation code
            message.setCode(randomNum);
            repo.save(message);
        }

        return ResponseEntity.ok("successfully");
    }




}


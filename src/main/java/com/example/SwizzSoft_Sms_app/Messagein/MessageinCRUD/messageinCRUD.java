package com.example.SwizzSoft_Sms_app.Messagein.MessageinCRUD;

import com.example.SwizzSoft_Sms_app.Messagein.dbo.Messagein;
import com.example.SwizzSoft_Sms_app.Messagein.repo.messageinRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/get_message-in")
    public ResponseEntity<Object> getMessage() {
       return ResponseEntity.ok(repo.findAll(PageRequest.of(0, 100)).getContent());
    }

   /* @GetMapping("/get_code/{code}")
    public ResponseEntity<Optional<Messagein>> getByCode(@PathVariable Integer code){
        System.out.println(repo.findByCode(code));
        return ResponseEntity.ok(repo.findByCode(code));
    }*/

    @GetMapping("/get_code/{code}")
    public ResponseEntity<List<Messagein>> getByCode(@PathVariable Integer code) {
        Pageable pageable = PageRequest.of(0, 80);
        List<Messagein> messages = repo.findByCode(code, pageable).getContent();;

        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }
}


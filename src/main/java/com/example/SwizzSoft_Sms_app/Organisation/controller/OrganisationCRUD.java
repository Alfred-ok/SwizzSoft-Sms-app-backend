package com.example.SwizzSoft_Sms_app.Organisation.controller;

import com.example.SwizzSoft_Sms_app.Organisation.dbo.Organisations;
import com.example.SwizzSoft_Sms_app.Organisation.repo.OrganisationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

import static java.lang.Integer.parseInt;

@RestController
public class OrganisationCRUD {

    @Autowired
    private OrganisationRepo repo;

    @PostMapping("/registerOrganisation")
    public String register(@RequestBody Organisations request){


        //generating organistion code
       // Random rand = new Random();

        // Check if the orgCode already exists in the database
        Optional<Organisations> existingOrgCode = repo.findByOrgCode(request.getOrg_Code());

        if (existingOrgCode.isPresent()) {
            // If an organization with the same orgCode is found, return a message indicating it's a duplicate
            return "Registration failed: Organization with the same org code already exists.";
        }

        // Check if the url already exists in the database
        Optional<Organisations> existingUrl = repo.findByUrl(request.getUrl());

        if (existingUrl.isPresent()) {
            // If url with the same url is found, return a message indicating it's a duplicate
            return "Registration failed: url with the same url already exists.";
        }

        // If orgCode is not present in the database, proceed to save the new organization
        Organisations org = new Organisations();
        try {
           // int randomNum = rand.nextInt(900) + 100;
            org.setOrg_Code(request.getOrg_Code());
            org.setUrl(request.getUrl());
            org.setMBCode(request.getOrg_Code());
            org.setClientId(request.getClientId());
            org.setOrg_Name(request.getOrg_Name());
            org.setApiKey(request.getApiKey());
            org.setAccessKey(request.getAccessKey());
            org.setGroupID(request.getGroupID());

            org.setSmsCost(request.getSmsCost());

            // Save the new organization to the database
            repo.save(org);

        } catch (Exception e) {
            throw new RuntimeException("Registration failed");

        }

        System.out.println("successful registration on org");
        // Return success message
        return "Successful Registration";


    }



    // GET method to retrieve organisation details by organisation code
    @GetMapping("/get_organisation")
    public ResponseEntity<Object> getOrganisation() {
        return ResponseEntity.ok(repo.findAll());
    }


    @GetMapping("/get_organisation/{Id}")
    public ResponseEntity<Object> getOrganisationById(@PathVariable Long Id) {
        return ResponseEntity.ok(repo.findById(Id));
    }

    // PUT method to update organisation details
    @PutMapping("/update-organisation/{id}")
    public ResponseEntity<String> updateOrganisation(@PathVariable Long id, @RequestBody  Organisations requestData) {

        // Fetch the organisation by ID
        Optional<Organisations> optionalOrg = repo.findById(id);
       // Random rand = new Random();
       // int randomNum = rand.nextInt(900) + 100;

        // Check if the organisation exists
        if (optionalOrg.isPresent()) {
            Organisations org = optionalOrg.get();


            // Update the organisation's details
            //org.setOrg_Code(randomNum); //don't update
            org.setOrg_Name(requestData.getOrg_Name());
            //org.setMBCode(requestData.getMBCode());
            //org.setToken(requestData.getToken());
           // org.setUrl(requestData.getUrl());// don't update
            org.setSmsCost(requestData.getSmsCost());
            org.setGroupID(requestData.getGroupID());
            org.setClientId(requestData.getClientId());
            org.setAccessKey(requestData.getAccessKey());
            org.setApiKey(requestData.getApiKey());


            // Save the updated organisation back to the repository
            repo.save(org);

            return ResponseEntity.ok("Organisation updated successfully");
        } else {
        // If organisation not found, return 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organisation not found");
    }


    }

}
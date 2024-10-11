package com.example.SwizzSoft_Sms_app.Organisation.repo;

import com.example.SwizzSoft_Sms_app.Organisation.dbo.Organisations;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrganisationRepo extends JpaRepository<Organisations, Long> {

    // Method to find an organisation by its Org_Code (which is an Integer)

   // Optional<Organisations> findByOrg_Code(Integer orgCode);
    // Custom JPQL query to find by Org_Code
    @Query("SELECT o FROM Organisations o WHERE o.Org_Code = :orgCode")
    Optional<Organisations> findByOrgCode(@Param("orgCode") Integer orgCode);

    @Query("SELECT o FROM Organisations o WHERE o.url = :url")
    Optional<Organisations>findByUrl(@Param("url") String url);

   // Optional<Organisations> findByOrg_Code(Integer orgCode);
}
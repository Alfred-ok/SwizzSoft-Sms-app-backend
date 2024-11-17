package com.example.SwizzSoft_Sms_app.Messagein.repo;


import com.example.SwizzSoft_Sms_app.Messagein.dbo.Messagein;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface messageinRepo extends JpaRepository<Messagein, Long> {
    Page<Messagein> findAll(Pageable pageable);

    Page<Messagein> findByCode(Integer code, Pageable pageable);
   // <Organisations> findByCode(String groupID, Pageable pageable);


    // New method to fetch messages not older than 3 days, sorted by ID descending
    @Query("SELECT m FROM Messagein m WHERE m.auditDate >= :threeDaysAgo ORDER BY m.id DESC")
    Page<Messagein> findRecentMessages(LocalDateTime threeDaysAgo, Pageable pageable);
}


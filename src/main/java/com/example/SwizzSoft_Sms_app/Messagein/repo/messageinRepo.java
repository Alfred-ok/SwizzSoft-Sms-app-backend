package com.example.SwizzSoft_Sms_app.Messagein.repo;


import com.example.SwizzSoft_Sms_app.Messagein.dbo.Messagein;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface messageinRepo extends JpaRepository<Messagein, Long> {

    Page<Messagein> findByCodeOrderByAuditDateDesc(Integer code, Pageable pageable);
    Page<Messagein> findByCodeAndSendStatusOrderByAuditDateDesc(Integer code, String sendStatus, Pageable pageable);

    @Query("SELECT m FROM Messagein m WHERE m.code = :code ORDER BY m.auditDate DESC")
    Stream<Messagein> streamByCode(@Param("code") Integer code);

    List<Messagein> findByCodeOrderByAuditDateDesc(Integer code);
    // New method to fetch messages not older than 3 days, sorted by ID descending
    @Query("SELECT m FROM Messagein m WHERE m.auditDate >= :threeDaysAgo ORDER BY m.id DESC")
    Page<Messagein> findRecentMessages(LocalDateTime threeDaysAgo, Pageable pageable);

    Page<Messagein> findByCodeAndSendStatus(Integer code, String sendStatus, Pageable pageable);



    @Query("SELECT m FROM Messagein m WHERE m.code = :code " +
            "AND (:phone IS NULL OR m.phoneNumber LIKE %:phone%) " +
            "AND (:status IS NULL OR m.sendStatus = :status) " +
            "AND (:startDate IS NULL OR m.auditDate >= :startDate) " +
            "AND (:endDate IS NULL OR m.auditDate <= :endDate) " +
            "ORDER BY m.auditDate DESC")
    List<Messagein> findWithFilters(
            @Param("code") Integer code,
            @Param("phone") String phone,
            @Param("status") String status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


    //Optional<Messagein> findByCode(Integer code);
}


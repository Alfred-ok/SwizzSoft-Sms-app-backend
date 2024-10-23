package com.example.SwizzSoft_Sms_app.Messagein.repo;


import com.example.SwizzSoft_Sms_app.Messagein.dbo.Messagein;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface messageinRepo extends JpaRepository<Messagein, Long> {
    Page<Messagein> findAll(Pageable pageable);

    Page<Messagein> findByCode(Integer code, Pageable pageable);
   // <Organisations> findByCode(String groupID, Pageable pageable);
}


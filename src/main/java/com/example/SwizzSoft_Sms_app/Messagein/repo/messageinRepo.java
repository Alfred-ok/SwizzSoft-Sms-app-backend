package com.example.SwizzSoft_Sms_app.Messagein.repo;


import com.example.SwizzSoft_Sms_app.Messagein.dbo.Messagein;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface messageinRepo extends JpaRepository<Messagein, Long> {

}


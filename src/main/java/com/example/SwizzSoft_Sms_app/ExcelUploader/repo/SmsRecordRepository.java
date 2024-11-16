package com.example.SwizzSoft_Sms_app.ExcelUploader.repo;


import com.example.SwizzSoft_Sms_app.ExcelUploader.dbo.SmsRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRecordRepository extends JpaRepository<SmsRecord, Long> {
}


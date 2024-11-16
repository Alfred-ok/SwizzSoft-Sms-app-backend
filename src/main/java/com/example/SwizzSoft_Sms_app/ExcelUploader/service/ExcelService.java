package com.example.SwizzSoft_Sms_app.ExcelUploader.service;


import com.example.SwizzSoft_Sms_app.ExcelUploader.dbo.SmsRecord;
import com.example.SwizzSoft_Sms_app.ExcelUploader.repo.SmsRecordRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {
    private final SmsRecordRepository repository;

    public ExcelService(SmsRecordRepository repository) {
        this.repository = repository;
    }

    public void save(MultipartFile file) throws IOException {
        List<SmsRecord> records = parseExcelFile(file);
        repository.saveAll(records);
    }

    private List<SmsRecord> parseExcelFile(MultipartFile file) throws IOException {
        List<SmsRecord> records = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header
            Row row = sheet.getRow(i);

            SmsRecord record = new SmsRecord();
            record.setClientCode(row.getCell(1).getStringCellValue());
            record.setBatchNo(row.getCell(2).getStringCellValue());
            record.setPhoneNumber(row.getCell(3).getStringCellValue());
            record.setSms(row.getCell(4).getStringCellValue());
            record.setStatus(row.getCell(5).getStringCellValue());
            record.setRemarks(row.getCell(6).getStringCellValue());

            records.add(record);
        }

        workbook.close();
        return records;
    }
}


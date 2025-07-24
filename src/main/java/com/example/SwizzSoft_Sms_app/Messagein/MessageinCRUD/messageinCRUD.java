package com.example.SwizzSoft_Sms_app.Messagein.MessageinCRUD;

import com.example.SwizzSoft_Sms_app.Messagein.Base64File;
import com.example.SwizzSoft_Sms_app.Messagein.FileUploadRequest;
import com.example.SwizzSoft_Sms_app.Messagein.dbo.Messagein;
import com.example.SwizzSoft_Sms_app.Messagein.repo.messageinRepo;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@RestController
public class messageinCRUD {

    @Autowired
    private messageinRepo repo;


    // POST endpoint to save a new message
    @PostMapping("/messagein")
    public ResponseEntity<String> createMessage(@RequestBody Messagein messagesIn) {
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

        return ResponseEntity.ok("Successfully");
    }















    @PostMapping("/messageinfile")
    public ResponseEntity<String> uploadFile(@RequestBody FileUploadRequest request) {
        try {
            if (request.getFiles() == null || request.getFiles().isEmpty()) {
                return ResponseEntity.badRequest().body("No files were uploaded. Please upload a valid file.");
            }

            List<Messagein> messageins = new ArrayList<>();

            // Loop through each uploaded file
            for (Base64File file : request.getFiles()) {
                byte[] decodedBytes = Base64.getDecoder().decode(file.getData().split(",")[1]); // Remove 'data:<type>;base64,' prefix
                InputStream inputStream = new ByteArrayInputStream(decodedBytes);
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheetAt(0);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    Messagein messagein = Messagein.builder()
                            .code(request.getOrgCode())
                            .phoneNumber(getStringCellValue(row.getCell(0)))
                            .message(getStringCellValue(row.getCell(1)))
                            .msgStatus("0")
                            .sendStatus("NOT SENT")
                            .auditDate(LocalDateTime.now())
                            .build();

                    messageins.add(messagein);
                }
                workbook.close();
            }

            // Save all records to the database
            repo.saveAll(messageins);
            return ResponseEntity.ok("Files uploaded and data saved successfully.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload file: " + e.getMessage());
        }
    }




    private String getStringCellValue(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue()); // Convert numeric to string if needed
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

    private double getNumericCellValue(Cell cell) {
        if (cell == null) return 0;
        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> Double.parseDouble(cell.getStringCellValue()); // Attempt to parse string as numeric
            default -> 0;
        };
    }






















    @GetMapping("/get_message-in")
    public ResponseEntity<Object> getMessage() {
        // Calculate the date 3 days ago
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);

        // Fetch recent messages in descending order of ID
        Pageable pageable = PageRequest.of(0, 100); // Adjust page size as needed
        return ResponseEntity.ok(repo.findRecentMessages(threeDaysAgo, pageable).getContent());
    }




/*
   @GetMapping("/get_code/{code}")
    public ResponseEntity<Optional<Messagein>> getByCode(@PathVariable Integer code){
        System.out.println(repo.findByCode(code));
        return ResponseEntity.ok(repo.findByCode(code));
    }
*/


    @GetMapping("/get_code/{code}")
    public ResponseEntity<Page<Messagein>> getByCodeAndStatus(
            @PathVariable Integer code,
            @RequestParam(required = false) String sendStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {

        Pageable pageable = PageRequest.of(page, size); // No need for manual sort, it's handled in method name

        Page<Messagein> messagesPage;

        if (sendStatus != null && !sendStatus.isBlank()) {
            messagesPage = repo.findByCodeAndSendStatusOrderByAuditDateDesc(code, sendStatus, pageable);
        } else {
            messagesPage = repo.findByCodeOrderByAuditDateDesc(code, pageable);
        }

        if (messagesPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(messagesPage);
    }




    @GetMapping(value = "/export_excel_base64/{code}", produces = MediaType.TEXT_PLAIN_VALUE)
    @Transactional(readOnly = true)
    public ResponseEntity<String> exportMessagesAsBase64(@PathVariable Integer code) {
        List<Messagein> outmessage = repo.findByCodeOrderByAuditDateDesc(code);
        if (outmessage.isEmpty()) {
            return ResponseEntity.status(204).build(); // No Content
        }


        try (
                SXSSFWorkbook workbook = new SXSSFWorkbook(); // For large data
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Stream<Messagein> messages = repo.streamByCode(code) // Stream instead of List
        ) {
            Sheet sheet = workbook.createSheet("Messages");

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Phone Number");
            header.createCell(1).setCellValue("Message");
            header.createCell(2).setCellValue("Send Status");
            header.createCell(3).setCellValue("Audit Date");

            // Stream and write rows
            final int[] rowNum = {1};
            messages.forEach(msg -> {
                Row row = sheet.createRow(rowNum[0]++);
                row.createCell(0).setCellValue(msg.getPhoneNumber());
                row.createCell(1).setCellValue(msg.getMessage());
                row.createCell(2).setCellValue(msg.getSendStatus());
                row.createCell(3).setCellValue(msg.getAuditDate() != null ? msg.getAuditDate().toString() : "");
            });

            workbook.write(out);
            workbook.dispose(); // Clean up temporary files

            String base64 = Base64.getEncoder().encodeToString(out.toByteArray());
            return ResponseEntity.ok(base64);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }




    @GetMapping(value = "/export_excel_base64_filtered", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> exportFiltered(
            @RequestParam Integer code,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        try {
            List<Messagein> messages = repo.findWithFilters(code, phone, status, startDate, endDate);

            if (messages == null || messages.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                Sheet sheet = workbook.createSheet("Messages");

                // Set header
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Phone Number");
                header.createCell(1).setCellValue("Message");
                header.createCell(2).setCellValue("Send Status");
                header.createCell(3).setCellValue("Audit Date");

                // Populate rows
                int rowNum = 1;
                for (Messagein msg : messages) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(msg.getPhoneNumber());
                    row.createCell(1).setCellValue(msg.getMessage());
                    row.createCell(2).setCellValue(msg.getSendStatus());
                    row.createCell(3).setCellValue(
                            msg.getAuditDate() != null ? msg.getAuditDate().toString() : ""
                    );
                }

                // Convert to Base64
                workbook.write(out);
                String base64 = Base64.getEncoder().encodeToString(out.toByteArray());
                return ResponseEntity.ok(base64);

            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to export Excel");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }








}


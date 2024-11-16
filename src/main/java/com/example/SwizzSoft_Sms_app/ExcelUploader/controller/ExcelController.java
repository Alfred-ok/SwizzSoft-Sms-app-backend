package com.example.SwizzSoft_Sms_app.ExcelUploader.controller;



import com.example.SwizzSoft_Sms_app.ExcelUploader.service.ExcelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {
    private final ExcelService excelService;

    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Validate that a file is present
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file uploaded. Please upload a valid Excel file.");
            }

            // Process the uploaded file
            excelService.save(file);
            return ResponseEntity.ok("File uploaded and data saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload file: " + e.getMessage());
        }
    }
}

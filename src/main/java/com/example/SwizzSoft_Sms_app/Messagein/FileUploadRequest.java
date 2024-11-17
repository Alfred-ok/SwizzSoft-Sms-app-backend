package com.example.SwizzSoft_Sms_app.Messagein;

import java.util.List;

public class FileUploadRequest {
    private Integer orgCode;
    private List<Base64File> files;

    // Getters and Setters

    public Integer getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(Integer orgCode) {
        this.orgCode = orgCode;
    }

    public List<Base64File> getFiles() {
        return files;
    }

    public void setFiles(List<Base64File> files) {
        this.files = files;
    }
}

package com.example.SwizzSoft_Sms_app.Organisation.dbo;

import java.math.BigDecimal;

public class PaymentRequestSum {
    private Integer quantity;
    private  BigDecimal total;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}

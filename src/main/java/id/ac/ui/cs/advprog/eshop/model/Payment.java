package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;

        if (method.equals("VOUCHER")) {
            String voucherCode = paymentData.get("voucherCode");
            if (voucherCode != null && voucherCode.length() == 16 && voucherCode.startsWith("ESHOP")) {
                int numCount = 0;
                for (int i = 0; i < voucherCode.length(); i++) {
                    if (Character.isDigit(voucherCode.charAt(i))) {
                        numCount++;
                    }
                }
                if (numCount == 8) {
                    this.status = "SUCCESS";
                } else {
                    this.status = "REJECTED";
                }
            } else {
                this.status = "REJECTED";
            }
        } else if (method.equals("BANK_TRANSFER")) {
            String bankName = paymentData.get("bankName");
            String refCode = paymentData.get("referenceCode");
            if (bankName == null || bankName.isEmpty() || refCode == null || refCode.isEmpty()) {
                this.status = "REJECTED";
            } else {
                this.status = "SUCCESS";
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getPaymentData() {
        return paymentData;
    }
}
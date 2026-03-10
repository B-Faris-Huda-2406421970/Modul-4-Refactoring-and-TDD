package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.Map;

public class BankTransferPayment extends Payment {

    public BankTransferPayment(String id, Map<String, String> paymentData) {
        super(id, "BANK_TRANSFER", paymentData);

        String bankName = paymentData.get("bankName");
        String refCode = paymentData.get("referenceCode");

        if (bankName == null || bankName.isEmpty() || refCode == null || refCode.isEmpty()) {
            this.setStatus(PaymentStatus.REJECTED.getValue());
        } else {
            this.setStatus(PaymentStatus.SUCCESS.getValue());
        }
    }
}
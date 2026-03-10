package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.Map;

public class BankTransferPayment extends Payment {

    public BankTransferPayment(String id, Map<String, String> paymentData) {
        super(id, "BANK_TRANSFER", paymentData);
        this.setStatus(validateBankTransfer() ? PaymentStatus.SUCCESS.getValue() : PaymentStatus.REJECTED.getValue());
    }

    private boolean validateBankTransfer() {
        String bankName = this.getPaymentData().get("bankName");
        String refCode = this.getPaymentData().get("referenceCode");
        return bankName != null && !bankName.isEmpty() && refCode != null && !refCode.isEmpty();
    }
}
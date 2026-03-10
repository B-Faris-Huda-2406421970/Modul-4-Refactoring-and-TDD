package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;

    @Setter
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = validatePayment() ? PaymentStatus.SUCCESS.getValue() : PaymentStatus.REJECTED.getValue();
    }

    private boolean validatePayment() {
        if ("VOUCHER".equals(method)) {
            return validateVoucher();
        } else if ("BANK_TRANSFER".equals(method)) {
            return validateBankTransfer();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean validateVoucher() {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }
        long numCount = voucherCode.chars().filter(Character::isDigit).count();
        return numCount == 8;
    }

    private boolean validateBankTransfer() {
        String bankName = paymentData.get("bankName");
        String refCode = paymentData.get("referenceCode");
        return bankName != null && !bankName.isEmpty() && refCode != null && !refCode.isEmpty();
    }
}
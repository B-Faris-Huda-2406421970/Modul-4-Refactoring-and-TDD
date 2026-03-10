package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.Map;

public class VoucherPayment extends Payment {

    public VoucherPayment(String id, Map<String, String> paymentData) {
        super(id, "VOUCHER", paymentData);
        this.setStatus(validateVoucher() ? PaymentStatus.SUCCESS.getValue() : PaymentStatus.REJECTED.getValue());
    }

    private boolean validateVoucher() {
        String voucherCode = this.getPaymentData().get("voucherCode");
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }
        long numCount = voucherCode.chars().filter(Character::isDigit).count();
        return numCount == 8;
    }
}
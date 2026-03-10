package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.Map;

public class VoucherPayment extends Payment {

    public VoucherPayment(String id, Map<String, String> paymentData) {
        super(id, "VOUCHER", paymentData);

        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode != null && voucherCode.length() == 16 && voucherCode.startsWith("ESHOP")) {
            int numCount = 0;
            for (int i = 0; i < voucherCode.length(); i++) {
                if (Character.isDigit(voucherCode.charAt(i))) {
                    numCount++;
                }
            }
            if (numCount == 8) {
                this.setStatus(PaymentStatus.SUCCESS.getValue());
            } else {
                this.setStatus(PaymentStatus.REJECTED.getValue());
            }
        } else {
            this.setStatus(PaymentStatus.REJECTED.getValue());
        }
    }
}
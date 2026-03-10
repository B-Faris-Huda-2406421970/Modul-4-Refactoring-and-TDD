package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VoucherPaymentTest {
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreateVoucherPaymentSuccess() {
        VoucherPayment payment = new VoucherPayment("1", paymentData);
        assertEquals("1", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreateVoucherPaymentRejectLength() {
        paymentData.put("voucherCode", "ESHOP1234ABC567");
        VoucherPayment payment = new VoucherPayment("2", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreateVoucherPaymentRejectPrefix() {
        paymentData.put("voucherCode", "SHOP1234ABC56789");
        VoucherPayment payment = new VoucherPayment("3", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreateVoucherPaymentRejectNumCountLess() {
        paymentData.put("voucherCode", "ESHOP1234ABC567A");
        VoucherPayment payment = new VoucherPayment("4", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreateVoucherPaymentRejectNumCountMore() {
        paymentData.put("voucherCode", "ESHOP12345BC5678");
        VoucherPayment payment = new VoucherPayment("5", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreateVoucherPaymentRejectNullVoucherCode() {
        paymentData.put("voucherCode", null);
        VoucherPayment payment = new VoucherPayment("6", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}
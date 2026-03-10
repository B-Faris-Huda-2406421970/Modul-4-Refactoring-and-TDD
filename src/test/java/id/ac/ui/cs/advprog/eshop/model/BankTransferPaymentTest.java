package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankTransferPaymentTest {
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF12345");
    }

    @Test
    void testCreateBankTransferPaymentSuccess() {
        BankTransferPayment payment = new BankTransferPayment("1", paymentData);
        assertEquals("1", payment.getId());
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreateBankTransferPaymentRejectNullBankName() {
        paymentData.put("bankName", null);
        BankTransferPayment payment = new BankTransferPayment("2", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreateBankTransferPaymentRejectEmptyBankName() {
        paymentData.put("bankName", "");
        BankTransferPayment payment = new BankTransferPayment("3", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreateBankTransferPaymentRejectNullReferenceCode() {
        paymentData.put("referenceCode", null);
        BankTransferPayment payment = new BankTransferPayment("4", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreateBankTransferPaymentRejectEmptyReferenceCode() {
        paymentData.put("referenceCode", "");
        BankTransferPayment payment = new BankTransferPayment("5", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}
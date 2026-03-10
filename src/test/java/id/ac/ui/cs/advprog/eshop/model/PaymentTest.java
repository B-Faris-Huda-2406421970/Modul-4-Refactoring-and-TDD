package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentTest {
    Map<String, String> paymentDataVoucher;
    Map<String, String> paymentDataBankTransfer;

    @BeforeEach
    void setUp() {
        paymentDataVoucher = new HashMap<>();
        paymentDataVoucher.put("voucherCode", "ESHOP1234ABC5678");

        paymentDataBankTransfer = new HashMap<>();
        paymentDataBankTransfer.put("bankName", "BCA");
        paymentDataBankTransfer.put("referenceCode", "REF12345");
    }

    @Test
    void testCreatePaymentVoucherSuccess() {
        Payment payment = new Payment("1", "VOUCHER", paymentDataVoucher);
        assertEquals("1", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(paymentDataVoucher, payment.getPaymentData());
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectLength() {
        paymentDataVoucher.put("voucherCode", "ESHOP1234ABC567");
        Payment payment = new Payment("2", "VOUCHER", paymentDataVoucher);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectPrefix() {
        paymentDataVoucher.put("voucherCode", "SHOP1234ABC56789");
        Payment payment = new Payment("3", "VOUCHER", paymentDataVoucher);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectNumCountLess() {
        paymentDataVoucher.put("voucherCode", "ESHOP1234ABC567A");
        Payment payment = new Payment("4", "VOUCHER", paymentDataVoucher);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectNumCountMore() {
        paymentDataVoucher.put("voucherCode", "ESHOP12345BC5678");
        Payment payment = new Payment("5", "VOUCHER", paymentDataVoucher);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferSuccess() {
        Payment payment = new Payment("6", "BANK_TRANSFER", paymentDataBankTransfer);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectEmptyBankName() {
        paymentDataBankTransfer.put("bankName", "");
        Payment payment = new Payment("7", "BANK_TRANSFER", paymentDataBankTransfer);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectNullBankName() {
        paymentDataBankTransfer.put("bankName", null);
        Payment payment = new Payment("8", "BANK_TRANSFER", paymentDataBankTransfer);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectEmptyReferenceCode() {
        paymentDataBankTransfer.put("referenceCode", "");
        Payment payment = new Payment("9", "BANK_TRANSFER", paymentDataBankTransfer);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectNullReferenceCode() {
        paymentDataBankTransfer.put("referenceCode", null);
        Payment payment = new Payment("10", "BANK_TRANSFER", paymentDataBankTransfer);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("11", "MAGIC", paymentDataVoucher);
        });
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = new Payment("1", "VOUCHER", paymentDataVoucher);
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }
}
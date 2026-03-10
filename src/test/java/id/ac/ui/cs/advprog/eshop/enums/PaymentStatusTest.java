package id.ac.ui.cs.advprog.eshop.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentStatusTest {

    @Test
    void testContainsSuccess() {
        assertTrue(PaymentStatus.contains("SUCCESS"));
    }

    @Test
    void testContainsRejected() {
        assertTrue(PaymentStatus.contains("REJECTED"));
    }

    @Test
    void testContainsInvalid() {
        assertFalse(PaymentStatus.contains("MEOW"));
    }
}
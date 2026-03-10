package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EshopApplicationTests {

    @Test
    void testApplication() {
        assertDoesNotThrow(() -> {
            EshopApplication.main(new String[] {});
        });
    }
}

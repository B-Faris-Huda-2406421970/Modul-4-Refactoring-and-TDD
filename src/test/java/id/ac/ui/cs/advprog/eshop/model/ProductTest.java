package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;
    String testProductId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
    String testProductName = "Sampo Cap Bambang";
    int testProductQuantity = 100;

    @BeforeEach
    void setUp(){
        this.product = new Product();
        this.product.setProductId(testProductId);
        this.product.setProductName(testProductName);
        this.product.setProductQuantity(testProductQuantity);
    }

    @Test
    void testGetProductId(){
        assertEquals(testProductId, this.product.getProductId());
    }

    @Test
    void testGetProductName(){
        assertEquals(testProductName, this.product.getProductName());
    }

    @Test
    void testGetProductQuantity(){
        assertEquals(testProductQuantity, this.product.getProductQuantity());
    }
}

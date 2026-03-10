package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product1;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setProductName("product 1");
        product1.setProductQuantity(67);
        product1.setProductId("product 1");
    }

    @Test
    void testCreate(){
        Product createdProduct = productService.create(product1);
        assertEquals(product1, createdProduct);
    }

    @Test
    void testUpdate(){
        productService.create(product1);

        product1.setProductQuantity(1);
        Product updatedProduct = productService.update(product1);

        assertEquals(product1, updatedProduct);
    }

    @Test
    void testDelete(){
        productService.create(product1);

        String productId = product1.getProductId();
        productService.delete(productId);

        Product foundProduct = productRepository.findById(productId);
        assertNull(foundProduct);
    }

    @Test
    void testFindAll(){
        Product product2 = new Product();
        product2.setProductId("product 2");
        product2.setProductName("product 2");
        product2.setProductQuantity(2);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        Iterator<Product> productIterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> foundProductList = productService.findAll();
        assertEquals(product1, foundProductList.get(0));
        assertEquals(product2, foundProductList.get(1));
    }

    @Test
    void testFindById(){
        productService.create(product1);

        String productId = product1.getProductId();
        when(productRepository.findById(productId)).thenReturn(product1);

        Product foundProduct = productService.findById(productId);
        assertEquals(product1, foundProduct);
    }
}

package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    String testProductId1 = "eb558e9f-1c39-460e-8860-71af6af63bd6";
    String testProductName1 = "Sampo Cap Bambang";
    int testProductQuantity1 = 100;

    String testProductId2 = "a0f9de46-90b1-437d-a0bf-d0821dde9096";
    String testProductName2 = "Sampo Cap Usep";
    int testProductQuantity2 = 50;

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp(){

    }

    void setupTestProduct1(Product product){
        product.setProductId(testProductId1);
        product.setProductName(testProductName1);
        product.setProductQuantity(testProductQuantity1);
    }

    void setupTestProduct2(Product product){
        product.setProductId(testProductId2);
        product.setProductName(testProductName2);
        product.setProductQuantity(testProductQuantity2);
    }

    @Test
    void testCreateAndFind(){
        Product product = new Product();
        setupTestProduct1(product);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty(){
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct(){
        Product product1 = new Product();
        setupTestProduct1(product1);
        productRepository.create(product1);

        Product product2 = new Product();
        setupTestProduct2(product2);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdIfFound(){
        Product product = new Product();
        setupTestProduct1(product);
        productRepository.create(product);

        Product foundProduct = productRepository.findById(testProductId1);
        assertEquals(product.getProductId(), foundProduct.getProductId());
        assertEquals(product.getProductName(), foundProduct.getProductName());
        assertEquals(product.getProductQuantity(), foundProduct.getProductQuantity());
    }

    @Test
    void testFindByIdIfNotFound(){
        Product foundProduct = productRepository.findById(testProductId2);
        assertNull(foundProduct);
    }

    @Test
    void testUpdateProduct(){
        Product product = new Product();
        setupTestProduct1(product);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Name");
        updatedProduct.setProductQuantity(67);

        Product foundProduct = productRepository.update(updatedProduct);
        assertEquals(updatedProduct.getProductId(), foundProduct.getProductId());
        assertEquals(updatedProduct.getProductName(), foundProduct.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), foundProduct.getProductQuantity());
    }

    @Test
    void testUpdateProductIfIDNotFound(){
        Product product = new Product();
        setupTestProduct1(product);
        productRepository.create(product);

        Product updatedProduct = new Product();
        setupTestProduct2(updatedProduct);

        Product foundProduct = productRepository.update(updatedProduct);
        assertNull(foundProduct);
    }

    @Test
    void testDeleteProduct(){
        Product product = new Product();
        setupTestProduct1(product);
        productRepository.create(product);

        productRepository.delete(product.getProductId());
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProductIfIDNotFound(){
        Product product = new Product();
        setupTestProduct1(product);
        productRepository.create(product);

        productRepository.delete(testProductId2);
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());;
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }
}

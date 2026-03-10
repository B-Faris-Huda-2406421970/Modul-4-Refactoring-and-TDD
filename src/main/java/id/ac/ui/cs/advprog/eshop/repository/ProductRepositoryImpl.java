package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final List<Product> productData = new ArrayList<>();

    @Override
    public Product create(Product product){
        productData.add(product);
        return product;
    }

    @Override
    public Product update(Product updateProduct){
        Product product = findById(updateProduct.getProductId());
        if (product == null) return null;

        product.setProductName(updateProduct.getProductName());
        product.setProductQuantity(updateProduct.getProductQuantity());
        return product;
    }

    @Override
    public void delete(String productId) {
        productData.removeIf(product -> product.getProductId().equals(productId));
    }

    @Override
    public Iterator<Product> findAll(){
        return productData.iterator();
    }

    @Override
    public Product findById(String productId){
        for (Product product : productData){
            if (product.getProductId().equals(productId)){
                return product;
            }
        }

        return null;
    }
}

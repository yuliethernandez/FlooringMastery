
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.ClassFlooringDaoProduct;
import com.sg.flooringmastery.dao.ClassPersistenceException;
import com.sg.flooringmastery.dto.Product;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClassFlooringProductStubImpl implements ClassFlooringDaoProduct{
    
    public Product product;
    
    public ClassFlooringProductStubImpl(){
        this.product = new Product("Brick", new BigDecimal("15"), new BigDecimal("12"));
    }

    @Override
    public List<Product> getAllProduct() throws ClassPersistenceException, FileNotFoundException {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        return productList;
    }

    @Override
    public Product getProduct(String productType) throws ClassPersistenceException, FileNotFoundException {
        if (product.getProductType().equals(productType)) {
            return product;
        } else {
            return null;
        } 
    }
    
}

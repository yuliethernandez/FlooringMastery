
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.FileNotFoundException;
import java.util.List;

public interface ClassFlooringDaoProduct {
    
    public List<Product> getAllProduct() throws ClassPersistenceException, FileNotFoundException;
    
    public Product getProduct(String productType) throws ClassPersistenceException, FileNotFoundException;
    
}

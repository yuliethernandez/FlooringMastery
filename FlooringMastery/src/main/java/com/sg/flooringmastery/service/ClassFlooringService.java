
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.ClassPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State; 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ClassFlooringService {
    
    Order createOrder(Order order) throws ClassPersistenceException, IOException, 
            ClassDuplicateOrderException, ClassInvalidDataException;
//ClassFlooringPersistenceException;
 
    List<Order> getAllOrders(LocalDate date) throws ClassPersistenceException, FileNotFoundException, 
            ClassNotFoundOrderException, IOException;
 
    Order getOrder(int orderNumber, LocalDate dateOrder) throws ClassPersistenceException, 
            FileNotFoundException, IOException;
    
    Order editOrder(Order order, LocalDate dateOrder) throws ClassPersistenceException, IOException;
    
    Product getProduct(String productType) throws ClassPersistenceException, FileNotFoundException;
    
    State getState(String abrev) throws ClassPersistenceException, FileNotFoundException, IOException;
    
    List<State> getAllState() throws ClassPersistenceException, FileNotFoundException, 
            ClassNotFoundOrderException, IOException;
 
    List<Product> getAllProducts() throws ClassPersistenceException, FileNotFoundException, 
            ClassNotFoundOrderException, IOException;
        
    Order removeOrder(LocalDate date, int orderNumber) throws ClassPersistenceException, IOException;
    
    boolean validateDate(LocalDate date) throws ClassInvalidDataException;

    void validateArea(BigDecimal area) throws ClassInvalidDataException;
    
    boolean validateCustomerName(String name) throws ClassInvalidDataException; 
    
    BigDecimal getTaxRate(String abrevUser) throws IOException, ClassNotFoundException, 
            ClassPersistenceException;

    boolean exportData() throws IOException, ClassPersistenceException, ClassNotFoundException;

}

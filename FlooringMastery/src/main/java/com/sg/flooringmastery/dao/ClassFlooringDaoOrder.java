
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ClassFlooringDaoOrder {
    
    Order createOrder(Order order) throws ClassPersistenceException, FileNotFoundException, IOException;
    
    List<Order> getAllOrdersByDate(LocalDate date) throws ClassPersistenceException, FileNotFoundException, IOException;
       
    Order getOrder(int orderNumber, LocalDate dateOrder) throws ClassPersistenceException, FileNotFoundException, IOException;
    
    Order editOrder(Order order, LocalDate dateOrder) throws ClassPersistenceException, IOException;
    
    Order removeOrder(LocalDate date, int orderNumber) throws ClassPersistenceException, IOException;
    
    boolean exportAllData() throws ClassPersistenceException, IOException, ClassNotFoundException;
    
    Order getOrder(int orderNumber) throws ClassPersistenceException, FileNotFoundException, IOException;
}

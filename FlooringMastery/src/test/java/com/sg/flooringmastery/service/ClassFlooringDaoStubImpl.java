
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.ClassFlooringDaoOrder;
import com.sg.flooringmastery.dao.ClassPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClassFlooringDaoStubImpl implements ClassFlooringDaoOrder{
    
    public Order order;
    
    public ClassFlooringDaoStubImpl(){
        Product pro = new Product("Roof", new BigDecimal("15"), new BigDecimal("25"));
        this.order = new Order(100, "Elba Labrada", "CA", new BigDecimal("20"), 
                pro, new BigDecimal("300"));
    }
    public ClassFlooringDaoStubImpl(Order order){
        this.order = order;
    }

    @Override
    public Order createOrder(Order order) throws ClassPersistenceException, FileNotFoundException, IOException {
        if (order.getOrderNumber() == order.getOrderNumber()) {
            return order;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrdersByDate(LocalDate date) throws ClassPersistenceException, FileNotFoundException, IOException {
        List<Order> ordersList = new ArrayList<>();
        ordersList.add(order);
        return ordersList;
    }

    @Override//rev date
    public Order getOrder(int orderNumber, LocalDate dateOrder) throws ClassPersistenceException, FileNotFoundException, IOException {
        if (order.getOrderNumber() == orderNumber) {
            return order;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(Order order, LocalDate dateOrder) throws ClassPersistenceException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws ClassPersistenceException, IOException {
        if (order.getOrderNumber() == orderNumber){
            return order;
        } else {
            return null;
        }
    }

    @Override
    public boolean exportAllData() throws ClassPersistenceException, IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Order getOrder(int orderNumber) throws ClassPersistenceException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

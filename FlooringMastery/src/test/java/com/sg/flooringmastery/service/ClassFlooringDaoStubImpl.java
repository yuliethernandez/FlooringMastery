
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassFlooringDaoStubImpl implements ClassFlooringDaoOrder{
            
    private Order onlyOrder ;
    
    private Map<Integer, Order> listOrder = new HashMap<>();
    
    public ClassFlooringDaoStubImpl(){
        Product pro = new Product("Brick", new BigDecimal("15"), new BigDecimal("12"));
        onlyOrder = new Order(100, //int orderNumber
                "Mary",  //String customerName,
                "CU",  //String stateAbrev,
                new BigDecimal("5"),//BigDecimal taxRate,
                pro, //Product product,
                new BigDecimal("5.00"),//costPerSquareFoot, 
                new BigDecimal("35.00"),//laborCostPerSquareFoot, 
                new BigDecimal("24.00"),//materialCost, 
                new BigDecimal("25.00"),//laborCost, 
                new BigDecimal("25.00"),//tax, 
                new BigDecimal("50.00"),//total);
                new BigDecimal("500"));//BigDecimal area

        listOrder.put(onlyOrder.getOrderNumber(), onlyOrder);
    }
    public ClassFlooringDaoStubImpl(Order order){
        this.onlyOrder = order;
    }

    @Override
    public Order createOrder(Order order, LocalDate dateOrder)throws ClassPersistenceException, FileNotFoundException, IOException {
        //if (onlyOrder.getOrderNumber() != order.getOrderNumber()) {
        if (order.getOrderNumber()==onlyOrder.getOrderNumber()) {
            return null;
        } else {
            return order;
        }
    }

    @Override
    public List<Order> getAllOrdersByDate(LocalDate date) throws ClassPersistenceException, FileNotFoundException, IOException {
        /*List<Order> ordersList = new ArrayList<>();
        ordersList.add(onlyOrder);
        return ordersList;*/
        List<Order> list = listOrder.values().stream()
                .collect(Collectors.toCollection(ArrayList::new));
        return list;
    }

    @Override//rev date
    public Order getOrder(int orderNumber, LocalDate dateOrder) throws ClassPersistenceException, FileNotFoundException, IOException {
        if (onlyOrder.getOrderNumber() == orderNumber) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(Order order, LocalDate dateOrder) throws ClassPersistenceException, IOException {
        if (order.getOrderNumber() == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws ClassPersistenceException, IOException {
        if (onlyOrder.getOrderNumber() == orderNumber){
            return onlyOrder;
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
        if (listOrder.get(orderNumber)!=null) {
            return listOrder.get(orderNumber);
        } else {
            //Should return an empty list like the dao does.
            return null;
        }
    }

    @Override
    public int getNextId() throws ClassPersistenceException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

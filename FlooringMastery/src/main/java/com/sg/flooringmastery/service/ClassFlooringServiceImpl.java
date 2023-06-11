
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.ClassFlooringDaoWriteEntry;
import com.sg.flooringmastery.dao.ClassPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.util.List;
import com.sg.flooringmastery.dao.ClassFlooringDaoOrder;
import java.io.FileNotFoundException;
import com.sg.flooringmastery.dao.ClassFlooringDaoProduct;
import com.sg.flooringmastery.dao.ClassFlooringDaoState;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class ClassFlooringServiceImpl implements ClassFlooringService {
    private final ClassFlooringDaoOrder daoOrder;
    private final ClassFlooringDaoProduct daoProduct;
    private final ClassFlooringDaoState daoState;
    private final ClassFlooringDaoWriteEntry write;

    public ClassFlooringServiceImpl(ClassFlooringDaoOrder daoOrder, ClassFlooringDaoProduct daoProduct, 
            ClassFlooringDaoState daoState, ClassFlooringDaoWriteEntry write) {
        this.daoOrder = daoOrder;
        this.daoProduct = daoProduct;
        this.daoState = daoState;
        this.write = write;
    }

    @Override
    public Order createOrder(Order order, LocalDate dateOrder) throws ClassPersistenceException, IOException, ClassDuplicateOrderException, ClassInvalidDataException {
        if(daoOrder.getOrder(order.getOrderNumber()) != null){
            throw new ClassDuplicateOrderException("Could not create the Order.  Another Order with the Number: "
                    + order.getOrderNumber()
                    + " already exists");
        }
        validateOrderData(order);
        
        Order newOrder = daoOrder.createOrder(order, dateOrder);
        if(newOrder != null){
            write.writeEntry("ORDER NUMBER " + order.getOrderNumber() + ": CREATED ");
        }else
            throw new ClassPersistenceException("The Order was not created");
        
        return newOrder;
    }

    @Override
    public List<Order> getAllOrders(LocalDate date) throws ClassPersistenceException, FileNotFoundException, ClassNotFoundOrderException, IOException {
        List<Order> orders = null;
        orders = daoOrder.getAllOrdersByDate(date);
        if(orders.isEmpty()){
            throw new ClassNotFoundOrderException("No orders found");
        }
        return orders;
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate dateOrder) throws FileNotFoundException, IOException, ClassPersistenceException {
        Order order = daoOrder.getOrder(orderNumber, dateOrder);
        if(order == null){
            throw new FileNotFoundException("There is not order in the files with such a number");
        }
        return order;
    }

    @Override
    public Order editOrder(Order order, LocalDate dateOrder) throws ClassPersistenceException, IOException {
        Order orderEdited = daoOrder.editOrder(order, dateOrder);
        if(orderEdited == null){
            throw new ClassPersistenceException("Could not edit the Order " + order.getOrderNumber());
        }else{
            write.writeEntry("ORDER " + order.getOrderNumber() + " - EDITED");
        }
        return orderEdited;
    }
    
    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws ClassPersistenceException, IOException {
        Order order = null;
        try{
            order = daoOrder.removeOrder(date, orderNumber);
            if(order != null){
                write.writeEntry("ORDER " + order.getOrderNumber() + " - DELETED");
            }
        }        
        catch(IOException e){
            throw  new ClassPersistenceException("There is not orders with that date");
        }
        return order;
    }

    @Override
    public Product getProduct(String productType) throws ClassPersistenceException, FileNotFoundException {
        Product product = daoProduct.getProduct(productType);
        if(product == null){
            throw new FileNotFoundException("The product doesn't exist");
        }
        return product;
    }

    @Override
    public State getState(String abrev) throws ClassPersistenceException, FileNotFoundException, IOException {
        State state = daoState.getState(abrev);
        if(state == null){
            throw new FileNotFoundException("The state does not exist in the tax file, we cannot sell there");
        }
        return state;
    }
    
    private void validateOrderData(Order order) throws ClassInvalidDataException, ClassPersistenceException, 
            FileNotFoundException, ClassDuplicateOrderException, IOException {

        String stateAbrev = order.getStateAbrev();
        String prodType = order.getProduct().getProductType();
        
        if (order.getOrderNumber() == 0 || order.getCustomerName().trim().length() == 0
            || order.getStateAbrev() == null || order.getProduct() == null
            || order.getArea().compareTo(BigDecimal.ZERO)==0
            || order.getArea().compareTo(BigDecimal.ZERO)== -1
            || daoState.getState(stateAbrev) == null
            || daoProduct.getProduct(prodType) == null) {

            throw new ClassInvalidDataException(
                    "All fields are required.");
        }
    }

    @Override
    public List<State> getAllState() throws ClassPersistenceException, FileNotFoundException, ClassNotFoundOrderException, IOException {
        List<State> states = null;
        states = daoState.getAllState();
        if(states.isEmpty()){
            throw new ClassNotFoundOrderException("No states found");
        }
        return states;
    }
    
    @Override
    public List<Product> getAllProducts() throws ClassPersistenceException, FileNotFoundException, ClassNotFoundOrderException, IOException {
        List<Product> products = null;
        products = daoProduct.getAllProduct();
        if(products.isEmpty()){
            throw new ClassNotFoundOrderException("No products found");
        }
        return products;
    }

    @Override
    public boolean validateDate(LocalDate date) throws ClassInvalidDataException{
        LocalDate dateToday = LocalDate.now();
        boolean truth = date.isAfter(dateToday);
        if(!truth){
            throw new ClassInvalidDataException("The date must be in the future");
        }
        return truth;
    }

    @Override
    public void validateArea(BigDecimal area) throws ClassInvalidDataException{                
        if(area.compareTo(new BigDecimal("100")) == -1){
            throw new ClassInvalidDataException("The area must be a positive decimal. Minimum order size is 100 sq ft.");
        }                
        //return true;
    }
    
    @Override
    public boolean validateCustomerName(String name) throws ClassInvalidDataException {
        String regex = "^[A-Za-z0-9,. ]+$";
        Pattern pat = Pattern.compile(regex);
        /*Regular Expressions or Regex is an API for defining String patterns that can be used for searching, 
        manipulating, and editing a string in Java. Email validation and passwords are a few areas of strings 
        where Regex is widely used to define the constraints. Regular Expressions are provided under 
        java.util.regex package. */
        if(name.isBlank() || !pat.matcher(name).matches()){
            throw new ClassInvalidDataException("Invalid Data, May not be blank and can only contain: [a-z][0-9][,]"); 
        }
        return true;                         
    }

    @Override
    public BigDecimal getTaxRate(String abrevUser) throws IOException, ClassNotFoundException, ClassPersistenceException{
        BigDecimal taxRate = daoState.getTaxRate(abrevUser);
        if(taxRate == null){
            throw new ClassNotFoundException("There is not state with that abreviation");
        }        
        return taxRate;
    }

    @Override
    public boolean exportData() throws IOException, ClassPersistenceException, ClassNotFoundException {
        return daoOrder.exportAllData();
    }

    public int getIdOrder() throws ClassPersistenceException, IOException{
        return daoOrder.getNextId();
    }
}

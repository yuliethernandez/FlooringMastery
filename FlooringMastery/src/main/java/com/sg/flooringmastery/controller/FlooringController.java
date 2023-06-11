
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.ClassPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import com.sg.flooringmastery.service.ClassDuplicateOrderException;
import com.sg.flooringmastery.service.ClassFlooringServiceImpl;
import com.sg.flooringmastery.service.ClassInvalidDataException;
import com.sg.flooringmastery.service.ClassNotFoundOrderException;
import com.sg.flooringmastery.ui.ClassFlooringUserView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlooringController {

    private final ClassFlooringUserView io;
    private final ClassFlooringServiceImpl service;

    public FlooringController(ClassFlooringUserView io, ClassFlooringServiceImpl service) {
        this.io = io;
        this.service = service;
    }

    public void run() throws ClassPersistenceException, FileNotFoundException, ClassNotFoundOrderException, IOException, ClassDuplicateOrderException, ClassInvalidDataException, ClassNotFoundException {
        try{        
            while(true){            
                int opt = io.mainMenuUserStories();

                switch (opt) {
                  case 1:{
                      displayOrders();
                      break;
                  }
                  case 2:{
                      createOrder();
                      break;
                  }
                  case 3:{
                      editOrder();
                      break;
                  }
                  case 4:{
                      removeOrder();
                      break;
                  }
                  case 5:{
                      this.exportAllData();
                      break;
                  }
                  case 6:{
                      exit();
                      break;
                  }
              }
            }  
        }
        catch(ClassPersistenceException | ClassNotFoundOrderException | IOException | ClassDuplicateOrderException | ClassInvalidDataException | ClassNotFoundException e){
            
        }
        
        
    }

    private void exit() {          
        io.displayMessageEnd();
        System.exit(0);
    }

    private void displayOrders(){
        ArrayList<Order> list = null;
        LocalDate date = io.getDate();
        
        try{
            list = (ArrayList<Order>) service.getAllOrders(date);
            io.displayAllOrders(list);
        }
        catch(ClassPersistenceException | ClassNotFoundOrderException | IOException e){
            io.displayErrorMessage("ERROR. " + e.getMessage());
        }
        
    }

    private void createOrder() throws ClassPersistenceException, FileNotFoundException, 
            IOException, ClassDuplicateOrderException, ClassInvalidDataException, 
            ClassNotFoundOrderException, ClassNotFoundException {
        try{
            io.displayAddOrderBanner();
            LocalDate dateOrder = io.getDate();
            
            if(service.validateDate(dateOrder)){
                
                //Get customer Name
                String customerName;
                do{
                    customerName = io.getCustomerName(); 
                }while(!service.validateCustomerName(customerName));
                
                //Get State
                String abrevUser = "";
                State state;
                do{
                    List<State> listStates = service.getAllState();
                    abrevUser = io.displayAllStates((ArrayList<State>) listStates);
                    state = service.getState(abrevUser);
                }while(state == null);
                BigDecimal taxRate = service.getTaxRate(abrevUser); 
                taxRate = taxRate.setScale(2, RoundingMode.HALF_UP);
                
                //Showing the list of Products for choose  
                String prodT = "";
                Product prod;

                //Validating Products
                do{
                    List<Product> listproducts = service.getAllProducts();
                    prodT = io.displayAllProducts((ArrayList<Product>) listproducts);
                    prod = service.getProduct(prodT);
                }while(prod == null);

                //Validating Area
                BigDecimal area = io.getArea();
                area = area.setScale(2, RoundingMode.HALF_UP);
                int id = service.getIdOrder();
                
                Order newOrder = new Order(id, customerName, abrevUser, taxRate, prod, area);

                Order order = service.createOrder(newOrder, dateOrder);
                if(order != null){
                    io.displayAddSuccessSuccessfully();
                }  
            }            
        }
        catch(ClassPersistenceException | FileNotFoundException e){
            io.displayErrorMessage("ERROR. " + e.getMessage());
        }
        catch(IOException | ClassDuplicateOrderException | ClassInvalidDataException e){
            io.displayErrorMessage("ERROR. " + e.getMessage());
        }
    }

    private void editOrder() throws ClassPersistenceException, IOException, ClassInvalidDataException {
        io.displayEditOrderBanner();
        //the system should ask for the date and order number
        //Get date and validate
        LocalDate date = io.getDate();
        //Get order Number and validate
        Order order = null;
        int orderNumber = io.getOrderNumber();
        try{
            order = service.getOrder(orderNumber, date);
        }
        catch(FileNotFoundException e){
            io.displayErrorMessage("ERROR " + e.getMessage());
        }
       
        if(order != null){  
            //Validatin Product
            Product newProd = null;
            String productType = io.editProductType(order);
            try{
                if(!productType.isBlank()){
                    //Validating the product type
                    newProd = service.getProduct(productType);                
                    if(newProd != null){
                       order.setProduct(newProd); 
                    }                                
                } 
            }catch(FileNotFoundException e){
                io.displayErrorMessage("ERROR " + e.getMessage());
            }
            String newCustomerName = io.editOrderNewName(order.getCustomerName());
            try{
                if(!newCustomerName.isBlank()){
                    if(service.validateCustomerName(newCustomerName)){
                        order.setCustomerName(newCustomerName);
                    }                    
                }
            }catch(ClassInvalidDataException e){
                io.displayErrorMessage("ERROR " + e.getMessage());
            }
            
            String newState = io.editOrderNewState(order.getStateAbrev());
            try{
                if(!newState.isBlank()){
                    if(service.getState(newState) != null){
                        order.setStateAbrev(newState);
                    }                    
                }
            }catch(FileNotFoundException e){
                io.displayErrorMessage("ERROR " + e.getMessage());
            }
            
            String area = io.editOrderNewArea(order.getArea().toString());
            try{
                if(!area.isBlank()){
                    BigDecimal newArea = new BigDecimal(area);//BigDecimal.ONE;
                    service.validateArea(newArea); //!= null){
                    order.setArea(newArea); 
                }   
            }catch(ClassInvalidDataException e){
                io.displayErrorMessage("ERROR " + e.getMessage());
            }
            //Displaying summary of the new Order
            io.displaySummaryOrder(order); 

            //Ask for confirmation
            String option = io.askUserConfirmation();
            if(option.equalsIgnoreCase("Y")){   
                Order orderEdited = service.editOrder(order, date);
                //service.editOrder(order, date);
                if(orderEdited != null){
                    io.displayEditSuccessSuccessfully();
                }
            }               
        }        
    }

    private void removeOrder() throws ClassInvalidDataException, ClassPersistenceException, IOException {
        io.displayRemoveOrderBanner();
        //Get date and validate
        LocalDate date = io.getDate();
        //Get order Number and validate
        int orderNumber = io.getOrderNumber();
        Order order = service.getOrder(orderNumber, date);
       
        if(order != null){            
            io.displaySummaryOrder(order);
            String option = io.askUserConfirmation();
            
            if(option.equalsIgnoreCase("Y")){
                try{
                    Order orderDeleted = service.removeOrder(date, orderNumber);

                    if(orderDeleted != null){
                        io.displayremoveResult(orderDeleted);
                    }
                }
                catch(ClassPersistenceException e){
                    io.displayErrorMessage("ERROR. " + e.getMessage());
                }
            }
        }   
        
    }

    private void exportAllData() throws IOException, ClassPersistenceException, ClassNotFoundException {
        boolean var = service.exportData();
        if(var){
            io.displayExportDatasuccessfully();
        }else{
            io.displayErrorMessage("Error exporting datas");
        }
    }
}

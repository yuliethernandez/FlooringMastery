package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClassFlooringUserView {
    private UserIO io;

    public ClassFlooringUserView(UserIOImplementation io) {
        this.io = io;
    }
    
    public int mainMenuUserStories(){
        String out = 
                "  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
                "  * <<Flooring Program>>\n" +
                "  * 1. Display Orders\n" +
                "  * 2. Add an Order\n" +
                "  * 3. Edit an Order\n" +
                "  * 4. Remove an Order\n" +
                "  * 5. Export All Data\n" +
                "  * 6. Quit\n" +
                "  *\n" +
                "  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
                "Choose an option between ";
        return io.readInt(out, 1, 6);
    }
    
    public void displayAllOrders(ArrayList<Order> list){
        io.print("\n* * * Display All Orders * * *");
        String head = String.format(
                "%20s | %20s | %10s | %10s |%10s | %10s | %10s | %20s | %20s | %20s | %10s | %10s%n", 
                "Order's Number",
                "Customer's Name",
                "State",
                "Tax Rate",
                "Product", 
                "Area",
                "Cost/Square",
                "Labor Cost/Square",
                "Material Cost",
                "LaborCost",
                "Tax",
                "Total");
        io.print(head);
        list.stream().forEach(
                o -> System.out.printf(
                "%20s | %20s | %10s | %10s |%10s | %10s | %10s | %20s | %20s | %20s | %10s | %10s%n", 
                o.getOrderNumber(), 
                o.getCustomerName(), 
                o.getStateAbrev(),
                o.getTaxRate(),
                o.getProduct().getProductType(), 
                o.getArea(),
                o.getProduct().getCostPerSquareFoot(),
                o.getProduct().getLaborCostPerSquareFoot(),
                o.getMaterialCost(),
                o.getLaborCost(), 
                o.getTax(),
                o.getTotal()));
        io.readString("\nPlease hit enter to continue.");
    }
    
    public String displayAllStates(ArrayList<State> list){
        io.print("\n* * * STATES * * *");
        String head = String.format("%10s | %20s | %20s" , 
                "State Abbreviation",
                "State Name",
                "Tax Rate");
        io.print(head);
        list.stream().forEach(s -> System.out.printf("%10s | %20s | %20s%n", 
                s.getStateAbbreviation(), 
                s.getStateName(), 
                s.getTaxRate()));
        return io.readString("Choose an State Abbreviation from the list: ");
    }
    
    public String displayAllProducts(ArrayList<Product> list){
        io.print("\n* * * PRODUCTS * * *");
        String head = String.format("%10s | %20s | %20s" , 
                "Product Type",
                "Cost Per Square Foot",
                "Labor Cost Per Square Foot");
        io.print(head);
        list.stream().forEach(p -> System.out.printf("%10s | %20s | %20s%n", 
                p.getProductType(), 
                p.getCostPerSquareFoot(), 
                p.getLaborCostPerSquareFoot()));
        return io.readString("Choose an type of product from the list: ");
    }
    
    /*public Order editOrder(Order order){        
        String name = io.readString("Enter customer name (" + order.getCustomerName() + "):");
        if(!name.isBlank()){
            order.setCustomerName(name);
        }
        
        String state = io.readString("Enter state abreviation (" + order.getStateAbrev() + "):");
        if(!state.isBlank()){
            order.setStateAbrev(state);
        }
        BigDecimal newArea = BigDecimal.ONE;
        String area = io.readString("Enter the area (" + order.getArea() + "):");   
        if(!area.isBlank()){
            newArea = new BigDecimal(area);
            newArea = newArea.setScale(2, RoundingMode.HALF_UP);
            order.setArea(newArea);
        }
        return order;
    }*/
    
    public String editProductType(Order order){        
        String productType = io.readString("Enter the product type (" + order.getProduct().getProductType() + "):");
        return productType;
    }
    
    public String askUserConfirmation(){
        return io.readString("Please type Y for confirm the operation, or N for cancel: ");
    }
    
    public void displayAddOrderBanner(){
        io.print("= = =  ADD ORDER = = = ");
    }
    
    public void displayEditOrderBanner(){
        io.print("= = = EDIT ORDER = = = ");
    }
    
    public void displayRemoveOrderBanner(){
        io.print("= = = REMOVE ORDER = = = ");
    }

    public void displaySummaryOrder(Order order){
        io.print("* * * SUMMARY ORDER * * *");
        String head = String.format(
                "%20s | %20s | %10s | %10s |%10s | %10s | %10s | %20s | %20s | %20s | %10s | %10s%n", 
                "Order's Number",
                "Customer's Name",
                "State",
                "Tax Rate",
                "Product", 
                "Area",
                "Cost/Square",
                "Labor Cost/Square",
                "Material Cost",
                "LaborCost",
                "Tax",
                "Total");
        io.print(head);
        String orderSummary = String.format(
                "%20s | %20s | %10s | %10s |%10s | %10s | %10s | %20s | %20s | %20s | %10s | %10s%n", 
                order.getOrderNumber(), 
                order.getCustomerName(), 
                order.getStateAbrev(),
                order.getTaxRate(),
                order.getProduct().getProductType(), 
                order.getArea(),
                order.getProduct().getCostPerSquareFoot(),
                order.getProduct().getLaborCostPerSquareFoot(),
                order.getMaterialCost(),
                order.getLaborCost(), 
                order.getTax(),
                order.getTotal());
        io.print(orderSummary);
    }
    
    public void displayremoveResult(Order order){
        io.print("\nThe order " + order.getOrderNumber() + " has been successfully removed");
    }
    
    public int getOrderNumber(){
        return io.readInt("Enter the Order's number: ");
    }
    public BigDecimal getArea(){
        //return new BigDecimal(io.readString("Enter the area: "));
        return io.readArea("Enter the area: ");
    }

    public String getState(){
        return io.readString("Enter the State: ");
    }
    public String getProductType(){
        return io.readString("Enter the type of the product: ");
    }
    public String getCustomerName(){
        return io.readString("Enter the Customer's name: ");
    }
    
    public void displayAddSuccessSuccessfully() {
        io.readString("Order successfully created.  Please hit enter to continue");
    }
    
    public void displayEditSuccessSuccessfully() {
        io.readString("Order successfully edited.  Please hit enter to continue");
    }
    
    public void displayExportDatasuccessfully() {
        io.readString("Datas successfully saved.  Please hit enter to continue");
    }
    
    public void displayMessageEnd(){
        io.print("\n= = =  PROGRAM FINISHED = = = \n");
    }
    
    public void displayErrorMessage(String message){
        io.print("\n=== " + message + "=== \n");
    }

    public LocalDate getDate() {
        return io.readDate("Enter the date in format [MM-DD-YYYY]: ");
    }

    public String editOrderNewName(String name){        
        String newName = io.readString("Enter customer name (" + name + "):");      
        return newName;
    }
    public String editOrderNewState(String state){      
        String newState = io.readString("Enter state abreviation (" + state + "):");
        return newState;
    }
    public String editOrderNewArea(String area){        
        String newArea = io.readString("Enter the area (" + area + "):");   
        return area;
    }
}

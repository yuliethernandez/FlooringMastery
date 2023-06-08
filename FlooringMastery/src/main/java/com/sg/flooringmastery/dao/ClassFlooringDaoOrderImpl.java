package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ClassFlooringDaoOrderImpl implements ClassFlooringDaoOrder  {
    
    public String FILE_ORDERS = "Files\\Orders\\" + this.getNameFileOrderToday();
    public final String ORDER_FILE_HEADER = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
    public static final String DELIMITER = ",";    
    private Map<Integer, Order> listOrder = new HashMap<>();
    private final String EXPORT_FILE = "Files\\Backup\\DataExport.txt";
    private ClassFlooringDaoWriteEntry audit = new ClassFlooringDaoWriteEntryImpl();
    
    public ClassFlooringDaoOrderImpl(){
    }
    
    public ClassFlooringDaoOrderImpl(String fileOrders){
        this.FILE_ORDERS = fileOrders;
    }    

    @Override
    public Order createOrder(Order order) throws ClassPersistenceException, IOException {
        loadFileOrdersToday();
        Order newOrder = listOrder.put(order.getOrderNumber(), order);
        writeFileOrder(order);
        audit.writeEntry("The Order " + order.getOrderNumber() + " have been add.");
        return newOrder;
    }

    @Override
    public List<Order> getAllOrdersByDate(LocalDate date) throws ClassPersistenceException, FileNotFoundException, IOException {
        loadFileOrdersByDate(date);
        return listOrder.values().stream()
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    
    public List<Order> getAllOrders() throws ClassPersistenceException, FileNotFoundException, IOException {
        loadFileOrders();
        return listOrder.values().stream()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Order editOrder(Order order) throws ClassPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws ClassPersistenceException, IOException {
        loadFileOrdersByDate(date);
        /*for(Order o: listOrder.values()){
            System.out.println("The object is " + o.getCustomerName());            
        }*/
        Order order = listOrder.remove(orderNumber);
        System.out.println("order deleted " + order.getCustomerName());
        String formatted = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        String dirFile = "Files\\Orders\\Orders_" + formatted.replaceAll("-", "")+".txt";
        writeFileOrderFile(dirFile);
        
        return order;
    }

    @Override
    public Order getOrder(int orderNumber) throws ClassPersistenceException, FileNotFoundException, IOException {
        loadFileOrders();
        return listOrder.get(orderNumber);
    }

    private void writeFileOrder(Order order) throws ClassPersistenceException, FileNotFoundException, IOException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(FILE_ORDERS, true));
            
        } catch (IOException e) {
            throw new ClassPersistenceException(
                    "Could not save order data.", e);
        }        
        String orderAsText;
        //List<Order> orderList = this.getAllOrders();

        //for (Order currentOrder: listOrder.values()) {
            orderAsText = marshallOrder(order);
            out.println(orderAsText);
            out.flush();
            out.close();
        //}
    }
    
    private void writeFileOrderFile(String file) throws ClassPersistenceException, FileNotFoundException, IOException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(file));
            
        } catch (IOException e) {
            throw new ClassPersistenceException(
                    "Could not save order data.", e);
        }        
        String orderAsText;
        out.println(ORDER_FILE_HEADER);

        for (Order currentOrder: listOrder.values()) { 
            System.out.println("Order marshalling " + currentOrder.getCustomerName());
            orderAsText = marshallOrder(currentOrder);
            out.println(orderAsText);
            out.flush();
            out.close();
        }
    }
        
    private void loadFileOrdersToday() throws IOException, ClassPersistenceException{
        Scanner scanner;
        try{
            // Create Scanner for reading the file
            scanner = new Scanner(new BufferedReader(new FileReader(FILE_ORDERS)));
            // currentLine holds the most recent line read from the file
            String currentLine;
            // currentOrder holds the most recent order unmarshalled
            Order currentOrder;
            currentLine = scanner.nextLine();

            while (scanner.hasNextLine()) {
                // get the next line in the file
                currentLine = scanner.nextLine();
                // unmarshall the line into a Order
                currentOrder = unmarshallOrder(currentLine);

                listOrder.put(currentOrder.getOrderNumber(), currentOrder);
            }
            // close scanner
            scanner.close();
        }catch(IOException e){
            PrintWriter out = new PrintWriter(new FileWriter(FILE_ORDERS, true));
            out.println(ORDER_FILE_HEADER);
            out.close();
        }
        
    }

    private Order unmarshallOrder(String orderAsText) throws ClassPersistenceException, FileNotFoundException{
        String[] orderTokens = orderAsText.split(DELIMITER);

        // Given the pattern above, the student Id is in index 0 of the array.
        int orderNumber = Integer.parseInt(orderTokens[0]);        
        String customerName= orderTokens[1];
        
        //class State
        String stateAbrev = orderTokens[2];    
        //class taxRate
        BigDecimal taxRate= new BigDecimal(orderTokens[3]);
        taxRate= taxRate.setScale(2, RoundingMode.HALF_UP);

        //class Product
        String productType = orderTokens[4];            
        BigDecimal costPerSquareFoot = new BigDecimal(orderTokens[6]);
        costPerSquareFoot= costPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal laborCostPerSquareFoot= new BigDecimal(orderTokens[7]);
        laborCostPerSquareFoot= laborCostPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
                
        Product productObj = new Product(productType, costPerSquareFoot, laborCostPerSquareFoot);
        //OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot, LaborCostPerSquareFoot, MaterialCost, LaborCost,Tax, Total
        BigDecimal area = new BigDecimal(orderTokens[5]);        
  
        BigDecimal materialCost= new BigDecimal(orderTokens[8]);
        materialCost= costPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal laborCost = new BigDecimal(orderTokens[9]);
        costPerSquareFoot= costPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal tax= new BigDecimal(orderTokens[10]);
        costPerSquareFoot= costPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal total= new BigDecimal(orderTokens[11]);
        costPerSquareFoot= costPerSquareFoot.setScale(2, RoundingMode.HALF_UP);

        Order orderFromFile = new Order(orderNumber, customerName, stateAbrev, taxRate, productObj, 
                area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, total);

        return orderFromFile;
    }
    
    private void loadFileOrdersByDate(LocalDate dateFile) throws ClassPersistenceException, FileNotFoundException, IOException {
        
        String s = dateFile.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")).replace("-", "");
        String fileDate = "Files\\Orders\\Orders_" + s + ".txt";
        Scanner scanner;

        // Create Scanner for reading the file
        scanner = new Scanner(new BufferedReader(new FileReader(fileDate)));
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentOrder holds the most recent order unmarshalled
        Order currentOrder;
        currentLine = scanner.nextLine();

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentOrder = unmarshallOrder(currentLine);
            listOrder.put(currentOrder.getOrderNumber(), currentOrder);
        }
        scanner.close();
        
    }
    
    private void loadFileOrders() throws ClassPersistenceException, FileNotFoundException, IOException {
        
        ArrayList<String> arrayListOrderFromFile = new ArrayList<>();
        //inner class
        class readFilesOrder extends SimpleFileVisitor<Path>{
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                BufferedReader bufferedReader = Files.newBufferedReader(file);
                String line = bufferedReader.readLine();
                while (line != null) {
                    arrayListOrderFromFile.add(line);
                    line = bufferedReader.readLine();
                }

                return FileVisitResult.CONTINUE;
            }
        }
        Files.walkFileTree(Paths.get("Files\\Orders\\"), 
                   EnumSet.noneOf(FileVisitOption.class),
                   1, //deep
                   new readFilesOrder());//class that extend the abstract class of FileVisitor interface
        
        for(String s: arrayListOrderFromFile){
            if(!s.contains("OrderNumber")){
                Order order = unmarshallOrder(s);
                listOrder.put(order.getOrderNumber(), order);
            }
        }
    }
    
    private String marshallOrder(Order order){
        String orderAsText = order.getOrderNumber() + DELIMITER;
        // adding the rest of the properties in the correct order:
        // CustomerName
        orderAsText += order.getCustomerName() + DELIMITER;
        // State
        orderAsText += order.getStateAbrev() + DELIMITER;
        // TaxRate
        orderAsText += order.getTax()+ DELIMITER;
        //ProductType
        orderAsText += order.getProduct().getProductType() + DELIMITER;
        //Area
        orderAsText += order.getArea() + DELIMITER;        
        //CostPerSquareFoot
        orderAsText += order.getProduct().getCostPerSquareFoot() + DELIMITER;
        //LaborCostPerSquareFoot
        orderAsText += order.getProduct().getLaborCostPerSquareFoot() + DELIMITER;
        //MaterialCost
        orderAsText += order.getMaterialCost() + DELIMITER;
        //LaborCost
        orderAsText += order.getLaborCost() + DELIMITER;
        //Tax
        orderAsText += order.getTax() + DELIMITER;
        //Total
        orderAsText += order.getTotal();

        // We return the Order as String
        return orderAsText;
    }
     
    public String getNameFileOrderToday(){
        
        LocalDate today = LocalDate.now();
        String formatted = today.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        String file = "Orders_" + formatted.replaceAll("-", "")+".txt";
        
        return file;
    }

    /* public ArrayList<Order> loadAllOrder() throws ClassNotFoundException, ClassPersistenceException, FileNotFoundException {
       ArrayList<Order> orderList = new ArrayList<Order>();
        Scanner scanner = null;

        File orderTextFile;
        File file = new File("Files/Orders/");
        // go through Order Folder and read files one by one
        for (File currentFile : file.listFiles()) {
            orderTextFile = currentFile;

            try {
                scanner = new Scanner(new BufferedReader(new FileReader(orderTextFile)));
            } catch (FileNotFoundException e) {
                throw new ClassNotFoundException("Could not load Orders into memory", e);
            }
            String currentLine;
            Order currentOrder;

            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                currentOrder = unmarshallOrder(currentLine);
                orderList.add(currentOrder);
            }
        }
        //close Scanner
        scanner.close();
        return orderList;
    }*/
    @Override
    public boolean exportAllData() throws ClassPersistenceException, IOException, FileNotFoundException, ClassNotFoundException{

        /*PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(EXPORT_FILE, false));
        } catch (IOException e) {
            throw new ClassPersistenceException("Could not load the file's data.", e);
        }
        Scanner sc;
        try{
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(EXPORT_FILE))
            );
        }catch(FileNotFoundException e){
            throw new ClassPersistenceException("Could not load the file's data.", e);
        }
        if(sc.hasNextLine()){
            sc.nextLine();
        }else{
            out.println(ORDER_FILE_HEADER);
        }
        String currentLine;
        List<Order> orderList = this.getAllOrders();

        for (Order currentOrder: orderList) { 
            currentLine = marshallOrder(currentOrder);
            out.println(currentLine);
            out.flush();                
        }
        out.close();            
        return true;*/
        File[] listFile = new File("Files/Orders").listFiles();
        System.out.println("array de file en 0" + listFile[0]);
        try{
            if(listFile != null){
                
                PrintWriter out;
                try {
                    out = new PrintWriter(new FileWriter(EXPORT_FILE, true));
                } catch (IOException e) {
                    throw new ClassPersistenceException("Could not load the file's data.", e);
                }
                
                out.println(ORDER_FILE_HEADER);
                
                for(File file: listFile){
                    Scanner sc;
                    try{
                        sc = new Scanner(
                                new BufferedReader(
                                        new FileReader(file))
                        );
                    }catch(FileNotFoundException e){
                        throw new ClassPersistenceException("Could not load the file's data.", e);
                    }
                    if(sc.hasNextLine()){
                        sc.nextLine();
                    }
                    String currentLine;
                    String dateString = "";
                    while(sc.hasNextLine()){
                        currentLine = sc.nextLine();
                        //String dateString = file.getName().substring(7, 15);
                        /*String str = file.getName().substring(7, 15);
                        char s[] = str.toCharArray();
                        for(int i = 0; i < s.length; i++){
                            
                        }*/     
                        /*public static void main(String[] args) {  
                        String s1="Javatpoint";    
                        String substr = s1.substring(0); // Starts with 0 and goes to end  
                        System.out.println(substr);  
                        String substr2 = s1.substring(5,10); // Starts from 5 and goes to 10  
                        System.out.println(substr2);    
                        String substr3 = s1.substring(5,15); // Returns Exception  
                        }  
                        Javatpoint
                        point
                        Exception in thread "main" java.lang.StringIndexOutOfBoundsException: begin 5, end 15, length 10
                                                */
                        //06072023
                        dateString = file.getName().substring(7, 15);
                        System.out.println("date is " + dateString);
                        
                        String year = dateString.substring(4);
                        System.out.println("year is " + year);
                        System.out.println("date is " + dateString);
                        
                        String day = dateString.substring(2, 4);
                        System.out.println("day is " + day);
                        
                        String month= dateString.substring(0, 2);
                        System.out.println("month is " + month);
                        
                        String date = month + "-" + day + "-" + year;
                        System.out.println("date of file " + date + "\n");
                        out.println(currentLine + ", " + date);                         
                    }
                    sc.close(); 
                }   
                out.flush();
                out.close();      
        }
        }catch(ClassPersistenceException e){
            throw new ClassPersistenceException("Could not find the directory");
        }
        return true;       
    }    
}
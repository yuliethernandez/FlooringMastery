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
    
    private String FILE_ORDERS;
    private final String ORDER_FILE_HEADER = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
    private static final String DELIMITER = ",";    
    private Map<Integer, Order> listOrder = new HashMap<>();
    private String EXPORT_FILE;
    
    public ClassFlooringDaoOrderImpl(){
        this.FILE_ORDERS = "Files\\Orders\\";
        this.EXPORT_FILE = "Files\\Backup\\DataExport.txt";
    }
    
    public ClassFlooringDaoOrderImpl(String fileOrders, String fileExport){
        this.FILE_ORDERS = fileOrders;
        this.EXPORT_FILE = fileExport;
    }   
    public ClassFlooringDaoOrderImpl(String fileOrders){
        this.FILE_ORDERS = fileOrders;
    } 

    @Override
    public Order createOrder(Order order, LocalDate dateOrder) throws ClassPersistenceException, IOException {
        int orderNumer = getNextId();
        listOrder = new HashMap<>();
        //Order newOrder = null;
        String file = this.getFileDirByDate(dateOrder);
        try{
            loadFileOrdersByDate(dateOrder);            
        }catch(ClassPersistenceException | IOException e){
        }
        Order newOrder = listOrder.put(orderNumer, order);  
        writeFileOrderFile(file);
        return order;
    }

    @Override
    public List<Order> getAllOrdersByDate(LocalDate date) throws ClassPersistenceException, FileNotFoundException, IOException {
        loadFileOrdersByDate(date);
        List<Order> listByDate = listOrder.values().stream()
                .collect(Collectors.toCollection(ArrayList::new));
        listOrder = new HashMap<>();
        return listByDate;
    }

    @Override
    public Order editOrder(Order order, LocalDate dateOrder) throws ClassPersistenceException, IOException {
        loadFileOrdersByDate(dateOrder);        
        Order newOrder = listOrder.put(order.getOrderNumber(), order);  
        writeFileOrderFile(getFileDirByDate(dateOrder));
        return newOrder;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws ClassPersistenceException, IOException {
        //loadFileOrdersByDate(date);
        /*for(Order o: listOrder.values()){
            System.out.println("The object is " + o.getCustomerName());            
        }*/
        Order order = listOrder.remove(orderNumber);
        String formatted = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        String dirFile =  FILE_ORDERS +"Orders_" + formatted.replaceAll("-", "")+".txt";
        writeFileOrderFile(dirFile);
        //listOrder = new HashMap<>();
        return order;
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate dateOrder) throws ClassPersistenceException, FileNotFoundException, IOException {
        try{
            loadFileOrdersByDate(dateOrder);
        }catch(ClassPersistenceException e){
            throw new ClassPersistenceException("There is not order in the files with such a number");
        }
        Order order = listOrder.get(orderNumber);        
        //listOrder = new HashMap<>();
        return order;
    }
    
    @Override
    public Order getOrder(int orderNumber) throws ClassPersistenceException, FileNotFoundException, IOException {        
        loadFileOrders();        
        Order order = listOrder.get(orderNumber);        
        listOrder = new HashMap<>();
        return order;
    }
    
    private void writeFileOrderFile(String file) throws ClassPersistenceException, FileNotFoundException, IOException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(file, false));            
        } catch (IOException e) {
            throw new ClassPersistenceException(
                    "Could not save order data.", e);
        }
        String orderAsText;
        out.println(ORDER_FILE_HEADER);

        for (Order currentOrder: listOrder.values()) { 
            orderAsText = marshallOrder(currentOrder);
            out.println(orderAsText);                        
        }
        out.flush();
        out.close();
        listOrder = new HashMap<>();
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
        
        //LocalDate dateOrder = new BigDecimal(orderTokens[11]);

        Order orderFromFile = new Order(orderNumber, customerName, stateAbrev, taxRate, productObj, 
                area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, total);

        return orderFromFile;
    }
    
    private void loadFileOrdersByDate(LocalDate dateFile) throws ClassPersistenceException, FileNotFoundException, IOException {
        
        //String s = dateFile.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")).replace("-", "");
        //String fileDate = "Files/Orders/Orders_" + s + ".txt";
        listOrder = new HashMap<>();
        String fileDate = getFileDirByDate(dateFile);
        Scanner scanner;

        // Create Scanner for reading the file
        scanner = new Scanner(
                new BufferedReader(
                        new FileReader(fileDate)));
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentOrder holds the most recent order unmarshalled
        Order currentOrder;
        currentLine = scanner.nextLine();//For the HEADER

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentOrder = unmarshallOrder(currentLine);
            listOrder.put(currentOrder.getOrderNumber(), currentOrder);
        }
        scanner.close();        
    }
    
    private String getFileDirByDate(LocalDate dateFile){
        String s = dateFile.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")).replace("-", "");
        String file = FILE_ORDERS + "Orders_" + s + ".txt";
        return file;
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
        orderAsText += order.getTaxRate()+ DELIMITER;
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
  
    @Override
    public boolean exportAllData() throws ClassPersistenceException, IOException, FileNotFoundException, ClassNotFoundException{

        File[] listFile = new File(FILE_ORDERS).listFiles();
        try{
            if(listFile != null){                
                PrintWriter out;
                try {
                    out = new PrintWriter(new FileWriter(EXPORT_FILE, false));
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
                        dateString = file.getName().substring(7, 15);
                        
                        String year = dateString.substring(4);                        
                        String day = dateString.substring(2, 4);                        
                        String month= dateString.substring(0, 2);
                        
                        String date = month + "-" + day + "-" + year;
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
        
    private List<Order> getAllOrders() throws ClassPersistenceException, FileNotFoundException, IOException {
        loadFileOrders();
        List<Order> listAllOrder = listOrder.values().stream()
                .collect(Collectors.toCollection(ArrayList::new));
        
        return listAllOrder;
        
    }
    
    @Override
    public int getNextId() throws ClassPersistenceException, IOException{
        List<Order> listAllOrder = getAllOrders();
        Order lastOrder = null;
        int nextOrderNumber = 1;
        if(!listAllOrder.isEmpty()){
            int posLastOrder = listAllOrder.size();
            posLastOrder --;
            lastOrder = listAllOrder.get(posLastOrder); 
            nextOrderNumber = lastOrder.getOrderNumber();
            nextOrderNumber++;
        }
        return nextOrderNumber;        
    }
 /* private String getNameFileOrderToday(){
        
        LocalDate today = LocalDate.now();
        String formatted = today.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        String file = "Orders_" + formatted.replaceAll("-", "")+".txt";
        
        return file;
    }*/
    /*private void loadFileOrdersToday() throws IOException, ClassPersistenceException{
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
        
    }*/
    /*private void writeFileOrder(Order order) throws ClassPersistenceException, FileNotFoundException, IOException {
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
    }*/

}
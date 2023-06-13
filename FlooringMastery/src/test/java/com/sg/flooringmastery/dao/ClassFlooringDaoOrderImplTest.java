
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClassFlooringDaoOrderImplTest {
    private ClassFlooringDaoOrder daoOrder;
    LocalDate date = LocalDate.now();
    Product pro = new Product("Brick", new BigDecimal("15.00"), new BigDecimal("12.00"));
    
    public ClassFlooringDaoOrderImplTest() throws IOException {
        String testFile = "FilesTest\\Orders\\";        
        daoOrder = new ClassFlooringDaoOrderImpl(testFile);
    }
    
    @BeforeEach
    public void setUp() throws IOException {  
        //String testFile = "FilesTest\\Orders\\";
        //daoOrder = new ClassFlooringDaoOrderImpl(testFile);
    }

    @Test
    public void testCreateOrder() throws Exception {        
        //String testFile = "FilesTest\\Orders\\";
        //daoOrder = new ClassFlooringDaoOrderImpl(testFile);
        //new FileWriter(testFile);
        // Create Order test
        Order order = new Order(100, //int orderNumber
                "Marybel L",  //String customerName,
                "CU",  //String stateAbrev,
                new BigDecimal("5.00"),//BigDecimal taxRate,
                pro, //Product product,
                new BigDecimal("5.00"),//costPerSquareFoot, 
                new BigDecimal("35.00"),//laborCostPerSquareFoot, 
                new BigDecimal("24.00"),//materialCost, 
                new BigDecimal("25.00"),//laborCost, 
                new BigDecimal("25.00"),//tax, 
                new BigDecimal("50.00"),//total);
                new BigDecimal("500"));//BigDecimal area

        // call addOrder
        Order allOrder = daoOrder.createOrder(order, date);

        // Assert
        assertEquals(order, allOrder, "Order should be successfully added");
    }

    @Test
    public void testGetAllOrdersByDate() throws Exception {
        //String testFile = "FilesTest/testFlooring.txt";
        //String testFile = "FilesTest\\Orders\\";
        //daoOrder = new ClassFlooringDaoOrderImpl(testFile);
        
        Order order1 = new Order(101, //int orderNumber
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
        Order order2 = new Order(102, //int orderNumber
                "Mary",  //String customerName,
                "CU",  //String stateAbrev,
                new BigDecimal("5.00"),//BigDecimal taxRate,
                pro, //Product product,
                new BigDecimal("5.00"),//costPerSquareFoot, 
                new BigDecimal("35.00"),//laborCostPerSquareFoot, 
                new BigDecimal("24.00"),//materialCost, 
                new BigDecimal("25.00"),//laborCost, 
                new BigDecimal("25.00"),//tax, 
                new BigDecimal("50.00"),//total);
                new BigDecimal("500"));//BigDecimal area
        Order o1 = daoOrder.createOrder(order1, date);
        Order o2 = daoOrder.createOrder(order2, date);
        // put all the products into a list for comparison
        ArrayList<Order> expectedResult = new ArrayList<>();
        expectedResult.add(order1);
        expectedResult.add(order2);

        // get a list of the actual products
        List<Order> result = daoOrder.getAllOrdersByDate(date);
        assertTrue(expectedResult.contains(o1), "Don't contain Order1");
        assertTrue(expectedResult.contains(o2), "Don't contain Order2");
        assertEquals(2, expectedResult.size(), "The size is not 2");
    }

    @Test
    public void testRemoveOrder() throws Exception {
        //String testFile = "FilesTest\\Orders\\";
        //String testFile = "FilesTest/testFlooring.txt";
        //daoOrder = new ClassFlooringDaoOrderImpl(testFile);
        
        Order order1  = new Order(101, //int orderNumber
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
        daoOrder.createOrder(order1, date);
        // ACT & ASSERT
        Order shouldBeMary = daoOrder.removeOrder(date, order1.getOrderNumber());
        assertNull( shouldBeMary, "Removing 101 should be not null.");

        Order shouldBeNull = daoOrder.removeOrder(date, 200);    
        assertNull( shouldBeNull, "Removing 200 should be null.");
       
    }

    @Test
    public void testGetOrderLocalDate() throws Exception {
        //String testFile = "FilesTest\\Orders\\";
        //String testFile = "FilesTest/testFlooring.txt";
        //new FileWriter(testFile, true);
        //daoOrder = new ClassFlooringDaoOrderImpl(testFile);
        
         // Create Order1 and order2 test
        Order order  = new Order(102, //int orderNumber
                "Mary",  //String customerName,
                "CU",  //String stateAbrev,
                new BigDecimal("5.00"),//BigDecimal taxRate,
                pro, //Product product,
                new BigDecimal("5.00"),//costPerSquareFoot, 
                new BigDecimal("35.00"),//laborCostPerSquareFoot, 
                new BigDecimal("24.00"),//materialCost, 
                new BigDecimal("25.00"),//laborCost, 
                new BigDecimal("25.00"),//tax, 
                new BigDecimal("50.00"),//total);
                new BigDecimal("500"));//BigDecimal area
        daoOrder.createOrder(order, date.plusDays(2));
        // Get all the orders
        List<Order> allOrder = daoOrder.getAllOrdersByDate(date.plusDays(2));

        // First check the general contents of the list
        assertNotNull( allOrder, "All orders list should be not null.");
        assertEquals( 1, allOrder.size(), "All orders should only have 1 order.");   

        // Check the contents of the list - it should be empty
        assertFalse(allOrder.isEmpty(), "The retrieved list of orders should be empty.");
    }

    @Test
    public void testExportAllData() throws Exception {
        //String testFile = "FilesTest\\Orders\\";
        String testFile = "FilesTest\\Orders\\";
        String testExport = "FilesTest\\Backup\\DataExport.txt";
        
        daoOrder = new ClassFlooringDaoOrderImpl(testFile, testExport);
        
        boolean varResult = daoOrder.exportAllData();
        assertTrue(varResult, "The result is false");
    }

    @Test
    public void testGetNextId() throws Exception {
        Order order = new Order(103, //int orderNumber
                "Mary",  //String customerName,
                "CU",  //String stateAbrev,
                new BigDecimal("5.00"),//BigDecimal taxRate,
                pro, //Product product,
                new BigDecimal("5.00"),//costPerSquareFoot, 
                new BigDecimal("35.00"),//laborCostPerSquareFoot, 
                new BigDecimal("24.00"),//materialCost, 
                new BigDecimal("25.00"),//laborCost, 
                new BigDecimal("25.00"),//tax, 
                new BigDecimal("50.00"),//total);
                new BigDecimal("500"));//BigDecimal area        
        daoOrder.createOrder(order, date);
        int idNext = daoOrder.getNextId();
        assertEquals(order.getOrderNumber()+1, idNext, "The id should be 151");
    }
      
    @Test
    public void testGetOrder() throws Exception {
        Order order = new Order(150, //int orderNumber
                "Mary",  //String customerName,
                "CU",  //String stateAbrev,
                new BigDecimal("5.00"),//BigDecimal taxRate,
                pro, //Product product,
                new BigDecimal("5.00"),//costPerSquareFoot, 
                new BigDecimal("35.00"),//laborCostPerSquareFoot, 
                new BigDecimal("24.00"),//materialCost, 
                new BigDecimal("25.00"),//laborCost, 
                new BigDecimal("25.00"),//tax, 
                new BigDecimal("50.00"),//total);
                new BigDecimal("500"));//BigDecimal area

        // call addOrder
        daoOrder.createOrder(order, date);
        Order orderGet = daoOrder.getOrder(order.getOrderNumber());
        assertEquals(order.getOrderNumber(), orderGet.getOrderNumber(), 
                "Should be the same order");
    }
}

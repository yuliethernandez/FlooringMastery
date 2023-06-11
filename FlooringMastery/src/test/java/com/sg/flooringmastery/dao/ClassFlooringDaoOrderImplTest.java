
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClassFlooringDaoOrderImplTest {
    private ClassFlooringDaoOrder testDao;
    
    public ClassFlooringDaoOrderImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        String testFile = "testFlooring.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new ClassFlooringDaoOrderImpl(testFile);
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCreateOrder() throws Exception {
        System.out.println("createOrder");
        Order order = null;
        LocalDate dateOrder = null;
        ClassFlooringDaoOrderImpl instance = new ClassFlooringDaoOrderImpl();
        Order expResult = null;
        Order result = instance.createOrder(order, dateOrder);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetAllOrdersByDate() throws Exception {
        System.out.println("getAllOrdersByDate");
        LocalDate date = null;
        ClassFlooringDaoOrderImpl instance = new ClassFlooringDaoOrderImpl();
        List<Order> expResult = null;
        List<Order> result = instance.getAllOrdersByDate(date);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testEditOrder() throws Exception {
        System.out.println("editOrder");
        Order order = null;
        LocalDate dateOrder = null;
        ClassFlooringDaoOrderImpl instance = new ClassFlooringDaoOrderImpl();
        Order expResult = null;
        Order result = instance.editOrder(order, dateOrder);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testRemoveOrder() throws Exception {
        System.out.println("removeOrder");
        LocalDate date = null;
        int orderNumber = 0;
        ClassFlooringDaoOrderImpl instance = new ClassFlooringDaoOrderImpl();
        Order expResult = null;
        Order result = instance.removeOrder(date, orderNumber);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetOrder_int_LocalDate() throws Exception {
        System.out.println("getOrder");
        int orderNumber = 0;
        LocalDate dateOrder = null;
        ClassFlooringDaoOrderImpl instance = new ClassFlooringDaoOrderImpl();
        Order expResult = null;
        Order result = instance.getOrder(orderNumber, dateOrder);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetOrder_int() throws Exception {
        System.out.println("getOrder");
        int orderNumber = 0;
        ClassFlooringDaoOrderImpl instance = new ClassFlooringDaoOrderImpl();
        Order expResult = null;
        Order result = instance.getOrder(orderNumber);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testExportAllData() throws Exception {
        System.out.println("exportAllData");
        ClassFlooringDaoOrderImpl instance = new ClassFlooringDaoOrderImpl();
        boolean expResult = false;
        boolean result = instance.exportAllData();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetAllOrders() throws Exception {
        System.out.println("getAllOrders");
        ClassFlooringDaoOrderImpl instance = new ClassFlooringDaoOrderImpl();
        List<Order> expResult = null;
        List<Order> result = instance.getAllOrders();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetNextId() throws Exception {
        System.out.println("getNextId");
        ClassFlooringDaoOrderImpl instance = new ClassFlooringDaoOrderImpl();
        int expResult = 0;
        int result = instance.getNextId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}

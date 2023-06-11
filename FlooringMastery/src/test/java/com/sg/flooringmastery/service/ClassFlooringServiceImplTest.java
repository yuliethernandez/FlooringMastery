package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.ClassFlooringDaoOrder;
import com.sg.flooringmastery.dao.ClassFlooringDaoOrderImpl;
import com.sg.flooringmastery.dao.ClassFlooringDaoProduct;
import com.sg.flooringmastery.dao.ClassFlooringDaoState;
import com.sg.flooringmastery.dao.ClassFlooringDaoWriteEntry;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

public class ClassFlooringServiceImplTest {
    private ClassFlooringServiceImpl service;
    
    
    public ClassFlooringServiceImplTest() {
        ClassFlooringDaoOrder daoOrder = 
                new ClassFlooringDaoStubImpl();
        ClassFlooringDaoProduct daoProduct = 
                new ClassFlooringProductStubImpl();
        ClassFlooringDaoState daoState = 
                new ClassFlooringStateStubImpl();
        ClassFlooringDaoWriteEntry audit = 
                new ClassFlooringAuditDaoStubImpl();
        
        service = 
                new ClassFlooringServiceImpl(daoOrder, daoProduct, daoState, audit);
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        ClassFlooringDaoOrder daoOrder = 
                new ClassFlooringDaoStubImpl();
        ClassFlooringDaoProduct daoProduct = 
                new ClassFlooringProductStubImpl();
        ClassFlooringDaoState daoState = 
                new ClassFlooringStateStubImpl();
        ClassFlooringDaoWriteEntry audit = 
                new ClassFlooringAuditDaoStubImpl();
        
        service = 
                new ClassFlooringServiceImpl(daoOrder, daoProduct, daoState, audit);
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("Create Valid Order ")
    public void testCreateOrder() throws Exception {        
        // ARRANGE
        //String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot
        Product pro = new Product("Brick", new BigDecimal("15"), new BigDecimal("12"));
        
        //int orderNumber, String customerName, String stateAbrev, BigDecimal taxRate, Product product, BigDecimal area
        Order order = new Order(150, //int orderNumber
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
        assertEquals(order, service.createOrder(order, LocalDate.MAX));
        // ACT
        try {
            service.createOrder(order, LocalDate.MAX);
        } catch (ClassDuplicateOrderException | ClassInvalidDataException e) {
        // ASSERT
            fail("Order was valid. No exception should have been thrown. The exception was: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Create Invalid Order ")
    public void testCreateInvalidOrder() throws Exception {        
        // ARRANGE
        //String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot
        Product pro = new Product("Brick", new BigDecimal("15"), new BigDecimal("12"));
        
        //int orderNumber, String customerName, String stateAbrev, BigDecimal taxRate, Product product, BigDecimal area
        Order order = new Order(150, //int orderNumber
                "",  //String customerName,
                "",  //String stateAbrev,
                new BigDecimal("5"),//BigDecimal taxRate,
                pro, //Product product,
                new BigDecimal("5.00"),//costPerSquareFoot, 
                new BigDecimal("35.00"),//laborCostPerSquareFoot, 
                new BigDecimal("24.00"),//materialCost, 
                new BigDecimal("25.00"),//laborCost, 
                new BigDecimal("25.00"),//tax, 
                new BigDecimal("50.00"),//total);
                new BigDecimal("500"));//BigDecimal area
        // ACT
        assertThrows(ClassInvalidDataException.class , ()-> service.createOrder(order, LocalDate.MAX));
        assertThrows(FileNotFoundException.class , ()-> service.getOrder(150, LocalDate.MAX));
        assertFalse(service.getAllOrders(LocalDate.MAX).contains(order),
                                  "The only order should not containthe empty order");
        //With order number duplicated
        Order order2 = new Order(100, //int orderNumber
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
        assertThrows(ClassDuplicateOrderException.class , ()-> service.createOrder(order2, LocalDate.MAX));
    }
    
    @Test
    public void testGetAllOrders() throws Exception {
        // ARRANGE
        Product pro = new Product("Brick", new BigDecimal("15"), new BigDecimal("12"));
        Order order = new Order(100, //int orderNumber
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
        // ACT & ASSERT
        List<Order> listOrder = service.getAllOrders(LocalDate.MAX);
        assertNotNull(listOrder, "The list of orders must be not null");
        assertEquals(1, listOrder.size(), 
                                       "Should only have one order.");
        assertTrue(listOrder.contains(order),
                                  "The only order should be the order of Mary.");
    }

    @Test
    public void testGetOrder() throws Exception {
        Product pro = new Product("Brick", new BigDecimal("15"), new BigDecimal("12"));
        Order testMary = new Order(100, //int orderNumber
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
        
        assertThrows(FileNotFoundException.class , ()-> service.getOrder(5020, LocalDate.MAX));
        
        // ACT & ASSERT
        Order shouldBeMary = service.getOrder(100, LocalDate.MAX);
        assertNotNull(shouldBeMary, "Getting Mary should be not null.");
        assertEquals( testMary, shouldBeMary,"Order stored as 100 should be Mary.");

    }
  
    @Test
    public void testRemoveOrder() throws Exception {
        // ARRANGE
        //String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot
        Product pro = new Product("Brick", new BigDecimal("15"), new BigDecimal("12"));
        Order testClone  = new Order(100, //int orderNumber
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

        // ACT & ASSERT
        Order shouldBeMary = service.removeOrder(LocalDate.MAX, 100);
        assertNotNull( shouldBeMary, "Removing 100 should be not null.");
        assertEquals( testClone, shouldBeMary, "Order removed from 100 should be Mary.");

        Order shouldBeNull = service.removeOrder(LocalDate.MAX, 200);    
        assertNull( shouldBeNull, "Removing 200 should be null.");
    }

    @Test
    public void testGetProduct() throws Exception {
        Product prodTest = new Product("Brick", new BigDecimal("15"), new BigDecimal("12"));
        //String productType = "Lamp";
        assertThrows(FileNotFoundException.class , ()-> service.getProduct("Lamp"));
        
        // ACT & ASSERT
        Product shouldBeBrik = service.getProduct(prodTest.getProductType());
        assertNotNull(shouldBeBrik, "Getting the product Brick should be not null.");
        assertEquals( prodTest, shouldBeBrik,"product stored as brick should is not correct.");
        
    }

    @Test
    public void testGetState() throws Exception {
        //ARRANGE
        State stateTest = new State("CU", "Cuba", new BigDecimal("5"));
        
        // ACT & ASSERT
        assertThrows(FileNotFoundException.class , ()-> service.getState("UR"));
                
        State shouldBeCU = service.getState(stateTest.getStateAbbreviation());
        assertNotNull(shouldBeCU, "Getting the state CU should be not null.");
        assertEquals( stateTest, shouldBeCU,"State stored as brick is not correct.");
    }

    @Test
    public void testGetAllState() throws Exception {
        // ARRANGE
        State state = new State("CU", "Cuba", new BigDecimal("5"));
        // ACT & ASSERT
        assertEquals(1, service.getAllState().size(), 
                                       "Should only have one state.");
        assertTrue(service.getAllState().contains(state),
                                  "The only state should be CU.");
    }

    @Test
    public void testGetAllProducts() throws Exception {
        // ARRANGE
        Product product = new Product("Brick", new BigDecimal("15"), new BigDecimal("12"));
        
        // ACT & ASSERT
        assertEquals(1, service.getAllProducts().size(), 
                                       "Should only have one order.");
        assertTrue(service.getAllProducts().contains(product),
                                  "The only order should be the order of Mary.");
    }

    @Test
    public void testValidateDate() throws Exception {
        //ARRANGE
        LocalDate dateTestPast = LocalDate.now().minusDays(1);
        LocalDate dateTestToday = LocalDate.now();
        // ACT
        assertThrows(ClassInvalidDataException.class ,()->{
                service.validateDate(dateTestPast);
                service.validateDate(dateTestToday);
                });
        //date TOMORROW
        LocalDate dateTestFuture = LocalDate.now().plusDays(1);
        assertTrue(service.validateDate(dateTestFuture), 
                "The method should return false because the date is in the past");
    }

    @Test
    public void testValidateArea() throws Exception {
        BigDecimal areaTest = new BigDecimal("50");
        // ACT & ASSERT
        assertThrows(ClassInvalidDataException.class , ()-> service.validateArea(areaTest));
    }

    @Test
    public void testValidateCustomerName() throws Exception {
        // ACT & ASSERT
        assertThrows(ClassInvalidDataException.class , ()-> {
            service.validateCustomerName("/");
            service.validateCustomerName("*");
            service.validateCustomerName("-");
            service.validateCustomerName("%");
            service.validateCustomerName(":");
            service.validateCustomerName(";");
            service.validateCustomerName("@");
            service.validateCustomerName("!");
            service.validateCustomerName("&");
            service.validateCustomerName("(");
            service.validateCustomerName("+");
                });
    }

    @Test
    public void testGetTaxRate() throws Exception {
        //ARRANGE
        State stateTest = new State("CU", "Cuba", new BigDecimal("5"));
        // ACT & ASSERT
        assertThrows(ClassNotFoundException.class, ()-> service.getTaxRate("PA"));
        
        assertEquals(stateTest.getTaxRate(), service.getTaxRate("CU"), 
                "The tax Rate is not correct");
        
        assertEquals(stateTest.getStateAbbreviation(), service.getState("CU").getStateAbbreviation(), 
                "The tax Rate is not correct");
    }
 
    @Test
    public void testEditOrder() throws Exception {        
        /*Order order2 = service.getOrder(100, LocalDate.MAX);

        order2.setCustomerName("Marybel");
        order2.setArea(BigDecimal.ONE);

        service.editOrder(order2, LocalDate.MAX);
        //todayDate
        Order orderEdited = service.getOrder(100, LocalDate.MAX);
        assertEquals("Marybel", orderEdited.getCustomerName());
        assertEquals(BigDecimal.ONE, orderEdited.getArea());*/
    }
    
    
    @Test
    public void testExportData() throws Exception {
    }
}

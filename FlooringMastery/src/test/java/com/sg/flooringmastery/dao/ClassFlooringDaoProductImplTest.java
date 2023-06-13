package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.service.ClassFlooringProductStubImpl;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ClassFlooringDaoProductImplTest {
    ClassFlooringDaoProduct daoProduct;
    
    public ClassFlooringDaoProductImplTest() throws IOException {
        String testFile = "FilesTest\\Data\\Products.txt";
        
        // Use the FileWriter to quickly blank the file
        daoProduct = new ClassFlooringDaoProductImpl(testFile);
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        String testFile = "FilesTest\\Data\\Products.txt";
        
        // Use the FileWriter to quickly blank the file
        daoProduct = new ClassFlooringDaoProductImpl(testFile);
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllProduct() throws Exception {
        String testFile = "FilesTest\\Data\\Products.txt";
        
        // Use the FileWriter to quickly blank the file
        //new FileWriter(testFile, true);
        daoProduct = new ClassFlooringDaoProductImpl(testFile);
        
        Product brick = new Product("Brick", new BigDecimal("15.00"), new BigDecimal("12.00"));
        Product laminate = new Product("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10"));
        Product tile = new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"));
        Product wood = new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"));
        // ACT & ASSERT
        List<Product> listProduct = daoProduct.getAllProduct();
        assertNotNull(listProduct, "The list must be not null");
        assertEquals(4, listProduct.size(), 
                                    "Should only have one product.");
        assertTrue(listProduct.contains(brick),
                                  "The only product should be the order of Brick.");
        assertTrue(listProduct.contains(tile),
                                  "The only product should be the order of Brick.");
        assertTrue(listProduct.contains(wood),
                                  "The only product should be the order of Brick.");
        assertTrue(listProduct.contains(laminate),
                                  "The only product should be the order of Brick.");
    }

    @Test
    public void testGetProduct() throws Exception {
        String testFile = "FilesTest\\Data\\Products.txt";
        
        // Use the FileWriter to quickly blank the file
        //new FileWriter(testFile, true);
        daoProduct = new ClassFlooringDaoProductImpl(testFile);
        
        Product productLaminate = new Product("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10"));
        // ACT & ASSERT
         //ACT
        Product retrievedProduct = daoProduct.getProduct("Laminate");
        
        //ASSERT
        assertEquals(retrievedProduct,productLaminate,"The retrieved product should be equal to the carpet product.");
        
        //Product shouldBeBrick = daoProduct.getProduct("Brick");
        //assertNotNull(shouldBeBrick, "Getting brick should be not null.");
        //assertEquals(product, shouldBeBrick,"Product stored should be Brick.");
    }
    
}

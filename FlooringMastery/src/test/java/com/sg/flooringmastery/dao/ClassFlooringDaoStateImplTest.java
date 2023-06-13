package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.State;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClassFlooringDaoStateImplTest {
    ClassFlooringDaoState daoState;
    
    public ClassFlooringDaoStateImplTest() {
        //daoState = new ClassFlooringStateStubImpl();
        String testFile = "FilesTest\\Data\\Taxes.txt";
        
        // Use the FileWriter to quickly blank the file
        daoState = new ClassFlooringDaoStateImpl(testFile);
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        String testFile = "FilesTest\\Data\\Taxes.txt";
        daoState = new ClassFlooringDaoStateImpl(testFile);
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllState() throws Exception {
        String testFile = "FilesTest\\Data\\Taxes.txt";        
        // Use the FileWriter to quickly blank the file
        daoState = new ClassFlooringDaoStateImpl(testFile);
        
        State oneState = new State("CU", "Cuba", new BigDecimal("2.45"));
        // ACT & ASSERT
        List<State> listState = daoState.getAllState();
        assertNotNull(listState, "The list must be not null");
        assertEquals(5, listState.size(), 
                                       "Should only have one state.");
        assertTrue(listState.contains(oneState),
                                  "The only state should be the order of CU.");
    }

    @Test
    public void testGetState() throws Exception {
        String testFile = "FilesTest\\Data\\Taxes.txt";
        
        // Use the FileWriter to quickly blank the file
        daoState = new ClassFlooringDaoStateImpl(testFile);
        
        State oneState = new State("CU", "Cuba", new BigDecimal("2.45"));        
        // ACT & ASSERT
        State shouldBeCuba = daoState.getState("CU");
        assertNotNull(shouldBeCuba, "Getting Cuba should be not null.");
        assertEquals( oneState, shouldBeCuba,"State stored should be Cuba.");
    }

    @Test
    public void testGetTaxRate() throws Exception {
        String testFile = "FilesTest\\Data\\Taxes.txt";
        
        // Use the FileWriter to quickly blank the file
        daoState = new ClassFlooringDaoStateImpl(testFile);
        
        State oneState = new State("CU", "Cuba", new BigDecimal("2.45"));
                
        BigDecimal thisTax = daoState.getTaxRate("CU");        
        assertEquals(oneState.getTaxRate(), thisTax);
        
        assertNotNull(daoState.getTaxRate("CU"), "The tax rate should be null");
        assertThrows(ClassNotFoundException.class , ()-> daoState.getTaxRate("FL"));
    }
    
}

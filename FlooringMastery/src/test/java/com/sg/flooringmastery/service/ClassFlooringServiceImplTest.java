
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.ClassFlooringDaoOrder;
import com.sg.flooringmastery.dao.ClassFlooringDaoProduct;
import com.sg.flooringmastery.dao.ClassFlooringDaoState;
import com.sg.flooringmastery.dao.ClassFlooringDaoWriteEntry;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ClassFlooringServiceImplTest {
    private ClassFlooringServiceImpl service;
    
    public ClassFlooringServiceImplTest() {
        ClassFlooringDaoOrder daoOrder = new ClassFlooringDaoStubImpl();
        ClassFlooringDaoProduct daoProduct = new ClassFlooringProductStubImpl();
        ClassFlooringDaoState daoState = new ClassFlooringStateStubImpl();
        ClassFlooringDaoWriteEntry writer = new ClassFlooringAuditDaoStubImpl();
        
        service = new ClassFlooringServiceImpl(daoOrder, daoProduct, daoState, writer);
    }

    @Test
    public void testSomeMethod() {
    }
    
}

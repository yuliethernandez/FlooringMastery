
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.State;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ClassFlooringDaoState {
    
    List<State> getAllState() throws ClassPersistenceException, FileNotFoundException, IOException;

    State getState(String stateAbrev) throws ClassPersistenceException, FileNotFoundException, IOException;

    public BigDecimal getTaxRate(String abrevUser) throws IOException, ClassNotFoundException, ClassPersistenceException;
    
}

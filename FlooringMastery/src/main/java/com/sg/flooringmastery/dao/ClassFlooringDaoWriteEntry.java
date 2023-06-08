package com.sg.flooringmastery.dao;

import java.io.IOException;

public interface ClassFlooringDaoWriteEntry {
    void writeEntry(String entry) throws ClassPersistenceException, IOException;
    
    //void writeOrders(String entry) throws ClassPersistenceException, IOException;
}

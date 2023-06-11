
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.ClassFlooringDaoState;
import com.sg.flooringmastery.dao.ClassPersistenceException;
import com.sg.flooringmastery.dto.State;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClassFlooringStateStubImpl implements ClassFlooringDaoState{
    public State state;
    
    public ClassFlooringStateStubImpl(){
        this.state = new State("CU", "Cuba", new BigDecimal("5"));
    }

    @Override
    public List<State> getAllState() throws ClassPersistenceException, FileNotFoundException, IOException {
        List<State> statetList = new ArrayList<>();
        statetList.add(state);
        return statetList;
    }

    @Override
    public State getState(String stateAbrev) throws ClassPersistenceException, FileNotFoundException, IOException {
        if (state.getStateAbbreviation().equals(stateAbrev)) {
            return state;
        } else {
            return null;
        }  
    }

    @Override
    public BigDecimal getTaxRate(String abrevUser) throws IOException, ClassNotFoundException, ClassPersistenceException {
        if (state.getStateAbbreviation().equals(abrevUser)) {
            return state.getTaxRate();
        } else {
            return null;
        } 
    }
    
}

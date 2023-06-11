package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.State;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ClassFlooringDaoStateImpl implements ClassFlooringDaoState {
    
    public static String DATA_STATE;
    public static final String DELIMITER = ",";    
    private final Map<String, State> listState = new TreeMap<>();
    
    public ClassFlooringDaoStateImpl(){
        this.DATA_STATE = "Files\\Data\\Taxes.txt";
    }
    
    public ClassFlooringDaoStateImpl(String fileStateTaxes){
        this.DATA_STATE = fileStateTaxes;
    }
    
    @Override
    public List<State> getAllState() throws ClassPersistenceException, FileNotFoundException, IOException {
        loadFileState();
        //-listProducts.values() retrieves a Collection of all the Order objects stored in the listOrder map.
        return listState.values().stream()
                //-collects the Orders objects into an ArrayList using the Collectors.toCollection method.
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public State getState(String stateAbrev) throws ClassPersistenceException, FileNotFoundException,IOException{
        loadFileState();
        return listState.get(stateAbrev);
    }
    
    private State unmarshallState(String stateAsText){
        String[] stateTokens = stateAsText.split(DELIMITER);

        String state= stateTokens[0];
        String stateName = stateTokens[1];
        BigDecimal taxRate = new BigDecimal(stateTokens[2]);
        taxRate = taxRate.setScale(2, RoundingMode.HALF_UP);
        State stateObj = new State(state, stateName, taxRate);

        return stateObj;
    }
    
    private void loadFileState() throws ClassPersistenceException, FileNotFoundException, IOException {
   
        Scanner scanner;

        // Create Scanner for reading the file
        scanner = new Scanner(new BufferedReader(new FileReader(DATA_STATE)));
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentOrder holds the most recent order unmarshalled
        State currentState;
        currentLine = scanner.nextLine();
        
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a Order
            currentState = unmarshallState(currentLine);

            listState.put(currentState.getStateAbbreviation(), currentState);
        }
        // close scanner
        scanner.close();
    }

    @Override
    public BigDecimal getTaxRate(String abrevUser) throws IOException, ClassNotFoundException, ClassPersistenceException{
        loadFileState();
        State state = listState.values().stream()
                .filter(s -> s.getStateAbbreviation().equals(abrevUser))
                .findAny()
                .orElse(null);
        /*if(state == null){
            throw new ClassNotFoundException("There is not state with that abreviation");
        }*/
        return state.getTaxRate();
    }
}

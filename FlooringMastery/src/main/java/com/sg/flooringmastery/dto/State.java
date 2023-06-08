package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class State {
    private String stateAbbreviation;
    private String stateName;
    private BigDecimal taxRate;

    public State(String stateAbbreviation, BigDecimal taxRate) {
        this.stateAbbreviation = stateAbbreviation;
        this.taxRate = taxRate;
    }
    
    public State(String stateAbbreviation, String stateName, BigDecimal taxRate) {
        this.stateAbbreviation = stateAbbreviation;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public String getStateName() {
        return stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.stateAbbreviation);
        hash = 97 * hash + Objects.hashCode(this.stateName);
        hash = 97 * hash + Objects.hashCode(this.taxRate);
        return hash;
    }

    @Override
    public String toString() {
        return "State{" + "stateAbbreviation=" + stateAbbreviation + ", stateName=" + stateName + ", taxRate=" + taxRate + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (!Objects.equals(this.stateAbbreviation, other.stateAbbreviation)) {
            return false;
        }
        if (!Objects.equals(this.stateName, other.stateName)) {
            return false;
        }
        return Objects.equals(this.taxRate, other.taxRate);
    }
   
}

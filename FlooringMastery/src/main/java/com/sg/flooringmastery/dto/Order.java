package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

public class Order {
    private int orderNumber;    
    private String customerName;
    private String stateAbrev;
    private Product product;
    private BigDecimal area;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal total;
    private BigDecimal taxRate;
    private BigDecimal tax;
    
    //CustomerName,State,TaxRate,ProductType,Area,    CostPerSquareFoot, LaborCostPerSquareFoot, MaterialCost,LaborCost,Tax, Total, date
    public Order(int orderNumber, String customerName,
            String stateAbrev, BigDecimal taxRate, 
            Product product, BigDecimal area) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.stateAbrev = stateAbrev;
        this.taxRate = taxRate;
        this.product = product;
        this.area = area;
        this.materialCost = this.calculateMaterialCost();
        this.laborCost = this.calculateLaborCost();
        this.total = this.calculateTotal();
        this.tax = this.calculateTax();
    }
    public Order(int orderNumber, String customerName, 
            String stateAbrev, BigDecimal taxRate, Product product, 
            BigDecimal area, BigDecimal costPerSquareFoot, 
            BigDecimal laborCostPerSquareFoot, BigDecimal materialCost, 
            BigDecimal  laborCost, BigDecimal tax, BigDecimal total) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.stateAbrev = stateAbrev;
        this.taxRate = taxRate;
        this.product = product;
        this.area = area;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
        this.total = total;
        this.tax = tax;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getStateAbrev() {
        return stateAbrev;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getArea() {
        return area;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    //MaterialCost = (Area * CostPerSquareFoot)
    public BigDecimal calculateMaterialCost() {
        BigDecimal matCost = new BigDecimal(BigInteger.ONE);
        BigDecimal costPerSquareFoot = this.getProduct().getCostPerSquareFoot();                
        return matCost.multiply(this.getArea()).multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
    }

    //LaborCost = (Area * LaborCostPerSquareFoot)
    public BigDecimal calculateLaborCost() {
        BigDecimal labCost = new BigDecimal(BigInteger.ONE);
        BigDecimal laborCostPerSquareFoot = this.getProduct().getLaborCostPerSquareFoot();   
        return labCost.multiply(this.getArea()).multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
    }
    
    //Tax = (MaterialCost + LaborCost)*(TaxRate/100), Tax rates are stored as whole numbers
    public BigDecimal calculateTax() {
        BigDecimal tax = BigDecimal.ONE;
        //creating the variables
        BigDecimal taxRateDivBy100 = this.getTaxRate().divide(new BigDecimal("100")); //rev
        BigDecimal labCost = this.getLaborCost();
        BigDecimal matCost = this.getMaterialCost();
        //Tax = (MaterialCost + LaborCost)
        tax = matCost.add(labCost);
        //tax *=(TaxRate/100)
        return tax.multiply(taxRateDivBy100).setScale(2, RoundingMode.HALF_UP);
    }
    
    //Total = (MaterialCost + LaborCost + Tax)
    public BigDecimal calculateTotal() {
        BigDecimal total = new BigDecimal(BigInteger.ONE);
        //Calculating the total
        BigDecimal labCost = this.getLaborCost();
        BigDecimal matCost = this.getMaterialCost();
        return total.add(matCost).add(labCost).add(matCost).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.orderNumber;
        hash = 73 * hash + Objects.hashCode(this.customerName);
        hash = 73 * hash + Objects.hashCode(this.stateAbrev);
        hash = 73 * hash + Objects.hashCode(this.product);
        hash = 73 * hash + Objects.hashCode(this.area);
        hash = 73 * hash + Objects.hashCode(this.materialCost);
        hash = 73 * hash + Objects.hashCode(this.laborCost);
        hash = 73 * hash + Objects.hashCode(this.total);
        hash = 73 * hash + Objects.hashCode(this.tax);
        return hash;
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
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.stateAbrev, other.stateAbrev)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return Objects.equals(this.tax, other.tax);
    }

    

    @Override
    public String toString() {
        return "Order{" + "orderNumber=" + orderNumber + ", customerName=" + customerName + ", state=" + stateAbrev + ", product=" + product + ", area=" + area + ", materialCost=" + materialCost + ", laborCost=" + laborCost + ", total=" + total + '}';
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setStateAbrev(String stateAbrev) {
        this.stateAbrev = stateAbrev;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }
    
    
}

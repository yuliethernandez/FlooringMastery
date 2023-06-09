package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ClassFlooringDaoProductImpl implements ClassFlooringDaoProduct {

    public static String DATA_PRODUCTS;
    public static final String DELIMITER = ",";    
    private final Map<String, Product> listProduct = new TreeMap<>();
    
    public ClassFlooringDaoProductImpl(){
        this.DATA_PRODUCTS = "Files\\Data\\Products.txt";
    }
    
    public ClassFlooringDaoProductImpl(String fileProducts){
        this.DATA_PRODUCTS = fileProducts;
    }   

    @Override
    public List<Product> getAllProduct() throws ClassPersistenceException, FileNotFoundException {
        loadFileProduct();
        //-listProducts.values() retrieves a Collection of all the Order objects stored in the listOrder map.
        return listProduct.values().stream()
                //-collects the Orders objects into an ArrayList using the Collectors.toCollection method.
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    @Override
    public Product getProduct(String productType) throws ClassPersistenceException, FileNotFoundException{
        loadFileProduct();
        return listProduct.get(productType);
    }  
    
    private Product unmarshallProduct(String productAsText){
        String[] productTokens = productAsText.split(DELIMITER);

        String productType= productTokens[0];
        BigDecimal costPerSquareFoot = new BigDecimal(productTokens[1]);
        costPerSquareFoot = costPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal laborCostPerSquareFoot = new BigDecimal(productTokens[2]);
        laborCostPerSquareFoot = laborCostPerSquareFoot.setScale(2, RoundingMode.HALF_UP);

        Product product = new Product(productType, costPerSquareFoot, laborCostPerSquareFoot);

        return product;
    }

    private void loadFileProduct() throws ClassPersistenceException, FileNotFoundException {
        Scanner scanner;

        // Create Scanner for reading the file
        scanner = new Scanner(new BufferedReader(new FileReader(DATA_PRODUCTS)));
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentOrder holds the most recent order unmarshalled
        Product currentProduct;
        currentLine = scanner.nextLine();
        
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a Order
            currentProduct = unmarshallProduct(currentLine);

            // We are going to use the student id as the map key for our student object.
            // Put currentOrder into the map using student id as the key
            listProduct.put(currentProduct.getProductType(), currentProduct);
        }
        // close scanner
        scanner.close();
    }
    
    
}

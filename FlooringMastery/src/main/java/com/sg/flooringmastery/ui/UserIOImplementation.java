
package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserIOImplementation implements UserIO{
    
    private final Scanner sc ;
    
    public UserIOImplementation(){
        this.sc = new Scanner(System.in);
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        int number = 0;
        while(true){
            try{
                number = Integer.parseInt(readString(prompt));
                break;
            }
            catch(NumberFormatException ex){
                print("***ERROR*** \nInvalid entry. You must Enter a number.");
            }
        }
        
        return number;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int num = 0;
        do{
            num = readInt(prompt + "[" + min + " - " + max + "]: ");
        }while(num<min || num>max);
        return num;
    }

    @Override
    public double readDouble(String prompt) {
        double number = 0.0;        
        try{
            number = Double.parseDouble(readString(prompt));
        }
        catch(NumberFormatException ex){
            print("***ERROR*** \nInvalid entry. You must Enter a number.");
        }
        return number;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double num = 0.0;        
        do{
            num = readDouble(prompt + "[" + min + " - " + max + "]: ");
        }while(num < min || num > max);        
        return num;
    }

    @Override
    public float readFloat(String prompt) {
        float number = 0.0f;
        try{
            number = Float.parseFloat(readString(prompt));
        }
        catch(NumberFormatException ex){
            print("***ERROR*** \nInvalid entry. You must Enter a number.");
        }
        return number;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float num = 0.0f;
        do{
            num = readFloat(prompt + "[" + min + " - " + max + "]: ");
        }while(num < min || num > max);
        return num;
    }

    @Override
    public long readLong(String prompt) {
        long number = 0l;
        try{
            number = Long.parseLong(readString(prompt));
        }
        catch(NumberFormatException ex){
            print("***ERROR*** \nInvalid entry. You must Enter a number.");
        }
        return number;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long num = 0l;        
        do{
            num = readLong(prompt + "[" + min + " - " + max + "]: ");
        }while(num < min || num > max);        
        return num;
    }

    @Override
    public BigDecimal readArea(String prompt) {
        BigDecimal area = BigDecimal.ZERO;
        while(true){            
            try{                
                do{
                    area = new BigDecimal(readString(prompt));
                    area= area.setScale(2, RoundingMode.HALF_UP);
                    
                    if(area.compareTo(new BigDecimal("100")) == -1){
                        print("The area must be a positive decimal. Minimum order size is 100 sq ft.");
                    }else
                        break;
                    
                }while(true);   
                
                break;
            }
            catch(NumberFormatException ex){
                print("***ERROR*** \nInvalid entry. You must enter a number.");
            }
        }        
        return area;
    }

    @Override
    public LocalDate readDate(String prompt)  throws DateTimeParseException{
        LocalDate date;
        String input = "";
        while(true){
            try{
                DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                input = readString(prompt);
                date = LocalDate.parse(input, format);
                break;  
            }catch(DateTimeParseException e){
                print("ERROR " + input + " is not valid Date");
            } 
        }
        return date;
    }

}

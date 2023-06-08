package com.sg.flooringmastery.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class ClassFlooringDaoWriteEntryImpl implements ClassFlooringDaoWriteEntry{

    private String EXPORT_FILE = "Audit.txt";
    PrintWriter out;
    
    /*public ClassFlooringDaoWriteEntryImpl(String EXPORT_FILE){
        this.EXPORT_FILE = EXPORT_FILE;
    }*/
    @Override
    public void writeEntry(String entry) throws ClassPersistenceException, IOException {       
        out = new PrintWriter(new FileWriter(EXPORT_FILE, true));
 
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
    }
/*
    @Override
    public void writeOrders(String entry) throws ClassPersistenceException, IOException {
        EXPORT_FILE += getNameFileOrderToday();
        out = new PrintWriter(new FileWriter(EXPORT_FILE, true));
 
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
    }
    
    private String getNameFileOrderToday(){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate ld = LocalDate.parse("02/07/2010", formatter);//the ld is: 2010-02-07
        String formatted = ld.format(formatter); //the formatted is: 02/07/2010        
        
        Date legacyDate = new Date();
        ZonedDateTime zdt = ZonedDateTime.ofInstant(legacyDate.toInstant(), ZoneId.systemDefault());//the zdt is: 2023-06-06T16:33:03.335+01:00[GMT+01:00]
        ld = zdt.toLocalDate();//the ld is: 2023-06-06        
        
        String file = "\\Orders_" + ld.toString().replaceAll("-", "");//file is Orders_2023-06-06  
        
        return file;
    }*/
    
}

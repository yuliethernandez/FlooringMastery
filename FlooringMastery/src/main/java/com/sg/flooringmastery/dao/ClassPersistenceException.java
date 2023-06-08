
package com.sg.flooringmastery.dao;

public class ClassPersistenceException extends Exception{
    public ClassPersistenceException(String message) {
        super(message);
    }
    
    public ClassPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}

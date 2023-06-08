
package com.sg.flooringmastery.service;

public class ClassDuplicateOrderException extends Exception{
    public ClassDuplicateOrderException(String message) {
        super(message);
    }
    
    public ClassDuplicateOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}

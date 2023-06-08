
package com.sg.flooringmastery.service;

public class ClassNotFoundOrderException extends Exception{
    public ClassNotFoundOrderException(String message) {
        super(message);
    }
    
    public ClassNotFoundOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}

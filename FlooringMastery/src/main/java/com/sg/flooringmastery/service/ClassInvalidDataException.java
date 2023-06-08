
package com.sg.flooringmastery.service;

public class ClassInvalidDataException extends Exception{
    public ClassInvalidDataException(String message) {
        super(message);
    }
    
    public ClassInvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

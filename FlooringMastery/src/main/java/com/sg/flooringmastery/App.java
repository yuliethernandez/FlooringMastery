package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.FlooringController;
import com.sg.flooringmastery.dao.ClassPersistenceException;
import com.sg.flooringmastery.service.ClassDuplicateOrderException;
import com.sg.flooringmastery.service.ClassInvalidDataException;
import com.sg.flooringmastery.service.ClassNotFoundOrderException;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) throws ClassPersistenceException, FileNotFoundException, ClassNotFoundOrderException, IOException, ClassDuplicateOrderException, ClassInvalidDataException, ClassNotFoundException {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        FlooringController controller = appContext.getBean("controller", FlooringController.class);
        controller.run();
    }
}

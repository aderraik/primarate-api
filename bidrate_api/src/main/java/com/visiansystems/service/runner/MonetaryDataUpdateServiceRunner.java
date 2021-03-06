package com.visiansystems.service.runner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.visiansystems.service.MonetaryDataUpdateService;

public class MonetaryDataUpdateServiceRunner {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("main-context.xml");
        MonetaryDataUpdateService updateService = ctx.getBean(MonetaryDataUpdateService.class);

        try {
            //TODO: Verify if Hibernate is in the right mode
            updateService.run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

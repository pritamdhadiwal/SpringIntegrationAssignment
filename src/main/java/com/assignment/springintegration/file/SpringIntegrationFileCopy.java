package com.assignment.springintegration.file;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIntegrationFileCopy {

    public static void main(String[] args) throws InterruptedException, IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "fileCopyApplicationContext.xml");
    }
    
}

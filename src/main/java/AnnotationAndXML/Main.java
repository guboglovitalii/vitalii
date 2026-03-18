package AnnotationAndXML;


import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            
        Car car = context.getBean("car", Car.class);
        car.drive();

        context.close();
        
    }
}

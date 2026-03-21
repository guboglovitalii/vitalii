package lesson_N4.JavaCode;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
            
        Car car = context.getBean("car", Car.class);
        System.out.println(car);

        context.close();
        
    }
}

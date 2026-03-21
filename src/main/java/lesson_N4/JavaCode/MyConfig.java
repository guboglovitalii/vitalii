package lesson_N4.JavaCode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public Wheel wheel() {
        return new Wheel();
    }
    @Bean
    public Accumulator accumulator() {
        return new Accumulator();
    }


    @Bean
    public Starter starter() {
        return new Starter();
    }
    @Bean
    public SparkPlug sparkPlug() {
        return new SparkPlug();
    }
    @Bean
    public Engine engine() {
        return new Engine(starter(), sparkPlug());
    }




    @Bean
    public Differential differential() {
        return new Differential();
    }
    @Bean
    public Hinge hinge() {
        return new Hinge();
    }
    @Bean
    public Suspension suspension() {
        return new Suspension(hinge(), differential());
    }




    @Bean
    public Car car(Engine engine,
                   Wheel wheel,
                   Accumulator accumulator,
                   Suspension suspension) {

        return new Car(engine, accumulator, wheel, suspension);
    }
}
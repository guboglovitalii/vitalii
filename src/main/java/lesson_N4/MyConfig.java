package lesson_N4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public Wheel wheel() {
        return new Wheel();
    }

    @Bean
    public Car car(Engine engine,
                   Wheel wheel,
                   Accumulator accumulator,
                   Suspension suspension) {

        return new Car(engine, accumulator, wheel, suspension);
    }
}
package lesson_N4.XMLAndAnotation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {

    @Autowired
    private Engine engine;

    @Autowired
    private Suspension suspension;

    @Autowired
    private Wheel wheel;

    @Autowired
    private Accumulator accumulator;

    public String toString() {
        return "Car:\n" +
                engine + "\n" +
                wheel + "\n" +
                accumulator + "\n" +
                suspension;
    }
}
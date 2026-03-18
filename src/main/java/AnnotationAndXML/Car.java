package AnnotationAndXML;

import org.springframework.stereotype.Component;

@Component
public class Car {
    private EngineStart engine;
    // private Accumulator accumulator;
    private Wheel wheel;
    // private Suspension suspension;


    public Car(EngineStart engine) {
        this.engine = engine;
        // this.accumulator = accumulator;
        // this.wheel = wheel;
        // this.suspension = suspension;

        System.out.println("Car crated");
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }



    public void drive() {
        engine.start();
        System.out.println("Car is driving");
    }
    public void getWheel() {
        wheel.start();
        
    }
    
    
}

//  Accumulator accumulator,, Suspension suspension
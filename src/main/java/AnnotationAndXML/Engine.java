package AnnotationAndXML;

import org.springframework.stereotype.Component;

@Component
public class Engine implements EngineStart {
    public Engine() {
        System.out.println("Engine created");
    }

    @Override
    public void start() {
        System.out.println("Engine started");
    }
    
}

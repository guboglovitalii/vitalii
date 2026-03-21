package lesson_N4.XMLAndAnotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class Engine {

    @Autowired
    private SparkPlug sparkPlug;

    @Autowired
    private Starter starter;

    @Override
    public String toString() {
        return "Engine with " + sparkPlug + " and " + starter;
    }
}





package lesson_N4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class Suspension {
  
    @Autowired
    private Hinge hinge;

    @Autowired
    private Differential differential;

    @Override
    public String toString() {
        return "Suspension with " + hinge + " and " + differential;
    }
}


package lesson_N4.JavaCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Suspension {
    private Hinge hinge;
    private Differential differential;

    public Suspension( Hinge hinge, Differential differential) {
        this.hinge = hinge;
        this.differential = differential;
    }

//    public void start() {
//        System.out.println("Engine started");
//    }

    @Override
    public String toString() {
        return "Engine{" +
                "starter=" + hinge +
                ", sparkPlug=" + differential +
                '}';
    }
}


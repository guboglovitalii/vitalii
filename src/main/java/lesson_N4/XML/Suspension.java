package lesson_N4.XML;

public class Suspension {
    private Hinge hinge;
    private Differential differential;

    public Suspension(Hinge hinge, Differential differential) {
        this.hinge = hinge;
        this.differential = differential;
    }

//    public void start() {
//        System.out.println("Engine started");
//    }

    @Override
    public String toString() {
        return "Suspension{" +
                "hinge=" + hinge +
                ", differential=" + differential +
                '}';
    }
}
package lesson_N4;

public class Engine {
    private Starter starter;
    private SparkPlug sparkPlug;

    public Engine(Starter starter, SparkPlug sparkPlug) {
        this.starter = starter;
        this.sparkPlug = sparkPlug;
    }

    public void start() {
        System.out.println("Engine started");
    }

    @Override
    public String toString() {
        return "Engine{" +
                "starter=" + starter +
                ", sparkPlug=" + sparkPlug +
                '}';
    }
}




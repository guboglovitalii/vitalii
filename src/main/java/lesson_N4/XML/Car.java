package lesson_N4.XML;


public class Car {
    private Engine engine;
    private Accumulator accumulator;
    private Wheel wheel;
    private Suspension suspension;

   public Car(Engine engine, Accumulator accumulator, Wheel wheel, Suspension suspension) {
    this.engine = engine;
    this.accumulator = accumulator;
    this.wheel = wheel;
    this.suspension = suspension;
   }

   public String toString() {
    return "Car:\n" +
                engine + "\n" +
                wheel + "\n" +
                accumulator + "\n" +
                suspension;
   }
}
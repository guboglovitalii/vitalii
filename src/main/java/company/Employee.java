package company;

public class Employee {
    private String firstName;
    private String lastName;
    private int age;
    private Position post;
    private String skills;

    public Employee(String firstName, String lastName, int age, Position post, String skills) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.post = post;
        this.skills = skills;
    }

    public void comeToWork() {
        System.out.println(firstName + " " + lastName + " пришёл на работу.");
    }

    public void doWork() {
        switch (post) {
            case CLEANER -> System.out.println(firstName + " " + lastName + " убирает помещения.");
            case DRIVER -> System.out.println(firstName + " " + lastName + " перевозит сотрудников.");
            case MANAGER -> System.out.println(firstName + " " + lastName + " управляет проектами.");
            case SECURITY -> System.out.println(firstName + " " + lastName + " охраняет территорию.");
        }
    }

    public void lunchBreak() {
        int lunchTime = switch (post) {
            case CLEANER -> 30;
            case DRIVER -> 40;
            case SECURITY -> 20;
            case MANAGER -> 60;
        };
        System.out.println(firstName + " " + lastName + " обедает " + lunchTime + " минут.");
    }

    public void goToHome() {
        System.out.println(firstName + " " + lastName + " уходит домой.");
    }

    public void workDay() {
        comeToWork();
        doWork();
        lunchBreak();
        goToHome();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", возраст: " + age +
               ", должность: " + post +
               ", навыки: " + skills;
    }
}

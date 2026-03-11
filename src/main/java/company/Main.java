package company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Company company = new Company();

        company.addEmployee(new Employee("Иван", "Иванов", 30, Position.CLEANER, "Быстрая уборка"));
        company.addEmployee(new Employee("Петр", "Петров", 45, Position.DRIVER, "Категория B"));
        company.addEmployee(new Employee("Мария", "Сидорова", 28, Position.MANAGER, "Управление проектами"));
        company.addEmployee(new Employee("Сергей", "Кузнецов", 35, Position.SECURITY, "Боевые искусства"));

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nСписок сотрудников:");
                company.showEmployeesWithIndex();
                System.out.println("0. Выход");

                System.out.print("Выберите сотрудника по номеру: ");
                int choice = scanner.nextInt();

                if (choice == 0) {
                    System.out.println("Программа завершена.");
                    break;
                }

                Employee selected = company.getEmployeeByIndex(choice - 1);
                if (selected != null) {
                    System.out.println("\nДень сотрудника:");
                    selected.workDay();
                } else {
                    System.out.println("Некорректный выбор!");
                }
            }
        }
    }
}

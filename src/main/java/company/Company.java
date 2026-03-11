package company;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void showEmployeesWithIndex() {
        for (int i = 0; i < employees.size(); i++) {
            System.out.println((i+1) + ". " + employees.get(i));
        }
    }

    public Employee getEmployeeByIndex(int index) {
        if (index >= 0 && index < employees.size()) {
            return employees.get(index);
        }
        return null;
    }
}

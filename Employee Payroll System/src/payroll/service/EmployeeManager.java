package payroll.service;

import payroll.model.Employee;
import java.util.ArrayList;

public class EmployeeManager {

    private ArrayList<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Employee searchEmployee(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public boolean employeeExists(int id) {
        return searchEmployee(id) != null;
    }

    public ArrayList<Employee> getAllEmployees() {
        return employees;
    }
}

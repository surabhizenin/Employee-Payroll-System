package payroll.model;

public class FullTimeEmployee extends Employee {

    public FullTimeEmployee(int id, String name, double baseSalary,
                            int leavesTaken, double bonus, double deduction) {
        super(id, name, baseSalary, leavesTaken, bonus, deduction);
    }

    @Override
    public double calculateSalary() {
        double salary = baseSalary + bonus - deduction;
        if (leavesTaken > 2) {
            salary -= 1000;
        }
        return salary;
    }
}

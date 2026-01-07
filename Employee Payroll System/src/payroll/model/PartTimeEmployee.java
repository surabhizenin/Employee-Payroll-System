package payroll.model;

public class PartTimeEmployee extends Employee {

    public PartTimeEmployee(int id, String name, double baseSalary,
                            int leavesTaken, double bonus, double deduction) {
        super(id, name, baseSalary, leavesTaken, bonus, deduction);
    }

    @Override
    public double calculateSalary() {
        double salary = (baseSalary * 0.8) + bonus - deduction;
        if (leavesTaken > 1) {
            salary -= 500;
        }
        return salary;
    }
}

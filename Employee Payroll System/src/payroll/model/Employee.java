package payroll.model;

public abstract class Employee {

    protected int id;
    protected String name;
    protected double baseSalary;
    protected int leavesTaken;
    protected double bonus;
    protected double deduction;

    public Employee(int id, String name, double baseSalary,
                    int leavesTaken, double bonus, double deduction) {
        this.id = id;
        this.name = name;
        this.baseSalary = baseSalary;
        this.leavesTaken = leavesTaken;
        this.bonus = bonus;
        this.deduction = deduction;
    }

    public abstract double calculateSalary();

    public final String generateSalarySlip() {
        return "Employee ID: " + id +
                "\nName: " + name +
                "\nBase Salary: " + baseSalary +
                "\nBonus: " + bonus +
                "\nDeduction: " + deduction +
                "\nLeaves Taken: " + leavesTaken +
                "\n----------------------" +
                "\nFinal Salary: " + calculateSalary();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

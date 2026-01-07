package payroll.ui;

import payroll.model.*;
import payroll.service.EmployeeManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PayrollFrame extends JFrame {

    private EmployeeManager manager = new EmployeeManager();

    private JTextField idField = new JTextField();
    private JTextField nameField = new JTextField();
    private JTextField salaryField = new JTextField();
    private JTextField leavesField = new JTextField();
    private JTextField bonusField = new JTextField();
    private JTextField deductionField = new JTextField();

    private JComboBox<String> typeBox =
            new JComboBox<>(new String[]{"Full-Time", "Part-Time"});

    private JTextArea outputArea = new JTextArea();

    // JTable components
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public PayrollFrame() {

        setTitle("Employee Payroll System");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // FORM PANEL
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        formPanel.add(new JLabel("Employee ID"));
        formPanel.add(idField);

        formPanel.add(new JLabel("Name"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Base Salary"));
        formPanel.add(salaryField);

        formPanel.add(new JLabel("Leaves Taken"));
        formPanel.add(leavesField);

        formPanel.add(new JLabel("Bonus"));
        formPanel.add(bonusField);

        formPanel.add(new JLabel("Deduction"));
        formPanel.add(deductionField);

        formPanel.add(new JLabel("Employee Type"));
        formPanel.add(typeBox);

        JButton addBtn = new JButton("Add Employee");
        JButton searchBtn = new JButton("Search Employee");
        JButton slipBtn = new JButton("Generate Salary Slip");

        formPanel.add(addBtn);
        formPanel.add(searchBtn);
        formPanel.add(slipBtn);

        // TABLE SETUP
        String[] columns = {"ID", "Name", "Type", "Base Salary", "Final Salary"};
        tableModel = new DefaultTableModel(columns, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(employeeTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Employee List"));

        // OUTPUT AREA
        outputArea.setRows(5);
        outputArea.setColumns(40);
        outputArea.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createTitledBorder("Output"));

        // MAIN LAYOUT
        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(outputScroll, BorderLayout.SOUTH);

        // BUTTON ACTIONS
        addBtn.addActionListener(e -> addEmployee());
        searchBtn.addActionListener(e -> searchEmployee());
        slipBtn.addActionListener(e -> generateSlip());
    }

    // ================= LOGIC METHODS =================

    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            double salary = Double.parseDouble(salaryField.getText());
            int leaves = Integer.parseInt(leavesField.getText());
            double bonus = Double.parseDouble(bonusField.getText());
            double deduction = Double.parseDouble(deductionField.getText());

            if (manager.employeeExists(id)) {
                outputArea.setText("Employee ID already exists");
                return;
            }

            Employee emp;
            String type;

            if (typeBox.getSelectedItem().equals("Full-Time")) {
                emp = new FullTimeEmployee(id, name, salary, leaves, bonus, deduction);
                type = "Full-Time";
            } else {
                emp = new PartTimeEmployee(id, name, salary, leaves, bonus, deduction);
                type = "Part-Time";
            }

            manager.addEmployee(emp);

            // Add row to JTable
            tableModel.addRow(new Object[]{
                    emp.getId(),
                    emp.getName(),
                    type,
                    salary,
                    emp.calculateSalary()
            });

            outputArea.setText("Employee added successfully");
            clearFields();

        } catch (NumberFormatException ex) {
            outputArea.setText("Please enter valid value(s)");
        }
    }

    private void searchEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            Employee emp = manager.searchEmployee(id);

            if (emp != null) {
                outputArea.setText("Employee Found:\nID: " +
                        emp.getId() + "\nName: " + emp.getName());
            } else {
                outputArea.setText("Employee not found");
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Enter valid Employee ID");
        }
    }

    private void generateSlip() {
        try {
            int id = Integer.parseInt(idField.getText());
            Employee emp = manager.searchEmployee(id);

            if (emp != null) {
                outputArea.setText(emp.generateSalarySlip());
            } else {
                outputArea.setText("Employee not found");
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Enter valid Employee ID");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        salaryField.setText("");
        leavesField.setText("");
        bonusField.setText("");
        deductionField.setText("");
        typeBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PayrollFrame().setVisible(true));
    }
}

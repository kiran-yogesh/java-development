import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;

public class AttendanceTrackingSystems extends JFrame implements ActionListener {
    private List<Employee> employees;
    private TextField tfId, tfName;
    private TextArea taReport;

    // Authentication fields
    private JFrame loginFrame;
    private TextField tfUsername, tfPassword;

    class Employee {
        private String id;
        private String name;
        private Date clockInTime;
        private Date clockOutTime;
        private boolean onLeave;

        public Employee(String id, String name) {
            this.id = id;
            this.name = name;
            this.onLeave = false;
        }

        // Getters and Setters
        public String getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public Date getClockInTime() {
            return clockInTime;
        }
        public void setClockInTime(Date clockInTime) {
            this.clockInTime = clockInTime;
        }
        public Date getClockOutTime() {
            return clockOutTime;
        }
        public void setClockOutTime(Date clockOutTime) {
            this.clockOutTime = clockOutTime;
        }
        public boolean isOnLeave() {
            return onLeave;
        }
        public void setOnLeave(boolean onLeave) {
            this.onLeave = onLeave;
        }
    }

    public AttendanceTrackingSystems() {
        // Initialize employees list
        employees = new ArrayList<>();

        // Show login frame first
        showLoginFrame();
    }

    private void showLoginFrame() {
        loginFrame = new JFrame("Login");

        loginFrame.setLayout(new FlowLayout());
        loginFrame.setSize(300, 150);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Label lblUsername = new Label("Username:");
        loginFrame.add(lblUsername);

        tfUsername = new TextField(20);
        loginFrame.add(tfUsername);

        Label lblPassword = new Label("Password:");
        loginFrame.add(lblPassword);

        tfPassword = new TextField(20);
        tfPassword.setEchoChar('*');
        loginFrame.add(tfPassword);

        Button btnLogin = new Button("Login");
        btnLogin.addActionListener(this);
        loginFrame.add(btnLogin);

        loginFrame.setLocationRelativeTo(null); // Center the login frame on the screen
        loginFrame.setVisible(true); // Set visible at the end
    }

    private void showAttendanceSystem() {
        setLayout(new FlowLayout());
        setBackground(Color.cyan);

        Label lblId = new Label("Employee ID:");
        lblId.setBackground(Color.ORANGE);
        lblId.setForeground(Color.BLACK);
        add(lblId);

        tfId = new TextField(10);
        add(tfId);
        tfId.setEditable(true);
        tfId.setBackground(Color.lightGray);

        Label lblName = new Label("Employee Name:");
        lblName.setBackground(Color.ORANGE);
        lblName.setForeground(Color.BLACK);
        add(lblName);

        tfName = new TextField(24);
        add(tfName);
        tfName.setBackground(Color.LIGHT_GRAY);

        Button btnAddEmployee = new Button("Add Employee");
        btnAddEmployee.addActionListener(this);
        btnAddEmployee.setBackground(Color.black);
        btnAddEmployee.setForeground(Color.white);
        add(btnAddEmployee);

        Button btnClockIn = new Button("Clock In");
        btnClockIn.addActionListener(this);
        btnClockIn.setBackground(Color.black);
        btnClockIn.setForeground(Color.white);
        add(btnClockIn);

        Button btnClockOut = new Button("Clock Out");
        btnClockOut.addActionListener(this);
        btnClockOut.setBackground(Color.black);
        btnClockOut.setForeground(Color.white);
        add(btnClockOut);

        Button btnLeave = new Button("Request Leave");
        btnLeave.addActionListener(this);
        btnLeave.setBackground(Color.black);
        btnLeave.setForeground(Color.white);
        add(btnLeave);

        Button btnReport = new Button("Generate Report");
        btnReport.addActionListener(this);
        btnReport.setBackground(Color.black);
        btnReport.setForeground(Color.white);
        add(btnReport);

        Button btnClear = new Button("Clear");
        btnClear.addActionListener(this);
        btnClear.setBackground(Color.black);
        btnClear.setForeground(Color.white);
        add(btnClear);

        taReport = new TextArea(20, 50);
        taReport.setForeground(Color.BLACK);
        add(taReport);

        setTitle("Employee Attendance System");
        setSize(600, 400);
        setVisible(true);
    }

    private boolean isValidCredentials(String username, String password) {
        // Hardcoded credentials for example; in a real application, this should use secure authentication.
        return username.equals("admin") && password.equals("password123");
    }

    private Employee findEmployeeById(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }
        return null;
    }

    private Employee findEmployeeByName(String name) {
        for (Employee employee : employees) {
            if (employee.getName().equalsIgnoreCase(name)) {
                return employee;
            }
        }
        return null;
    }

    private boolean isValidInput(String id, String name) {
        if (id == null || id.trim().isEmpty()) {
            taReport.append("Employee ID cannot be empty.\n");
            return false;
        }
        if (name == null || name.trim().isEmpty()) {
            taReport.append("Employee Name cannot be empty.\n");
            return false;
        }
        // Check for duplicate ID or name
        if (findEmployeeById(id) != null) {
            taReport.append("Employee ID already exists.\n");
            return false;
        }
        if (findEmployeeByName(name) != null) {
            taReport.append("Employee Name already exists.\n");
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();

        // Login action
        if (cmd.equals("Login")) {
            String username = tfUsername.getText().trim();
            String password = tfPassword.getText().trim();

            if (isValidCredentials(username, password)) {
                loginFrame.dispose(); // Close login frame
                showAttendanceSystem(); // Show main system after successful login
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials. Try again.");
            }
            return;
        }

        // Attendance system actions
        String id = tfId.getText().trim();
        String name = tfName.getText().trim();

        if (cmd.equals("Add Employee")) {
            if (!isValidInput(id, name)) return;

            Employee employee = new Employee(id, name);
            employees.add(employee);
            taReport.append("Added Employee: " + name + "\n");
        } else if (cmd.equals("Clock In")) {
            Employee employee = findEmployeeById(id);
            if (employee != null && !employee.isOnLeave()) {
                employee.setClockInTime(new Date());
                taReport.append(employee.getName() + " clocked in at " + employee.getClockInTime() + "\n");
            } else if (employee != null) {
                taReport.append(employee.getName() + " is on leave and cannot clock in.\n");
            } else {
                taReport.append("Employee not found.\n");
            }
        } else if (cmd.equals("Clock Out")) {
            Employee employee = findEmployeeById(id);
            if (employee != null) {
                employee.setClockOutTime(new Date());
                taReport.append(employee.getName() + " clocked out at " + employee.getClockOutTime() + "\n");
            } else {
                taReport.append("Employee not found.\n");
            }
        } else if (cmd.equals("Request Leave")) {
            Employee employee = findEmployeeById(id);
            if (employee != null) {
                employee.setOnLeave(true);
                taReport.append(employee.getName() + " is on leave.\n");
            } else {
                taReport.append("Employee not found.\n");
            }
        } else if (cmd.equals("Generate Report")) {
            taReport.setText("");
            for (Employee employee : employees) {
                taReport.append("Employee ID: " + employee.getId() + "\n");
                taReport.append("Name: " + employee.getName() + "\n");
                taReport.append("Clock-in Time: " + employee.getClockInTime() + "\n");
                taReport.append("Clock-out Time: " + employee.getClockOutTime() + "\n");
                taReport.append("On Leave: " + (employee.isOnLeave() ? "Yes" : "No") + "\n");
                taReport.append("-----\n");
            }
        } else if (cmd.equals("Clear")) {
            tfId.setText("");
            tfName.setText("");
            taReport.setText("");
        }
    }

    public static void main(String[] args) {
        new AttendanceTrackingSystems();
    }
}

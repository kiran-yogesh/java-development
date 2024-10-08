import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
public class AttendanceTrackingSystem extends JFrame implements ActionListener {
    private List<Employee> employees;
    private TextField tfId, tfName;
    private TextArea taReport;

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
    public AttendanceTrackingSystem() {
        employees = new ArrayList<>();

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

        Button btnClr= new Button("Clear");
        btnClr.addActionListener(this);
        btnClr.setBackground(Color.black);
        btnClr.setForeground(Color.white);
        add(btnClr);

        taReport = new TextArea(20, 50);
        taReport.setForeground(Color.BLACK);
        add(taReport);
    

        setTitle("Employee Attendance System");
        setSize(600, 400);
        setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        String id = tfId.getText();
        String name = tfName.getText();

        if (cmd.equals("Clock In")) {
            Employee employee = new Employee(id, name);
            employees.add(employee);
            taReport.append("Added Employee: " + name + "\n");
            if (employee.getId().equals(id)) {
                employee.setClockInTime(new Date());
                taReport.append(employee.getName() + " clocked in at " + employee.getClockInTime() + "\n");
                return;
            }
        } 
         else if (cmd.equals("Clock Out")) {
            for (Employee employee : employees) {
                if (employee.getId().equals(id)) {
                    employee.setClockOutTime(new Date());
                    taReport.append(employee.getName() + " clocked out at " + employee.getClockOutTime() + "\n");
                    return;
                }
            }
            taReport.append("Employee not found.\n");
        } else if (cmd.equals("Request Leave")) {
                    taReport.append(name + " is on leave.\n");
                    return;      
            
        } else if (cmd.equals("Generate Report")) {
            taReport.setText("");
            for (Employee employee : employees) {
                taReport.append("Employee ID: " + employee.getId() + "\n");
                taReport.append("Name: " + employee.getName() + "\n");
                taReport.append("Clock-in Time: " + employee.getClockInTime() + "\n");
                taReport.append("Clock-out Time: " + employee.getClockOutTime() + "\n");
                taReport.append("On Leave: " + employee.isOnLeave() + "\n");
                taReport.append("-----\n");
            }
        }else if (cmd.equals("Clear")) {
            tfId.setText("");
            tfName.setText("");
            taReport.setText("");
        }
    }

    public static void main(String[] args) {
        new AttendanceTrackingSystem();
    }
}

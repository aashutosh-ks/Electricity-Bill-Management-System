package ui;
import model.ElectricityBill;
import model.UserData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class AdminDashboard extends JFrame {
    private final UserData userData;
    private final ElectricityBill billManager;
    public AdminDashboard(String adminName) {
        setTitle("Admin Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        userData = new UserData();
        billManager = new ElectricityBill();
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        JButton registerBtn = new JButton("Register New Customer");
        JButton deleteBtn = new JButton("Delete Customer");
        JButton viewCustomersBtn = new JButton("View All Customers");
        JButton generateBillBtn = new JButton("Generate Bill");
        JButton viewBillBtn = new JButton("View Customer Bill History");
        JButton logoutBtn = new JButton("Logout");
        panel.add(registerBtn);
        panel.add(deleteBtn);
        panel.add(viewCustomersBtn);
        panel.add(generateBillBtn);
        panel.add(viewBillBtn);
        panel.add(logoutBtn);
        add(panel);
        registerBtn.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Enter new customer's username:");
            String password = JOptionPane.showInputDialog(this, "Enter password:");
            if (userData.register(username, password, "customer")) {
                JOptionPane.showMessageDialog(this, "Customer registered successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        deleteBtn.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Enter username to delete:");
            userData.deleteCustomer(username);
        });
        viewCustomersBtn.addActionListener(e -> userData.viewAllCustomers());
        generateBillBtn.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Enter customer username:");
            double lastReading = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter last meter reading:"));
            double currentReading = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter current meter reading:"));
            int month = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter month (1-12):"));
            int year = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter year (e.g., 2025):"));
            billManager.generateBill(username, lastReading, currentReading, month, year);
        });
        viewBillBtn.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Enter customer username:");
            billManager.viewBillHistory(username, true);
        });
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });
        setVisible(true);
    }
}
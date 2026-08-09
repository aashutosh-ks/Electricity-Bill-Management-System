package ui;
import model.ElectricityBill;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class CustomerDashboard extends JFrame {
    private final ElectricityBill billManager;
    private final String username;
    public CustomerDashboard(String username) {
        this.username = username;
        billManager = new ElectricityBill();
        setTitle("Customer Dashboard - " + username);
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton viewUnpaidBtn = new JButton("View Unpaid Bills");
        JButton viewPaidBtn = new JButton("View Paid Bills");
        JButton payBillBtn = new JButton("Pay Bills");
        JButton logoutBtn = new JButton("Logout");
        panel.add(viewUnpaidBtn);
        panel.add(viewPaidBtn);
        panel.add(payBillBtn);
        panel.add(logoutBtn);
        add(panel);
        viewUnpaidBtn.addActionListener(e -> billManager.viewBillHistory(username, false));
        viewPaidBtn.addActionListener(e -> billManager.viewBillHistory(username, true));
        payBillBtn.addActionListener(e -> billManager.payBill(new java.util.Scanner(System.in), username));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });
        setVisible(true);
    }
}
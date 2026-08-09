package ui;
import model.UserData;
import javax.swing.*;
import java.awt.*;
public class LoginFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final UserData userData;
    public LoginFrame() {
        userData = new UserData();
        setTitle("Electricity Bill System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> register());
        setVisible(true);
    }
    private void login() {
        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();
        System.out.println("Attempting to login with username: " + username);
        if (userData.login(username, password)) {
            String role = userData.getUserRole(username);
            System.out.println("Login successful for " + username + " with role " + role);
            dispose();
            if ("admin".equals(role)) {
                new AdminDashboard(username);
            } else if ("customer".equals(role)) {
                new CustomerDashboard(username);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid role assigned to user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Login failed for username: " + username);
            JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void register() {
        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();
        if (userData.register(username, password, "customer")) {
            JOptionPane.showMessageDialog(this, "Registered successfully. You can now log in.");
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
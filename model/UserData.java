package model;
import java.io.*;
import java.util.*;

public class UserData {
    private final Map<String, User> users = new HashMap<>();
    private final String filePath = "C:\\Users\\lenovo\\OneDrive\\Desktop\\src\\data\\users.csv";
    public UserData() {
        loadUsersFromCSV(filePath);
    }
    private void loadUsersFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String role = parts[2].trim();
                    users.put(username, new User(username, password, role));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }
    private void saveUsersToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("username,password,role\n");
            for (User user : users.values()) {
                bw.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    public boolean login(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }
    public String getUserRole(String username) {
        User user = users.get(username);
        return user != null ? user.getRole() : null;
    }
    public boolean register(String username, String password, String role) {
        if (users.containsKey(username)) {
            return false;
        }
        User newUser = new User(username, password, role);
        users.put(username, newUser);
        saveUsersToCSV();
        return true;
    }
    public void deleteCustomer(String username) {
        users.remove(username);
        saveUsersToCSV();
    }
    public void viewAllCustomers() {
        for (User user : users.values()) {
            if ("customer".equals(user.getRole())) {
                System.out.println("• " + user.getUsername());
            }
        }
    }
}
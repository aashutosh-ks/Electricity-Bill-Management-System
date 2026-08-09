package model;

import javax.swing.*;
import java.io.*;
import java.util.*;
public class ElectricityBill {
    private final String billFile = "bills.csv";
    public ElectricityBill() {
        File file = new File(billFile);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(billFile))) {
                pw.println("username,units,amount,status,month,year");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void generateBill(String username, double lastReading, double currentReading, int month, int year) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(billFile, true))) {
            double units = currentReading - lastReading;
            double amount = calculateBill(units);
            writer.write(username + "," + units + "," + amount + ",Unpaid," + month + "," + year + "\n");
            JOptionPane.showMessageDialog(null, "Bill generated successfully for " + username);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing bill: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void viewBillHistory(String username, boolean paid) {
        StringBuilder builder = new StringBuilder("--- Bill History for " + username + " ---\n");
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(billFile))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username) && data[3].equalsIgnoreCase(paid ? "Paid" : "Unpaid")) {
                    builder.append("Month: ").append(data[4]).append("/").append(data[5])
                           .append(", Units: ").append(data[1])
                           .append(", Amount: ₹").append(data[2])
                           .append(", Status: ").append(data[3]).append("\n");
                    found = true;
                }
            }
            if (!found) builder.append("No bills found.");
            JOptionPane.showMessageDialog(null, builder.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading bill file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void payBill(Scanner scanner, String username) {
        List<String> updated = new ArrayList<>();
        boolean paidAny = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(billFile))) {
            reader.readLine();
            updated.add("username,units,amount,status,month,year");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username) && data[3].equalsIgnoreCase("Unpaid")) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Pay bill for " + data[4] + "/" + data[5] + "? Amount: ₹" + data[2], "Pay Bill", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        data[3] = "Paid";
                        paidAny = true;
                    }
                }
                updated.add(String.join(",", data));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading bill file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(billFile))) {
            for (String line : updated) writer.write(line + "\n");
            if (paidAny) {
                JOptionPane.showMessageDialog(null, "Bill(s) paid successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "No bills were paid.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to bill file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private double calculateBill(double units) {
        if (units <= 50) return units * 5.5;
        else if (units <= 100) return units * 6.5;
        else return units * 8.0;
    }
}
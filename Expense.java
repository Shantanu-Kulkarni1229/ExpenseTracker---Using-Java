// Import Serializable interface to allow Expense objects to be saved to files
import java.io.Serializable;

public class Expense implements Serializable {
    // Declare properties of the Expense class
    private String category;       // Category of the expense, e.g., "Food"
    private double amount;         // Amount spent
    private String date;           // Date of the expense in a specified format
    private String description;    // Description or details about the expense

    // Constructor to initialize an Expense object with the specified properties
    public Expense(String category, double amount, String date, String description) {
        this.category = category;      // Set the category
        this.amount = amount;          // Set the amount
        this.date = date;              // Set the date
        this.description = description; // Set the description
    }

    // Getter method to retrieve the category of the expense
    public String getCategory() {
        return category;
    }

    // Getter method to retrieve the amount of the expense
    public double getAmount() {
        return amount;
    }

    // Getter method to retrieve the date of the expense
    public String getDate() {
        return date;
    }

    // Getter method to retrieve the description of the expense
    public String getDescription() {
        return description;
    }

    // Override the toString method to provide a formatted string representation of the expense
    @Override
    public String toString() {
        // Format the string to include date, category, amount with 2 decimal places, and description
        return String.format("Date: %s, Category: %s, Amount: %.2f, Description: %s", date, category, amount, description);
    }
}

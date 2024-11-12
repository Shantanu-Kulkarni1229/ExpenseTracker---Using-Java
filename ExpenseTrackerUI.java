// Importing necessary libraries for GUI components, file handling, and collections
import javax.swing.*; // For GUI elements like JFrame, JButton, JLabel, etc.
import java.awt.*; // For layout management, specifically GridLayout here
import java.io.*; // For file operations to save/load data
import java.util.ArrayList; // ArrayList used to store a list of expenses
import java.util.List; // List interface for handling multiple expenses

// Class definition for the main user interface of the Expense Tracker
public class ExpenseTrackerUI extends JFrame {
    private User user; // Stores the currently logged-in user
    private CategoryManager categoryManager; // Manages expense categories
    private List<Expense> expenses; // Stores a list of expenses for the user

    // Constructor to initialize the UI with the logged-in user's information
    public ExpenseTrackerUI(User user) {
        this.user = user;
        this.categoryManager = new CategoryManager(user.getUsername());
        this.expenses = loadExpenses(); // Load existing expenses from file

        setTitle("Personal Expense Tracker - Welcome " + user.getUsername());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1)); // GridLayout for simple vertical button arrangement

        // Creating buttons for various features
        JButton addExpenseButton = new JButton("Add Expense");
        JButton viewExpensesButton = new JButton("View Expenses");
        JButton manageCategoriesButton = new JButton("Manage Categories");
        JButton generateReportButton = new JButton("Generate Report");
        JButton logOutButton = new JButton("Log Out");

        // Adding buttons to the UI layout
        add(addExpenseButton);
        add(viewExpensesButton);
        add(manageCategoriesButton);
        add(generateReportButton);
        add(logOutButton);

        // Adding action listeners for each button to handle button clicks
        addExpenseButton.addActionListener(e -> openAddExpenseDialog());
        viewExpensesButton.addActionListener(e -> viewExpenses());
        manageCategoriesButton.addActionListener(e -> manageCategories());
        generateReportButton.addActionListener(e -> generateReport());
        logOutButton.addActionListener(e -> logout());

        setVisible(true); // Display the UI window
    }

    // Method to load expenses for the user from a file
    private List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("expenses_" + user.getUsername() + ".dat"))) {
            expenses = (List<Expense>) in.readObject(); // Load expenses from file
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No expenses found for this user."); // If no file exists
        }
        return expenses; // Return loaded or empty list
    }

    // Method to save current expenses to a file for persistent storage
    private void saveExpenses() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("expenses_" + user.getUsername() + ".dat"))) {
            out.writeObject(expenses); // Save the list of expenses to the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to display a dialog for adding a new expense
    private void openAddExpenseDialog() {
        // Select a category from existing categories in CategoryManager
        String category = (String) JOptionPane.showInputDialog(this, "Select Category:", "Category",
                JOptionPane.QUESTION_MESSAGE, null, categoryManager.getCategories().toArray(), "Food");
    
        if (category != null) {
            String amountStr = JOptionPane.showInputDialog(this, "Enter Amount:"); // Get amount from user
            String date = JOptionPane.showInputDialog(this, "Enter Date:"); // Get date of expense
            String description = JOptionPane.showInputDialog(this, "Enter Description:"); // Get description
    
            double amount = Double.parseDouble(amountStr); // Convert amount to double
            Expense expense = new Expense(category, amount, date, description); // Create new expense
            expenses.add(expense); // Add to list of expenses
            saveExpenses(); // Save updated list to file
    
            // Show confirmation message after adding expense
            JOptionPane.showMessageDialog(this, "Expense added successfully!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // Method to display all expenses in a dialog box
    private void viewExpenses() {
        StringBuilder sb = new StringBuilder();
        for (Expense expense : expenses) {
            sb.append(expense.toString()).append("\n"); // Append each expense to StringBuilder
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Expenses", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to manage categories by adding or removing categories
    private void manageCategories() {
        String[] options = {"Add Category", "Remove Category"};
        int choice = JOptionPane.showOptionDialog(this, "Manage Categories", "Categories",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) { // Add a new category
            String newCategory = JOptionPane.showInputDialog(this, "Enter New Category:");
            categoryManager.addCategory(newCategory); // Add to CategoryManager
        } else if (choice == 1) { // Remove an existing category
            String categoryToRemove = (String) JOptionPane.showInputDialog(this, "Select Category to Remove:", "Remove Category",
                    JOptionPane.QUESTION_MESSAGE, null, categoryManager.getCategories().toArray(), "Food");
            if (categoryToRemove != null) {
                categoryManager.removeCategory(categoryToRemove); // Remove from CategoryManager
            }
        }
    }

    // Method to generate a summary report of all expenses
    private void generateReport() {
        StringBuilder report = new StringBuilder("Expense Report:\n\n");
        double total = 0.0;
        for (Expense expense : expenses) {
            total += expense.getAmount(); // Calculate total expense
            report.append(expense).append("\n"); // Append each expense to report
        }
        report.append("\nTotal Expenditure: ").append(total); // Append total expenditure
        JOptionPane.showMessageDialog(this, report.toString(), "Expense Report", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to log out the user and return to the login screen
    private void logout() {
        JOptionPane.showMessageDialog(this, "Logged out successfully.");
        new LoginUI(); // Display the login screen again
        dispose(); // Close the current expense tracker UI
    }
}

// File: LoginUI.java

// Importing necessary packages for GUI components and layout management
import javax.swing.*;  // Import Swing library for GUI elements
import java.awt.*;     // Import AWT library for layout managers

// Declares the LoginUI class, which extends JFrame to create a window-based application
public class LoginUI extends JFrame {
    
    // Text field for entering the username
    private JTextField usernameField;
    // Password field for entering the password
    private JPasswordField passwordField;

    // Constructor to initialize the login/register window
    public LoginUI() {
        // Set the title of the window
        setTitle("Login/Register");
        // Set the size of the window
        setSize(300, 200);
        // Specify the operation that will happen by default when the user closes the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the layout of the frame as a 4x1 grid layout
        setLayout(new GridLayout(4, 1));

        // Initialize the text fields for username and password
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        // Create login and register buttons
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Add components to the frame in order of appearance
        add(new JLabel("Username:"));     // Label for username field
        add(usernameField);               // Username input field
        add(new JLabel("Password:"));     // Label for password field
        add(passwordField);               // Password input field
        add(loginButton);                 // Login button
        add(registerButton);              // Register button

        // Add action listeners to buttons to specify actions when clicked
        loginButton.addActionListener(e -> login());           // Calls login method when clicked
        registerButton.addActionListener(e -> register());     // Calls register method when clicked

        // Make the window visible
        setVisible(true);
    }

    // Method to register a new user
    private void register() {
        // Retrieve text from username and password fields
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check if a user with the entered username already exists
        if (User.loadUser(username) != null) {
            // Show a message dialog if the user already exists
            JOptionPane.showMessageDialog(this, "User already exists!");
        } else {
            // Create a new user with the entered username and password
            User newUser = new User(username, password);
            // Save the new user to persistent storage
            User.saveUser(newUser);
            // Show a message dialog to confirm successful registration
            JOptionPane.showMessageDialog(this, "Registration successful!");
        }
    }

    // Method to log in an existing user
    private void login() {
        // Retrieve text from username and password fields
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Load the user with the specified username from storage
        User user = User.loadUser(username);
        
        // Check if the user exists
        if (user != null) {
            // If the user exists, verify if the password is correct
            if (user.checkPassword(password)) {
                // Show a message dialog confirming successful login
                JOptionPane.showMessageDialog(this, "Login successful!");
                // Open the main application window with the logged-in user
                new ExpenseTrackerUI(user);
                // Close the login window
                dispose();
            } else {
                // Show a message dialog if the password is incorrect
                JOptionPane.showMessageDialog(this, "Password is incorrect!");
            }
        } else {
            // Show a message dialog if the user does not exist
            JOptionPane.showMessageDialog(this, "User does not exist. Please register first.");
        }
    }
}

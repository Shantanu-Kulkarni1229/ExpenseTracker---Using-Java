// Import necessary classes for handling input/output operations
import java.io.*;

public class User implements Serializable { // User class implements Serializable to allow objects to be saved to files
    private String username;  // Stores the username of the user
    private String password;  // Stores the password of the user

    // Constructor to initialize a User object with username and password
    public User(String username, String password) {
        this.username = username;    // Set the username
        this.password = password;    // Set the password
    }

    // Getter method to retrieve the username of the user
    public String getUsername() {
        return username;
    }

    // Method to check if the provided password matches the user's password
    public boolean checkPassword(String password) {
        return this.password.equals(password);  // Return true if passwords match, false otherwise
    }

    // Static method to save user data to a file
    public static void saveUser(User user) {
        try (FileWriter fw = new FileWriter("users.txt", true);           // FileWriter to write to "users.txt" file in append mode
             BufferedWriter bw = new BufferedWriter(fw);                  // BufferedWriter to improve write efficiency
             PrintWriter out = new PrintWriter(bw)) {                     // PrintWriter to write formatted output
            // Write user's username and password in CSV format
            out.println(user.username + "," + user.password);
        } catch (IOException e) {
            e.printStackTrace();  // Print stack trace if an I/O exception occurs
        }
    }

    // Static method to load user data from a file and return a User object if found
    public static User loadUser(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) { // BufferedReader to read from "users.txt" file
            String line;
            // Loop through each line in the file to find the matching username
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Split line into username and password
                if (data[0].equals(username)) {  // Check if the username matches
                    return new User(data[0], data[1]); // Return User object if username is found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  // Print stack trace if an I/O exception occurs
        }
        return null;  // Return null if username is not found
    }
}

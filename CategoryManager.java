// Import necessary libraries for file handling and data structures
import java.io.*;
import java.util.*;

public class CategoryManager {
    // Default categories file containing initial categories for new users
    private static final String DEFAULT_CATEGORIES_FILE = "default_categories.txt";
    private String categoriesFile; // The file that stores user-specific categories

    // Constructor to set the categories file name based on the user's username
    public CategoryManager(String username) {
        // The filename is based on the username to create user-specific files
        this.categoriesFile = "categories_" + username + ".txt";
        // Initialize default categories if the user file does not exist
        initializeDefaultCategories();
    }

    // Method to create default categories if the user's categories file is missing
    private void initializeDefaultCategories() {
        // Creates a new File object for the user-specific categories file
        File file = new File(categoriesFile);
        // Check if the file exists; if not, create and initialize it
        if (!file.exists()) {
            // BufferedWriter is used to write to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Write default categories to the file, each on a new line
                writer.write("Food\nUtilities\nEntertainment\nTransport\nMiscellaneous\n");
            } catch (IOException e) { // Handle any IO exceptions that occur
                e.printStackTrace(); // Print stack trace for debugging
            }
        }
    }

    // Method to retrieve all categories for the user as a List of Strings
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>(); // List to store categories
        // BufferedReader to read from the categories file
        try (BufferedReader reader = new BufferedReader(new FileReader(categoriesFile))) {
            String line;
            // Read each line (category) in the file until end of file
            while ((line = reader.readLine()) != null) {
                categories.add(line.trim()); // Add the category to list after trimming spaces
            }
        } catch (IOException e) { // Handle exceptions during file reading
            e.printStackTrace(); // Print stack trace for debugging
        }
        return categories; // Return the list of categories
    }

    // Method to add a new category to the user's file
    public void addCategory(String category) {
        // BufferedWriter in append mode to add a new category without overwriting
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(categoriesFile, true))) {
            writer.write(category + "\n"); // Write the new category followed by a newline
        } catch (IOException e) { // Handle exceptions during file writing
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    // Method to remove an existing category from the user's categories file
    public void removeCategory(String category) {
        List<String> categories = getCategories(); // Fetch current categories
        categories.remove(category); // Remove the specified category from the list

        // Rewrite the file with the updated categories list
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(categoriesFile))) {
            // Loop through each category in the updated list
            for (String cat : categories) {
                writer.write(cat + "\n"); // Write each category back to the file
            }
        } catch (IOException e) { // Handle exceptions during file writing
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
}

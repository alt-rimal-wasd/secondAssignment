/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project1;

import java.util.regex.Pattern;

/**
 *
 * @author emort
 */
public class Customer {

    private String name;
    private String phoneNumber;
    private String email;

    public Customer(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        if (isValidEmail(email)) {
            this.email = email;
        } else {
            System.out.println("Warning: Invalid email format for " + name + ". Email skipped.");
            this.email = "unknown@example.com";  // Set default email or leave it empty
        }
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    // got help from chatGPT to validate email format. 
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,3}$";
        return Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }

}

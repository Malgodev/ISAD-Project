package com.malgo.malgo.controller;

import com.malgo.malgo.model.Customer;
import com.malgo.malgo.util.PasswordValidator;

import jakarta.servlet.http.HttpSession;

import com.malgo.malgo.service.CustomerService;

import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;


@Controller
public class LoginController {

    @Autowired
    private CustomerService customerService;

    // Display login form
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "login";
    }

    // Handle login submission
    @PostMapping("/login")
    public String login(@ModelAttribute Customer customer, Model model) {
        var existingCustomer = customerService.findByUsername(customer.getUsername());

        if (existingCustomer.isPresent() && existingCustomer.get().getPassword().equals(customer.getPassword())) {
            model.addAttribute("message", "Yay! You are logged in!");
            return "login";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    // Display registration form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    // Handle registration submission
    @PostMapping("/register")
    public String register(@ModelAttribute Customer customer, Model model) {
        
        if (!PasswordValidator.isValidPassword(customer.getPassword())) {
            model.addAttribute("error", PasswordValidator.getValidationErrorMessage());
            return "register";
        }

        if (customerService.findByUsername(customer.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        customerService.saveCustomer(customer);
        return "redirect:/login";
    }

    // Display change password form
    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "change-password";
    }

    // Handle change password submission
    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute Customer customer, @RequestParam String newPassword, Model model) {
        var existingCustomer = customerService.findByUsername(customer.getUsername());

        if (existingCustomer.isPresent() && existingCustomer.get().getPassword().equals(customer.getPassword())) {
            
            if (!PasswordValidator.isValidPassword(newPassword)) {
                model.addAttribute("error", PasswordValidator.getValidationErrorMessage());
                return "register";
            }

            existingCustomer.get().setPassword(newPassword);

            customerService.saveCustomer(existingCustomer.get());
            model.addAttribute("message", "Password changed successfully");
            return "login";
        } else {
            model.addAttribute("error", "Invalid current password");
            return "change-password";
        }
    }

    // Display forgot password form
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "forgot-password";
    }

    // Handle forgot password submission
    // @PostMapping("/forgot-password")
    // public String forgotPassword(@ModelAttribute Customer customer, @RequestParam String newPassword, Model model) {
    //     var existingCustomer = customerService.findByUsername(customer.getUsername());

    //     if (existingCustomer.isPresent()) {

    //         if (!PasswordValidator.isValidPassword(newPassword)) {
    //             model.addAttribute("error", PasswordValidator.getValidationErrorMessage());
    //             return "register";
    //         }

    //         existingCustomer.get().setPassword(newPassword);
    //         customerService.saveCustomer(existingCustomer.get());
    //         model.addAttribute("message", "Password reset successfully");
    //         return "login";
    //     } else {
    //         model.addAttribute("error", "Username not found");
    //         return "forgot-password";
    //     }
    // }

   private final Map<String, String> userPins = new HashMap<>(); // Store user PINs temporarily

    // Request PIN for password reset
    // @PostMapping("/forgot-password/request-pin")
    // public String requestPin(@RequestParam String username, Model model, HttpSession session) {
    //     Optional<Customer> existingCustomer = customerService.findByUsername(username);
    //     session.setAttribute("username", username);
    //     if (existingCustomer.isPresent()) {
    //         String pin = generateRandomPin();
    //         if (userPins.containsKey(username)) {
    //             userPins.remove(username);
    //         }
    //         userPins.put(username, pin); // Store the generated PIN
    //         System.out.println("Generated PIN for " + username + ": " + pin);
    //         model.addAttribute("message", "PIN sent to your registered contact method.");
    //         return ResponseEntity.ok("PIN sent to your registered contact method.");
    //     } else {
    //         model.addAttribute("error", "Username not found.");
    //         return "forgot-password";
    //     }
    // }

    @PostMapping("/forgot-password/request-pin")
    public ResponseEntity<String> requestPin(@RequestParam String username, Model model, HttpSession session) {
        Optional<Customer> existingCustomer = customerService.findByUsername(username);
        session.setAttribute("username", username);
        if (existingCustomer.isPresent()) {
            String pin = generateRandomPin();
    
            if (userPins.containsKey(username)) {
                userPins.remove(username);
            }
    
            userPins.put(username, pin); // Store the generated PIN
            System.out.println("Generated PIN for " + username + ": " + pin);
            model.addAttribute("message", "PIN sent to your registered contact method.");
            return ResponseEntity.ok("PIN sent to your registered contact method.");
        } else {
            model.addAttribute("error", "Username not found.");
            return ResponseEntity.ok("forgot-password");
        }
    }

    // Reset password with PIN
@PostMapping("/forgot-password/reset-password")
@ResponseBody
public ResponseEntity<?> resetPassword(@RequestParam String username, @RequestParam String pin, @RequestParam String newPassword) {
    String storedPin = userPins.get(username);
    if (storedPin != null && storedPin.equals(pin)) {
        if (PasswordValidator.isValidPassword(newPassword)) {
            Optional<Customer> existingCustomer = customerService.findByUsername(username);
            if (existingCustomer.isPresent()) {
                existingCustomer.get().setPassword(newPassword);
                customerService.saveCustomer(existingCustomer.get());
                userPins.remove(username); // Clear the stored PIN
                return ResponseEntity.ok("Password reset successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PasswordValidator.getValidationErrorMessage());
        }
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid PIN.");
    }
}

    // Reset password with PIN
    // @PostMapping("/forgot-password/reset-password")
    // public String resetPassword(@RequestParam String username, @RequestParam String pin, @RequestParam String newPassword, Model model) {
    //     String storedPin = userPins.get(username);
    //     for (String smt : userPins.keySet()) {
    //         System.out.println(smt + " " + userPins.get(smt) + " " + username.length());
    //     }
    //     if (storedPin != null && storedPin.equals(pin)) {
    //         // Validate the new password
    //         if (PasswordValidator.isValidPassword(newPassword)) {
    //             Optional<Customer> existingCustomer = customerService.findByUsername(username);
    //             if (existingCustomer.isPresent()) {
    //                 existingCustomer.get().setPassword(newPassword);
    //                 customerService.saveCustomer(existingCustomer.get());
    //                 model.addAttribute("message", "Password reset successfully.");
    //                 userPins.remove(username); // Clear the stored PIN
    //                 return "login"; // Redirect to login after successful password reset
    //             } else {
    //                 model.addAttribute("error", "User not found.");
    //                 return "forgot-password";
    //             }
    //         } else {
    //             model.addAttribute("error", PasswordValidator.getValidationErrorMessage());
    //             return "forgot-password";
    //         }
    //     } else {
    //         model.addAttribute("error", "Invalid PIN.");
    //         return "forgot-password";
    //     }
    // }

    // Generate a random 6-digit PIN
    private String generateRandomPin() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    // Display all customers (for administrative purposes)
    @GetMapping("/all")
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", customerService.findAllCustomers());
        return "all";
    }
}

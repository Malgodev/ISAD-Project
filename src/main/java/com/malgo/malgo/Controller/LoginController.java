package com.malgo.malgo.controller;

import com.malgo.malgo.model.Customer;
import com.malgo.malgo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("Registering customer: " + customer.getUsername());

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
    @PostMapping("/forgot-password")
    public String forgotPassword(@ModelAttribute Customer customer, @RequestParam String newPassword, Model model) {
        var existingCustomer = customerService.findByUsername(customer.getUsername());

        if (existingCustomer.isPresent()) {
            existingCustomer.get().setPassword(newPassword);
            customerService.saveCustomer(existingCustomer.get());
            model.addAttribute("message", "Password reset successfully");
            return "login";
        } else {
            model.addAttribute("error", "Username not found");
            return "forgot-password";
        }
    }

    // Display all customers (for administrative purposes)
    @GetMapping("/all")
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", customerService.findAllCustomers());
        return "all";
    }
}

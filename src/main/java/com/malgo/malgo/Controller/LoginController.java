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

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Customer customer, Model model) {
        var existingCustomer = customerService.findByUsername(customer.getUsername());

        if (existingCustomer.isPresent() && existingCustomer.get().getPassword().equals(customer.getPassword())) {
            model.addAttribute("error", "Yay! You are logged in!");
            System.out.println("Yay! You are logged in!");
            return "login";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Customer customer, Model model) {
        if (customerService.findByUsername(customer.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        customerService.saveCustomer(customer);
        return "redirect:/login";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "change-password";
    }

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

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "forgot-password";
    }

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

    @GetMapping("/all")
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", customerService.findAllCustomers());
        return "all";
    }
}

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

        if (existingCustomer.isPresent()) {
            if (existingCustomer.get().getPassword().equals(customer.getPassword())) {
                // return "redirect:/home";
                model.addAttribute("error", "Yay! You are logged in!");
                System.out.println("Yay! You are logged in!");
                return "login";
            }
            else{
                model.addAttribute("error", "Invalid password");
                return "login";
            }
        }
        else {
            model.addAttribute("error", "Invalid username");
            return "login";
        }
    }

    @GetMapping("/all")
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", customerService.findAllCustomers());
        System.out.println("All customers: " + customerService.findAllCustomers());
        return "all";
    }
}

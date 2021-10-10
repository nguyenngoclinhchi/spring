package com.example.demo.controller;

import com.example.demo.model.Address;
import com.example.demo.model.Customer;
import com.example.demo.model.Result;
import com.example.demo.service.AddressService;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@ComponentScan("com.example.demo.service")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private AddressService addressService;
    
    // display list of Customers
    @GetMapping("/")
    public String viewHomePage(Model model) {
        return findPaginated(1, "firstName", "asc", model);
    }
    
    @GetMapping("/showNewCustomerForm")
    public String showNewCustomerForm(Model model) {
        // create model attribute to bind form data
        Customer customer = new Customer();
        Address address = new Address();
        model.addAttribute("customer", customer);
        model.addAttribute("address", address);
        return "new_customer";
    }
    
    @PostMapping("/saveCustomer")
    public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer,
                               @Valid @ModelAttribute("address") Address address,
                               @RequestParam(value = "action") String action,
                               Model model) {
        try {
            Result customer_result = customerService.saveCustomer(customer);
            if (action.equals("save_customer")) {
                // save Customer and Address to database
                System.out.println("*************************************** ADD_CUSTOMER: Current customer has id as: " + customer_result.getId());
                addressService.saveAddress(new Address(address, customer_result.getId()));
                customer.addListAddress(addressService.findByCustomerid(customer_result.getId()));
                if (!customer_result.getMessage().equals("successful")) {
                    model.addAttribute("message", "Detect customer with same email saved before. This customer will not be saved");
                    // return "redirect:/";
                } else {
                    model.addAttribute("message", "Customer saved!");
                }
            } else {
                
                // save Customer and Address to database
                addressService.saveAddress(new Address(address, customer_result.getId()));
                
                System.out.println("*************************************** ADD_ADDRESS: Current customer has id as: " + customer_result.getId());
                customer.addListAddress(addressService.findByCustomerid(customer_result.getId()));
                model.addAttribute("message", "New Address saved!");
                model.addAttribute("customer", customer);
                model.addAttribute("address", address);
            }
            return "new_customer";
        } catch (NumberFormatException e) {
            System.out.println("========================Field should be integer");
            model.addAttribute("error_message_integer", "Field should be integer");
            return "new_customer";
        }
    }
    
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Integer id, Model model) {
        
        // get Customer from the service
        Customer customer = customerService.getById(id);
        
        // set Customer as a model attribute to pre-populate the form
        model.addAttribute("customer", customer);
        return "update_customer";
    }
    
    @GetMapping("/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable(value = "id") Integer id) {
        customerService.deleteById(id);
        addressService.deleteByCustomerid(id);
        return "redirect:/";
    }
    
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;
        
        Page<Customer> page = customerService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Customer> listCustomers = page.getContent();
        
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        
        model.addAttribute("listCustomers", listCustomers);
        return "index";
    }
    
    // TODO: Add search bar into the index page
    // Reference: https://stackoverflow.com/questions/54481219/how-to-set-up-a-search-bar-using-spring-boot-jpa-and-thymeleaf
    
}

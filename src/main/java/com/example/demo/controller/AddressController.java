package com.example.demo.controller;

import com.example.demo.model.Address;
import com.example.demo.model.Customer;
import com.example.demo.service.AddressService;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@Controller
public class AddressController {
    @Autowired
    CustomerService customerService;
    
    @Autowired
    AddressService addressService;
    
    @GetMapping("/addAddressInline")
    public String addAddressInline_save(@Valid @ModelAttribute("customer") Customer customer,
                                        @Valid @ModelAttribute("address") Address address, Model model) {
        addressService.saveAddress(new Address(address, customer.getId()));
        // customer.addListAddress(address);
        return "new_customer";
    }
    
    @GetMapping("/saveCustomer/deleteAddressInline/{id}")
    public String deleteAddressInline_save(@PathVariable(value = "id") Integer id) {
        addressService.deleteById(id);
        return "new_customer";
    }
    
    @GetMapping("/updateCustomer/addAddressInline")
    public String addAddressInline_update(@Valid @ModelAttribute("customer") Customer customer,
                                          @Valid @ModelAttribute("address") Address address, Model model) {
        address.setCustomerid(customer.getId());
        addressService.saveAddress(address);
        model.addAttribute("customer", customer);
        model.addAttribute("address", address);
        
        return "save_customer";
    }
    
    @GetMapping("/updateCustomer/deleteAddressInline/{id}")
    public String deleteAddressInline_update(@PathVariable(value = "id") Integer id) {
        addressService.deleteById(id);
        return "save_customer";
    }
}

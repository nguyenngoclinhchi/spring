package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Result;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<Customer> getAllCustomers();
    
    Result saveCustomer(Customer customer);
    
    Customer getById(Integer id);
    
    void deleteById(Integer id);
    
    Page<Customer> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    
}

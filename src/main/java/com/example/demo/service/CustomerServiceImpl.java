package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Result;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    @Override
    public Result saveCustomer(Customer customer) {
        Optional<Customer> existingCustomer = getAllCustomers().stream().filter(p -> p.equals(customer)).findFirst();
        Optional<Customer> duplicateCustomer = getAllCustomers().stream().filter(p -> p.equalsStrictly(customer)).findFirst();
        if (!existingCustomer.isPresent()) {
            customerRepository.save(customer);
            return new Result(customer.getId(), "successful");
        } else if (duplicateCustomer.isPresent()) {
            return new Result(duplicateCustomer.get().getId(), "successful");
        }
        return new Result(existingCustomer.get().getId(), "email duplicate detected!");
    }
    
    @Override
    public Customer getById(Integer id) {
        Optional<Customer> optional = customerRepository.findById(id);
        Customer customer;
        if (optional.isPresent()) {
            customer = optional.get();
        } else {
            throw new RuntimeException(" Customer not found for id :: " + id);
        }
        return customer;
    }
    
    @Override
    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }
    
    @Override
    public Page<Customer> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                        Sort.by(sortField).descending();
        
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return customerRepository.findAll(pageable);
    }
}

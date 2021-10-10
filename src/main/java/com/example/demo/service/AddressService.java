package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.model.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    List<Address> getAllAddresses();
    
    List<Address> findByCustomerid(Integer customerid);
    
    Result saveAddress(Address address);
    
    Address findById(Integer id);
    
    void deleteById(Integer id);
    
    void deleteByCustomerid(Integer id);
    // Page<Address> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    
}

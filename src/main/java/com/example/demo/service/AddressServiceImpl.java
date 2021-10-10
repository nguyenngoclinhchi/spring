package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.model.Result;
import com.example.demo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;
    
    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }
    
    @Override
    public List<Address> findByCustomerid(Integer customerid) {
        return addressRepository.findByCustomerid(customerid);
    }
    
    @Override
    public Result saveAddress(Address address) {
        Optional<Address> existingAddress = getAllAddresses().stream().filter(p -> p.equals(address)).findFirst();
        if (!existingAddress.isPresent()) {
            addressRepository.save(address);
            return new Result(address.getId(), "successful");
        }
        return new Result(address.getId(), "unsuccessful");
    }
    
    @Override
    public Address findById(Integer id) {
        Optional<Address> optional = addressRepository.findById(id);
        Address address;
        if (optional.isPresent()) {
            address = optional.get();
        } else {
            throw new RuntimeException(" Address not found for id :: " + id);
        }
        return address;
    }
    
    @Override
    public void deleteById(Integer id) {
        addressRepository.deleteById(id);
    }
    
    @Override
    public void deleteByCustomerid(Integer id) {
    
    }
}

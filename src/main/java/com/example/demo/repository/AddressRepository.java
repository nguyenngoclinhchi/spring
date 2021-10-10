package com.example.demo.repository;

import com.example.demo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Integer deleteByCustomerid(Integer customerid);
    
    List<Address> findByCustomerid(Integer customerid);
    
}

package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Valid
    @NotEmpty(message = "{street.not.empty}")
    @Size(max = 255, message = "{street.size}")
    @Column(nullable = false)
    private String street;
    
    @NotNull(message = "{unit.not.empty}")
    @Column(nullable = false)
    private Integer unitNumber;
    
    @NotNull(message = "{postalcode.not.empty}")
    @Column(nullable = false)
    private Integer postalCode;
    
    @Valid
    @ManyToOne
    @JoinColumn(name = "customerid", insertable = false, updatable = false, nullable = false)
    private Customer customer;
    
    @Column(nullable = false)
    private Integer customerid;
    
    public Address() {
        this.street = "";
        this.unitNumber = 0;
        this.postalCode = 0;
        this.customerid = -1;
    }
    
    public Address(Address otherAddress, Integer customerid) {
        this.street = otherAddress.getStreet();
        this.postalCode = otherAddress.getPostalCode();
        this.unitNumber = otherAddress.getUnitNumber();
        this.customerid = customerid;
    }
    
    public String getStreet() {
        return street;
    }
    
    public Integer getUnitNumber() {
        return unitNumber;
    }
    
    public Integer getPostalCode() {
        return postalCode;
    }
    
    public Integer getCustomerid() {
        return customerid;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getStreet().equals(address.getStreet()) && getUnitNumber().equals(address.getUnitNumber()) && getPostalCode().equals(address.getPostalCode()) && getCustomerid().equals(address.getCustomerid());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getStreet(), getUnitNumber(), getPostalCode(), getCustomerid());
    }
}

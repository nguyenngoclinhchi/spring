package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueEmail", columnNames = {"email"})})
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Valid
    @NotEmpty(message = "{firstname.not.empty}")
    @Size(max = 50, message = "{firstname.size}")
    @Column(nullable = false, length = 50)
    private String firstName;
    
    @Valid
    @NotEmpty(message = "{lastname.not.empty}")
    @Size(max = 50, message = "{lastname.size}")
    @Column(nullable = false, length = 50)
    private String lastName;
    
    @Valid
    @NotEmpty(message = "{email.not.empty}")
    @Email(message = "{email.valid}")
    @Size(max = 50, message = "{email.size}")
    @Column(unique = true, nullable = false, length = 50)
    private String email;
    
    @Valid
    @NotEmpty(message = "{phone.not.empty}")
    @Size(max = 20, message = "{phone.size}")
    @Pattern(regexp = "^[0-9+-]*$", message = "{phone.pattern}")
    @Column(nullable = false, length = 20)
    private String phone;
    
    @Valid
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;
    
    @Valid
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifiedDate;
    
    @Valid
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deletedDate;
    
    // One customer linked to many addresses
    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;
    
    public Customer() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.phone = "+65";
        this.addresses = new ArrayList<>();
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public List<Address> getAddresses() {
        return addresses;
    }
    
    public void addListAddress(List<Address> addresses) {
        getAddresses().addAll(addresses);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return getEmail().equals(customer.getEmail());
    }
    
    public boolean equalsStrictly(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return getEmail().equals(customer.getEmail()) && getFirstName().equals(customer.getFirstName())
                   && getLastName().equals(customer.getLastName()) && getPhone().equals(customer.getPhone());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}

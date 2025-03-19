package com.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class CustomerEntity implements UserDetails {

    private static final String AUTHORITIES_DELIMITER = "::";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;

    private String name;
    private String email;
    private long phoneNo;
    private String password;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    private List<OrderEntity> orders;


    private String authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Split the authorities string and convert to a list of SimpleGrantedAuthority objects
        return Arrays.stream(this.authorities.split(AUTHORITIES_DELIMITER)).peek(System.out::println)
                .map(SimpleGrantedAuthority::new).peek(System.out::println)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return getName();
    }
}

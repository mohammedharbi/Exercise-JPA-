package com.example.amazoncloneupdated.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "username is empty")
    @Size(min = 5, message = "username must be at least 5 letters long")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String username;

    @NotEmpty(message = "password is empty")
    @Pattern(regexp = "^[A-Za-z\\d]{7,}$")
    @Column(columnDefinition = "varchar(8) not null")
    private String password;

    @NotEmpty(message = "email is empty")
    @Email
    @Column(columnDefinition = "varchar(20) not null unique")
    private String email;

    @NotEmpty(message = "role is empty")
    @Pattern(regexp = "^(Admin|Customer)")
    @Column(columnDefinition = "varchar(8) not null")
    private String role;

    @NotNull(message = "balance is empty")
    //@Positive
    @Column(columnDefinition = "double not null")
    private Double balance;


}

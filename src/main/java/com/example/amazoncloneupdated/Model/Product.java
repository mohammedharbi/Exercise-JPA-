package com.example.amazoncloneupdated.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name is empty")
    @Size(min = 3, message = "name must be at least 3 letters")
    @Column(columnDefinition = "varchar(10) not null")
    private String name;

    @NotNull(message = "price is empty")
    @Positive
    @Column(columnDefinition = "double not null")
    private Double price;

    @NotNull(message = "category id is empty")
    @Column(columnDefinition = "varchar(10) not null")
    private Integer categoryid;


}

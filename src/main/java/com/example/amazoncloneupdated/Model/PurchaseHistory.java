package com.example.amazoncloneupdated.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "double not null")
    private Double amount;

    @NotNull(message = "user id id is empty")
    @Column(columnDefinition = "varchar(10) not null")
    private Integer userid;
}

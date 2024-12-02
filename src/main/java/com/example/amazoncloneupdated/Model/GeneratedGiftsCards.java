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
public class GeneratedGiftsCards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(15) not null")
    private String gift_card_code;

    @NotNull(message = "userid id is empty")
    @Column(columnDefinition = "varchar(10) not null")
    private Integer userid;
}

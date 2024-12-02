package com.example.amazoncloneupdated.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Check(constraints = "stock >= 10")
public class MerchantStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "product id is empty")
    @Column(columnDefinition = "varchar(10) not null")
    private Integer productId;

    @NotNull(message = "mechant id is empty")
    @Column(columnDefinition = "varchar(10) not null")
    private Integer merchantid;

    @NotNull(message = "stock is empty")
//    @Min(value = 10,message = "stock must be at least 10")
    @Column(columnDefinition = "int not null")
    private Integer stock;

}

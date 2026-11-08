package com.example.pharmacy.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineDTO implements Serializable {
    private Long id;
    private String name;
    private String activeIngredient;
    private BigDecimal price;
    private Integer stock;
}

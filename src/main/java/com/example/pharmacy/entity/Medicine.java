package com.example.pharmacy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "medicines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;          // Tên thuốc
    private String activeIngredient; // Hoạt chất
    private String unit;          // Đơn vị (viên, hộp...)
    private BigDecimal price;     // Giá
    private Integer stock;        // Tồn kho
    private String manufacturer;  // Nhà sản xuất
}

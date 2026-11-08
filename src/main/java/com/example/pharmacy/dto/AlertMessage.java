package com.example.pharmacy.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertMessage implements Serializable {
    private String type;      // IMPORT, EXPORT, WARNING...
    private String message;   // Nội dung thông báo
}

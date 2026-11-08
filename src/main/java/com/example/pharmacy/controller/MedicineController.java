package com.example.pharmacy.controller;

import com.example.pharmacy.dto.MedicineDTO;
import com.example.pharmacy.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping("/{id}")
    public ResponseEntity<MedicineDTO> get(@PathVariable Long id) {
        long start = System.currentTimeMillis();
        MedicineDTO dto = medicineService.getMedicineById(id);
        long elapsed = System.currentTimeMillis() - start;
        return ResponseEntity.ok()
                .header("X-Response-Time-ms", String.valueOf(elapsed))
                .body(dto);
    }

    @PutMapping("/{id}")
    public MedicineDTO update(@PathVariable Long id, @RequestBody MedicineDTO dto) {
        dto.setId(id);
        return medicineService.updateMedicine(dto);
    }
}

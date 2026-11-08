package com.example.pharmacy.service;

import com.example.pharmacy.dto.MedicineDTO;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;

    // ===== ĐỌC: Cache-aside =====
    @Cacheable(value = "medicines", key = "#id")
    public MedicineDTO getMedicineById(Long id) {
        log.info(">>> [DB HIT] Querying database for medicine id = {}", id);
        Medicine m = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found: " + id));
        return toDTO(m);
    }

    // ===== CẬP NHẬT: xóa cache ngay sau khi update =====
    @CacheEvict(value = "medicines", key = "#medicine.id")
    public MedicineDTO updateMedicine(MedicineDTO medicine) {
        log.info(">>> [UPDATE] Medicine id = {} -> evict cache", medicine.getId());
        Medicine entity = medicineRepository.findById(medicine.getId())
                .orElseThrow(() -> new RuntimeException("Not found: " + medicine.getId()));

        entity.setName(medicine.getName());
        entity.setPrice(medicine.getPrice());
        entity.setStock(medicine.getStock());
        entity.setActiveIngredient(medicine.getActiveIngredient());
        entity.setUnit(medicine.getUnit());

        Medicine saved = medicineRepository.save(entity);
        return toDTO(saved);
    }

    private MedicineDTO toDTO(Medicine m) {
        return MedicineDTO.builder()
                .id(m.getId()).name(m.getName())
                .activeIngredient(m.getActiveIngredient())
                .unit(m.getUnit())
                .price(m.getPrice()).stock(m.getStock())
                .build();
    }
}

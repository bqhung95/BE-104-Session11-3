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

    /**
     * Cache-aside:
     *  - Lần 1: chưa có trong Redis -> chạy method, query DB -> lưu kết quả vào Redis
     *  - Lần 2: có trong Redis -> trả về luôn, KHÔNG chạy method, KHÔNG query DB
     */
    @Cacheable(value = "medicines", key = "#id")
    public MedicineDTO getMedicineById(Long id) {
        log.info(">>> [DB HIT] Querying database for medicine id = {}", id);
        Medicine m = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found: " + id));
        return toDTO(m);
    }

    /** Xoá cache khi cập nhật — tránh dữ liệu cũ */
    @CacheEvict(value = "medicines", key = "#id")
    public MedicineDTO updateMedicine(Long id, MedicineDTO dto) {
        Medicine m = medicineRepository.findById(id).orElseThrow();
        m.setName(dto.getName());
        m.setPrice(dto.getPrice());
        m.setStock(dto.getStock());
        return toDTO(medicineRepository.save(m));
    }

    /** Xoá toàn bộ cache "medicines" */
    @CacheEvict(value = "medicines", allEntries = true)
    public void clearAllCache() { }

    private MedicineDTO toDTO(Medicine m) {
        return MedicineDTO.builder()
                .id(m.getId()).name(m.getName())
                .activeIngredient(m.getActiveIngredient())
                .price(m.getPrice()).stock(m.getStock())
                .build();
    }
}

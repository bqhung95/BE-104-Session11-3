package com.example.pharmacy.subscriber;

import com.example.pharmacy.dto.AlertMessage;
import tools.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlertSubscriber {

    private final ObjectMapper objectMapper;

    /**
     * Được gọi tự động khi có message mới trên channel "pharmacy-alerts".
     * Nếu message là JSON → parse thành AlertMessage.
     */
    public void onMessage(String rawMessage) {
        try {
            AlertMessage alert = objectMapper.readValue(rawMessage, AlertMessage.class);
            log.info("=== THÔNG BÁO DASHBOARD QUẢN LÝ ===");
            log.info("Type : {}", alert.getType());
            log.info("Nội dung: {}", alert.getMessage());
            log.info("=====================================");
        } catch (Exception e) {
            log.error("Không parse được message: {}", rawMessage, e);
        }
    }
}
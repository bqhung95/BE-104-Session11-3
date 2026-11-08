package com.example.pharmacy.publisher;

import com.example.pharmacy.config.RedisPubSubConfig;
import com.example.pharmacy.dto.AlertMessage;
import tools.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlertPublisher {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public void publish(AlertMessage alert) {
        try {
            String json = objectMapper.writeValueAsString(alert);
            Long receivers = redisTemplate.convertAndSend(RedisPubSubConfig.CHANNEL, json);
            log.info(">>> Đã gửi thông báo kênh: {} (số subscriber nhận: {})",
                    RedisPubSubConfig.CHANNEL, receivers);
        } catch (Exception e) {
            log.error("Publish thất bại", e);
        }
    }
}
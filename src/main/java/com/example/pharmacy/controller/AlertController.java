package com.example.pharmacy.controller;

import com.example.pharmacy.dto.AlertMessage;
import com.example.pharmacy.publisher.AlertPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertPublisher alertPublisher;

    @PostMapping
    public String sendAlert(@RequestBody AlertMessage alert) {
        alertPublisher.publish(alert);
        return "Đã gửi thông báo kênh : " + "pharmacy-alerts";
    }
}
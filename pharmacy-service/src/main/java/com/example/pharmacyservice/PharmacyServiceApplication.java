package com.example.pharmacyservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PharmacyServiceApplication implements CommandLineRunner {

    @Value("${app.branch-name}")
    private String branchName;

    @Value("${app.hotline}")
    private String hotline;

    public static void main(String[] args) {
        SpringApplication.run(PharmacyServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=========================================");
        System.out.println("Chi nhánh hiệu thuốc: " + branchName);
        System.out.println("Đường dây nóng: " + hotline);
        System.out.println("=========================================");
    }
}

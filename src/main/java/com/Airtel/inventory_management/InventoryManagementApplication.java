package com.Airtel.inventory_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("Airtel Inventory System Started!");
        System.out.println("Open: http://localhost:8080");
        System.out.println("========================================\n");
    }
}
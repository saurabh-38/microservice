package com.saurabh.ecommerce.inventory_service.Config;

import com.saurabh.ecommerce.inventory_service.model.Inventory;
import com.saurabh.ecommerce.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryConfig {

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository){

        return args -> {
            Inventory inventory = new Inventory();
            inventory.setSkuCode("iphone_13");
            inventory.setQuantity(100);
            Inventory inventory1 = new Inventory();
            inventory1.setSkuCode("iphone_13_red");
            inventory1.setQuantity(0);
            inventoryRepository.save(inventory1);
            inventoryRepository.save(inventory);
        };

    }
}

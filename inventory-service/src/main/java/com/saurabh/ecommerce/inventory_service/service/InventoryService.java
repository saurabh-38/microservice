package com.saurabh.ecommerce.inventory_service.service;

import com.saurabh.ecommerce.inventory_service.model.Inventory;
import com.saurabh.ecommerce.inventory_service.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode){
      return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}

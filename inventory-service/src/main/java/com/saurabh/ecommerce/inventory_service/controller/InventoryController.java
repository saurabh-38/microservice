package com.saurabh.ecommerce.inventory_service.controller;

import com.saurabh.ecommerce.inventory_service.dto.InventoryResponse;
import com.saurabh.ecommerce.inventory_service.model.Inventory;
import com.saurabh.ecommerce.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InventoryController {
    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);
    public final InventoryService inventoryService;
    @GetMapping("/api/inventory")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) throws InterruptedException{

            log.info("IsInStock");
            for(int i= 0 ;i< skuCode.size();i++){
              System.out.println( "Saurabh -> "+skuCode.get(i));
            }
            return inventoryService.isInStock(skuCode);
    }

}

package com.saurabh.ecommerce.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class OrderLineItemDto {
    public Long id;
    public String skuCode;
    public BigDecimal price;
    public Integer quantity;
}

package com.saurabh.ecommerce.orderservice.dto;

import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRequest {
    public List<OrderLineItemDto> orderLineItemsDtoList;

}

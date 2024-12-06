package com.saurabh.ecommerce.orderservice.service;

import com.saurabh.ecommerce.orderservice.dto.InventoryResponse;
import com.saurabh.ecommerce.orderservice.dto.OrderLineItemDto;
import com.saurabh.ecommerce.orderservice.dto.OrderRequest;
import com.saurabh.ecommerce.orderservice.model.Order;
import com.saurabh.ecommerce.orderservice.model.OrderLineItem;
import com.saurabh.ecommerce.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.saurabh.ecommerce.orderservice.dto.InventoryResponse;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor

@Service
@Transactional
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    public final OrderRepository orderRepository;
    public final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) throws IllegalAccessException {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        System.out.println(orderRequest.getOrderLineItemsDtoList());
        List<OrderLineItem> orderLineItem = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this:: mapTodo)
                .toList();
        order.setOrderLineItemlist(orderLineItem);
        List<String> skuCodes = order.getOrderLineItemlist().stream()
                .map(OrderLineItem::getSkuCode)
                .toList();
            if(skuCodes.size() == 0)
            {
                log.info("Size id zero");
            }else {
                    log.info("Size is "+skuCodes.size());
            }
        InventoryResponse[] inventoryResponsArray = webClient.get()
                .uri("http://localhost:8081/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        System.out.println("inventoryResponseArray "+inventoryResponsArray.toString());

          boolean allProductInStock = Arrays.stream(inventoryResponsArray).allMatch(InventoryResponse::isInStock);
          if(allProductInStock){
              orderRepository.save(order);
          }else{
              throw new IllegalAccessException("Product is not in stock");
          }

    }

    private OrderLineItem mapTodo(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());

        return orderLineItem;
    }
}

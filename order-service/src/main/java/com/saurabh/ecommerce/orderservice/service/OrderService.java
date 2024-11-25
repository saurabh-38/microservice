package com.saurabh.ecommerce.orderservice.service;

import com.saurabh.ecommerce.orderservice.dto.OrderLineItemDto;
import com.saurabh.ecommerce.orderservice.dto.OrderRequest;
import com.saurabh.ecommerce.orderservice.model.Order;
import com.saurabh.ecommerce.orderservice.model.OrderLineItem;
import com.saurabh.ecommerce.orderservice.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor

@Service
@Transactional
public class OrderService {
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
          Boolean  result  = webClient.get()
                .uri("http://localhost:8082/api/inventory")
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
          if(result){
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

package com.saurabh.ecommerce.orderservice.service;

import com.saurabh.ecommerce.orderservice.dto.OrderLineItemDto;
import com.saurabh.ecommerce.orderservice.dto.OrderRequest;
import com.saurabh.ecommerce.orderservice.model.Order;
import com.saurabh.ecommerce.orderservice.model.OrderLineItem;
import com.saurabh.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor

@Service
public class OrderService {
    public final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        System.out.println(orderRequest.getOrderLineItemsDtoList());
        List<OrderLineItem> orderLineItem = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this:: mapTodo)
                .toList();
        order.setOrderLineItemlist(orderLineItem);
        orderRepository.save(order);
    }

    private OrderLineItem mapTodo(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());

        return orderLineItem;
    }
}

package com.saurabh.ecommerce.orderservice.controller;

import com.saurabh.ecommerce.orderservice.dto.OrderRequest;
import com.saurabh.ecommerce.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
public class OrderController {
    public final OrderService orderService;

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) throws IllegalAccessException{
        System.out.println("saurabh" + orderRequest);
        orderService.placeOrder(orderRequest);

        return "OrderPlace SucessFully";
    }

}

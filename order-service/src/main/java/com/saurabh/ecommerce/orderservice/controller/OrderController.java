package com.saurabh.ecommerce.orderservice.controller;

import com.saurabh.ecommerce.orderservice.dto.OrderRequest;
import com.saurabh.ecommerce.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


@Slf4j

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
@TimeLimiter(name = "inventory")
@Retry(name = "inventory")
public class OrderController {
    public final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) throws IllegalAccessException{
        System.out.println("saurabh" + orderRequest);
        orderService.placeOrder(orderRequest);
        return CompletableFuture.supplyAsync(() -> {
            try {
                return orderService.placeOrder(orderRequest);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
       // return "Order Placed Sucesfully";
    }
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        log.info("Cannot Place Order Executing Fallback logic");
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after some time!");
    }

}

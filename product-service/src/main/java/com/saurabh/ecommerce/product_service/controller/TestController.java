package com.saurabh.ecommerce.product_service.controller;

import com.saurabh.ecommerce.product_service.dto.ProductRequest;
import com.saurabh.ecommerce.product_service.exception.UserNotFoundException;
import com.saurabh.ecommerce.product_service.models.Product;
import com.saurabh.ecommerce.product_service.payload.DeleteResponse;
import com.saurabh.ecommerce.product_service.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("/api/product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Product>> getProducts() throws UserNotFoundException {
        // return ResponseEntity.of(Optional.of(testService.fetchProducts()));
        System.out.println("Get Request");
        return testService.fetchProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductByID(@PathVariable long id) throws UserNotFoundException {
        System.out.println("ProductController.getProductById");
        System.out.println("id " + id);
        return this.testService.getProductByID(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postProducts(@RequestBody ProductRequest req) {
        System.out.println("saurabh" + req);
        testService.createProduct(req);
    }


    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProductBody(@PathVariable long id, @RequestBody Product product) throws UserNotFoundException {
        System.out.println(product.toString());
        return testService.updateProduct(id, product);
    }


    @DeleteMapping("/product/{id}")
    public ResponseEntity<DeleteResponse> deleteProduct(@PathVariable long id) throws UserNotFoundException {
        return testService.deleteProduct(id);
    }


}



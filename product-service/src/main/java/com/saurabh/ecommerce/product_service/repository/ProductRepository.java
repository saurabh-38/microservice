package com.saurabh.ecommerce.product_service.repository;

import com.saurabh.ecommerce.product_service.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getProductById(Long id);
}

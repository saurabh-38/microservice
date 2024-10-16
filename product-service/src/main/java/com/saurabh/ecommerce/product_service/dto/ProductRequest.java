package com.saurabh.ecommerce.product_service.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductRequest {

    public String title;
    public String description;
    public String price;
    public String image;
    public String category;
}

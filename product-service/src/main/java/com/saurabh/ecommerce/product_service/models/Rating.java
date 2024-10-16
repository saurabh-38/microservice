package com.saurabh.ecommerce.product_service.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rating extends BaseModel {

    private Integer rating;
    private Integer count;
}

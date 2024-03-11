package com.shopapp.shopapp.dto.respone;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopapp.shopapp.model.Products;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponeDto {
    private String name;
    private float price;
    private String thumbnail;
    private String description;
    private Long categoryId;

    public static ProductResponeDto fromProduct(Products products) {
    ProductResponeDto productResponeDto = ProductResponeDto.builder()
            .name(products.getName())
            .price(products.getPrice())
            .description(products.getDescription())
            .thumbnail(products.getThumbnail())
            .categoryId(products.getCategory().getId())
            .build();
            return productResponeDto;
}
}

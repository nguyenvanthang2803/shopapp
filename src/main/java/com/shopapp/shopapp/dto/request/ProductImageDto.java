package com.shopapp.shopapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopapp.shopapp.model.Products;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductImageDto {
    @JsonProperty("product_id")

    private Long productId;
    @JsonProperty("image_url")
    private String imageUrl;
}

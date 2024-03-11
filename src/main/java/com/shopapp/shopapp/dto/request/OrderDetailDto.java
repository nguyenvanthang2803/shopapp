package com.shopapp.shopapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    @JsonProperty("order_id")
    @Min(value = 1,message = "Order ID must >0")
    private Long orderId;
    @Min(value = 1,message = "Product ID must >0")
    @JsonProperty("product_id")
    private Long productId;
    @Min(value = 0,message = "Price  must >=0")
    private Long price;
    @JsonProperty("number_of_product")
    @Min(value = 1,message = "Num of pro  must >1")
    private int numberOfProduct;
    @JsonProperty("total_money")
    @Min(value = 0,message = "Total must >=0")
    private int totalMoney;
    private String Color;
}

package com.shopapp.shopapp.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListResponeDto {
    private List<ProductResponeDto> productResponeDtos;
    private  int totalPages;
}

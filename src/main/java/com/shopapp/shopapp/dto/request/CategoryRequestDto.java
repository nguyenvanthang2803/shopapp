package com.shopapp.shopapp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotEmpty(message = "Category can't be empty")
    private String name;
}

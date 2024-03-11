package com.shopapp.shopapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class ProductRequestDto {
    @NotBlank(message = "Title Required")
    @Size(min = 3,max = 200,message = "Title must be between 3 and 200")
    private String name;
    @Min(value = 0,message = "Price must be greater or equal to 0")
    private float price;
    private String thumbnail;
    private String description;
    @JsonProperty("category_id")
    private Long categoryId;
}

package com.shopapp.shopapp.repositories;

import com.shopapp.shopapp.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepo extends JpaRepository<ProductImage,Long> {
    List<ProductImage> findByProductsId(Long productId);
}

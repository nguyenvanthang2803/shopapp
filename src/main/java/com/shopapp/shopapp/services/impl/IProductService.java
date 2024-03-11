package com.shopapp.shopapp.services.impl;

import com.shopapp.shopapp.dto.request.ProductImageDto;
import com.shopapp.shopapp.dto.request.ProductRequestDto;
import com.shopapp.shopapp.dto.respone.ProductResponeDto;
import com.shopapp.shopapp.exception.DataNotFoundException;
import com.shopapp.shopapp.model.ProductImage;
import com.shopapp.shopapp.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    Products createProduct(ProductRequestDto productRequestDto) throws DataNotFoundException;
    Products getProductById(Long id)   throws Exception;
    Page<ProductResponeDto> getAllProducts(PageRequest pageRequest);
    Products updateProduct(Long id,ProductRequestDto productRequestDto) throws Exception;
    void deleteProduct(Long id);
    boolean existsByName(String title);
    public ProductImage createProductImage(Long productId, ProductImageDto productImageDto) throws Exception;
    }

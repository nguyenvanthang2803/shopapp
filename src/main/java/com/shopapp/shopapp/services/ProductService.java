package com.shopapp.shopapp.services;

import com.shopapp.shopapp.dto.request.ProductImageDto;
import com.shopapp.shopapp.dto.request.ProductRequestDto;
import com.shopapp.shopapp.dto.respone.ProductResponeDto;
import com.shopapp.shopapp.exception.DataNotFoundException;
import com.shopapp.shopapp.exception.InvalidParamException;
import com.shopapp.shopapp.model.Category;
import com.shopapp.shopapp.model.ProductImage;
import com.shopapp.shopapp.model.Products;
import com.shopapp.shopapp.repositories.CategoryRepo;
import com.shopapp.shopapp.repositories.ProductImageRepo;
import com.shopapp.shopapp.repositories.ProductRepo;
import com.shopapp.shopapp.services.impl.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final ProductImageRepo productImageRepo;
    @Override
    public Products createProduct(ProductRequestDto productRequestDto) throws DataNotFoundException {
        Category existingCategory=categoryRepo.findById(productRequestDto.getCategoryId()).orElseThrow(
                ()-> new DataNotFoundException("Not found Category")
        );
        Products newProduct = Products.builder()
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .description(productRequestDto.getDescription())
                .thumbnail(productRequestDto.getThumbnail())
                .category(existingCategory)
                .build();
        return productRepo.save(newProduct);
    }

    @Override
    public Products getProductById(Long id) throws Exception {
        return productRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException("Not found Category"));
    }

    @Override
    public Page<ProductResponeDto> getAllProducts(PageRequest pageRequest) {
        return productRepo.findAll(pageRequest).map(ProductResponeDto::fromProduct);
    }

    @Override
    public Products updateProduct(Long id, ProductRequestDto productRequestDto) throws Exception {
       Products existingProduct = getProductById(id);
        Category existingCategory=categoryRepo.findById(productRequestDto.getCategoryId()).orElseThrow(
                ()-> new DataNotFoundException("Not found Category")
        );
       existingProduct.setDescription(productRequestDto.getDescription());
       existingProduct.setName(productRequestDto.getName());
       existingProduct.setPrice(productRequestDto.getPrice());
       existingProduct.setThumbnail(productRequestDto.getThumbnail());
       existingProduct.setCategory(existingCategory);
        return productRepo.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
       if(productRepo.findById(id).isPresent()){
           productRepo.deleteById(id);
       }
    }

    @Override
    public boolean existsByName(String name) {
        return productRepo.existsByName(name);
    }
    @Override
    public ProductImage createProductImage(Long productId, ProductImageDto productImageDto) throws Exception {
        Products existingProduct=productRepo.findById(productId).orElseThrow(
                ()-> new DataNotFoundException("Not found Category")
        );
        ProductImage newProductImage = ProductImage.builder()
                .products(existingProduct).
                imageUrl(productImageDto.getImageUrl())
                .build();
        //ko cho insert qua 5 anhr
       int size = productImageRepo.findByProductsId(existingProduct.getId()).size();
        if(size>=5){
            throw  new InvalidParamException("Number of images must be <=5");

        }
        return productImageRepo.save(newProductImage);
    }
}

package com.shopapp.shopapp.controller;

import com.github.javafaker.Faker;
import com.shopapp.shopapp.dto.request.ProductImageDto;
import com.shopapp.shopapp.dto.request.ProductRequestDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.shopapp.shopapp.dto.respone.ProductListResponeDto;
import com.shopapp.shopapp.dto.respone.ProductResponeDto;
import com.shopapp.shopapp.model.ProductImage;
import com.shopapp.shopapp.model.Products;
import com.shopapp.shopapp.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping()
    public ResponseEntity<ProductListResponeDto> getProduct(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        PageRequest pageRequest =
                PageRequest.of(page,limit, Sort.by("createdAt").descending());
        Page<ProductResponeDto> productsPage = productService.getAllProducts(pageRequest);
        int totalPage = productsPage.getTotalPages();
        List<ProductResponeDto> productsList =productsPage.getContent();
        return ResponseEntity.ok(ProductListResponeDto.builder()
                        .productResponeDtos(productsList)
                        .totalPages(totalPage)
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponeDto> getProductById(
            @PathVariable("id") Long id
    ) throws Exception {
        Products existingProduct = productService.getProductById(id);
        return ResponseEntity.ok(ProductResponeDto.fromProduct(existingProduct));
    }
    @PostMapping()
    public ResponseEntity<?> insertProduct(@Valid @RequestBody ProductRequestDto productRequestDto
            , BindingResult result)   {
        try{
            if(result.hasErrors()){
            List<String> errorMessage =result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
            //Luu Product
            Products newProduct = productService.createProduct(productRequestDto);


            return ResponseEntity.ok(newProduct);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "/uploads/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(
            @PathVariable("id") Long productId,
            @ModelAttribute("files")List<MultipartFile> files
    ) throws IOException {
        try {
            Products existingProduct = productService.getProductById(productId);
           files = files ==null? new ArrayList<MultipartFile>():files;
           List<ProductImage> productImageList = new ArrayList<>();
            for (MultipartFile file : files) {
                if(file.getSize()==0){
                    continue;
                }
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is to large");

                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("file must be an image");
                }
                //Luufile
                String filename = storeFile(file);
              ProductImage productImage=  productService.createProductImage(existingProduct.getId(),
                        ProductImageDto.builder()
                                .imageUrl(filename).build());
                productImageList.add(productImage);
            }
            return ResponseEntity.ok().body(productImageList);
        }
        catch (Exception e
        ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        }
    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString()+"_"+fileName;
        Path uploadDir = Paths.get("uploads");
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(),uniqueFileName);
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id,
                                                 @RequestBody ProductRequestDto productRequestDto) throws Exception {
        productService.updateProduct(id,productRequestDto);
        return ResponseEntity.ok("update successfull id="+id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);

        return ResponseEntity.ok("delete id="+id);
    }
    @GetMapping("/generateFake")
    public ResponseEntity<String> generateFake(){
        Faker faker = new Faker();

        for(int i=0;i<1000;i++){
            String productName = faker.commerce().productName();
            if(productService.existsByName(productName)){
                continue;
            }
            ProductRequestDto productRequestDto =ProductRequestDto
                    .builder()
                    .name(productName)
                    .price(faker.number().numberBetween(10,90_00_00))
                    .description(faker.lorem().sentence())
                    .thumbnail(faker.internet().url())
                    .categoryId((long)faker.number().numberBetween(1,4))
                    .build();
            try{
                productService.createProduct(productRequestDto);
            }catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("hello");
    }

}

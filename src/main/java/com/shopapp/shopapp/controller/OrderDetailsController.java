package com.shopapp.shopapp.controller;

import com.shopapp.shopapp.dto.request.OrderDetailDto;
import com.shopapp.shopapp.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
public class OrderDetailsController {
    private  final OrderDetailService orderDetailService;
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDto orderDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            return ResponseEntity.ok(orderDetailService.createOrderDetail(orderDto));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrdersDetailById(@Valid @PathVariable("id") Long id){
        try{

            return ResponseEntity.ok( orderDetailService.getOrderDetailById(id));
        }catch (Exception e ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrdersDetailList(@Valid @PathVariable("orderId") Long orderId){
        try{

            return ResponseEntity.ok(orderDetailService.findByOrderId(orderId));
        }catch (Exception e ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id,
                                               @RequestBody OrderDetailDto orderDetailDto){
        try{
            return ResponseEntity.ok("Lay danh sach order to id");
        }catch (Exception e ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id){
        try{
            return ResponseEntity.ok("Lay danh sach order to id");
        }catch (Exception e ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}

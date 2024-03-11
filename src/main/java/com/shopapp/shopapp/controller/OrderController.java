package com.shopapp.shopapp.controller;

import com.shopapp.shopapp.dto.request.OrderDto;
import com.shopapp.shopapp.dto.request.OrderRequestDto;
import com.shopapp.shopapp.model.Orders;
import com.shopapp.shopapp.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDto orderDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }

            Orders orderRespone=orderService.createOrder(orderDto);
            return ResponseEntity.ok(orderRespone);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrdersbyID(@Valid @PathVariable("user_id") Long userId){
        try{
            List<Orders> orders = orderService.findByUserId(userId);
          return ResponseEntity.ok(orders);
        }catch (Exception e ){
           return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("id") Long id){
        try{
            return ResponseEntity.ok( orderService.getOrderById(id));
        }catch (Exception e ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable("id") Long id,@Valid @RequestBody OrderRequestDto orderDto){
        try{
            Orders orders = orderService.updateOrder(id, orderDto);
            return ResponseEntity.ok(orders);
        }catch (Exception e ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("id") Long id){
        try{
            orderService.deleteOrder(id);
            return ResponseEntity.ok("xoa order to id");
            // Xoa mem -> set isActive = false
        }catch (Exception e ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}

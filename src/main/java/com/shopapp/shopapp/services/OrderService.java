package com.shopapp.shopapp.services;

import com.shopapp.shopapp.dto.request.OrderRequestDto;
import com.shopapp.shopapp.dto.respone.OrderRespone;
import com.shopapp.shopapp.exception.DataNotFoundException;
import com.shopapp.shopapp.model.Orders;
import com.shopapp.shopapp.model.Status;
import com.shopapp.shopapp.model.User;
import com.shopapp.shopapp.repositories.OrderRepo;
import com.shopapp.shopapp.repositories.UserRepo;
import com.shopapp.shopapp.services.impl.IOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final ModelMapper modelMapper;
    @Override
    public Orders createOrder(OrderRequestDto orderRequestDto) throws DataNotFoundException {
      User user = userRepo.findById(orderRequestDto.getUserId())
              .orElseThrow(()->new DataNotFoundException("Cannot found User"));
        modelMapper.typeMap(OrderRequestDto.class, Orders.class)
                .addMappings(mapper ->mapper.skip(Orders::setId));
        Orders order = new Orders();
        modelMapper.map(orderRequestDto,order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(Status.pending);
        LocalDate shippingDate = orderRequestDto.getShippingDate()==null?LocalDate.now():orderRequestDto.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Date must be at least today");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepo.save(order);
        return order;
    }

    @Override
    public Orders getOrderById(Long id) {
        return orderRepo.findById(id).orElseThrow(null);
    }

    @Override
    public List<Orders> findByUserId(Long userId) {
        return orderRepo.findByUserId(userId);
    }

    @Override
    public Orders updateOrder(Long orderId, OrderRequestDto orderRequestDto) throws DataNotFoundException {
        Orders orders = orderRepo.findById(orderId)
                .orElseThrow(()->new DataNotFoundException("Cannot found Order"));
        User existingUser = userRepo.findById(orderRequestDto.getUserId())
                .orElseThrow(()->new DataNotFoundException("Cannot found User"));
        modelMapper.typeMap(OrderRequestDto.class,Orders.class)
                .addMappings(mapper->mapper.skip(Orders::setId));
                modelMapper.map(orderRequestDto,orders);
                orders.setUser(existingUser);

        return orderRepo.save(orders);
    }

    @Override
    public void deleteOrder(Long id) {
        Orders orders = orderRepo.findById(id).orElseThrow(null);
        if(orders!=null){
        orders.setActive(false);
        orderRepo.save(orders);

        }
    }
}

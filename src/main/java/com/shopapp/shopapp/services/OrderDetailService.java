package com.shopapp.shopapp.services;

import com.shopapp.shopapp.dto.request.OrderDetailDto;
import com.shopapp.shopapp.exception.DataNotFoundException;
import com.shopapp.shopapp.model.OrderDetails;
import com.shopapp.shopapp.model.Orders;
import com.shopapp.shopapp.model.Products;
import com.shopapp.shopapp.repositories.OrderDetailRepo;
import com.shopapp.shopapp.repositories.OrderRepo;
import com.shopapp.shopapp.repositories.ProductRepo;
import com.shopapp.shopapp.services.impl.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private  final OrderRepo orderRepo;
    private  final ProductRepo productRepo;
    private final OrderDetailRepo orderDetailRepo;
    @Override
    public OrderDetails createOrderDetail(OrderDetailDto orderDetailDto) throws DataNotFoundException {
        Orders order = orderRepo.findById(orderDetailDto.getOrderId())
                .orElseThrow(()->new DataNotFoundException("Cannot find Order"));
        Products products = productRepo.findById(orderDetailDto.getProductId())
                .orElseThrow(()->new DataNotFoundException("Cannot find Product"));
        OrderDetails orderDetails = OrderDetails.builder()
                .orders(order)
                .products(products)
                .price(orderDetailDto.getPrice())
                .totalMoney(orderDetailDto.getTotalMoney())
                .NumberOfProducts(orderDetailDto.getNumberOfProduct())
                .color(orderDetailDto.getColor())
                .build();
        return orderDetailRepo.save(orderDetails);
    }

    @Override
    public OrderDetails getOrderDetailById(Long id) throws DataNotFoundException {

        return orderDetailRepo.findById(id).orElseThrow(()->
            new DataNotFoundException("Cannot find Order Details")
        );
    }

    @Override
    public List<OrderDetails> findByOrderId(Long id) {
        return orderDetailRepo.findByOrdersId(id);
    }

    @Override
    public OrderDetails updateOrderDetail(Long categoryId, OrderDetailDto orderDetailDto) {
        return null;
    }

    @Override
    public void deleteOrderDetail(Long id) {
    orderDetailRepo.deleteById(id);
    }
}

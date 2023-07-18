package com.onlineshopping.orderservice.service;
import com.onlineshopping.orderservice.dto.OrderLineItemsDto;
import com.onlineshopping.orderservice.dto.OrderRequest;
import com.onlineshopping.orderservice.dto.OrderResponse;
import com.onlineshopping.orderservice.model.Order;
import com.onlineshopping.orderservice.model.OrderLineItems;
import com.onlineshopping.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void plcaeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> items = orderRequest.getOrderLineItemsDtos();
        order.setOrderLineItemms(items);
        orderRepository.save(order);
    }

    private List<OrderLineItemsDto> MapToResponse(List<OrderLineItems> orderLineItems) {
        return orderLineItems.stream().map(this::DtoResponse).toList();
    }

    private OrderLineItemsDto DtoResponse(OrderLineItems orderLineItems) {
        return OrderLineItemsDto.builder()
                .id(orderLineItems.getId())
                .quantity(orderLineItems.getQuantity())
                .price(orderLineItems.getPrice())
                .skuCode(orderLineItems.getSkuCode())
                .build();
    }

    public List<OrderResponse> getAllProducts() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::ToOrderResponse).toList();
    }

    private OrderResponse ToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderLineItemms(MapToResponse(order.getOrderLineItemms()))
                .build();
    }
}

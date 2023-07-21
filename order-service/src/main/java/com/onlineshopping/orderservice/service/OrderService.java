package com.onlineshopping.orderservice.service;
import com.onlineshopping.orderservice.dto.InventoryResponse;
import com.onlineshopping.orderservice.dto.OrderLineItemsDto;
import com.onlineshopping.orderservice.dto.OrderRequest;
import com.onlineshopping.orderservice.dto.OrderResponse;
import com.onlineshopping.orderservice.model.Order;
import com.onlineshopping.orderservice.model.OrderLineItems;
import com.onlineshopping.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    public void plcaeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> items = orderRequest.getOrderLineItemsDtos();
        order.setOrderLineItemms(items);

        List<String> skuCodesList = order.getOrderLineItemms().stream().map(OrderLineItems::getSkuCode).toList();


        // talks to inventory service to check availability of products

        InventoryResponse[] invnetoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodesList).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(invnetoryResponseArray).allMatch(InventoryResponse::isInStock);

        if(Boolean.TRUE.equals(allProductsInStock)){
            orderRepository.save(order);
        }
        else{
            throw new IllegalArgumentException("Product is not in Stock!! Please try again later");
        }

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

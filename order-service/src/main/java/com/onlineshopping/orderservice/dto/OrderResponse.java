package com.onlineshopping.orderservice.dto;

import com.onlineshopping.orderservice.model.OrderLineItems;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    private String orderNumber;
    private List<OrderLineItemsDto> orderLineItemms;

}

package com.onlineshopping.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {

    private String skuCode;
    private Integer quantity;

}

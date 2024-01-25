package com.example.ElectronicStore.Dto;

import com.example.ElectronicStore.Entity.Orders;
import com.example.ElectronicStore.Entity.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotNull;

public class OrderItemsDto {
    private Long id;
    // quantity of each orderItem
    private Integer quantity;
    // price of each orderItem
    private Double price;

    @NotNull
    private Product product;

    private OrderDto orderDto;
}

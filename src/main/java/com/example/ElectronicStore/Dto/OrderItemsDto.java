package com.example.ElectronicStore.Dto;

import com.example.ElectronicStore.Entity.Orders;
import com.example.ElectronicStore.Entity.Product;
import jakarta.validation.constraints.NotNull;

public class OrderItemsDto {
    private Long id;
    // quantity of each orderItem
    private Integer quantity;
    // price of each orderItem
    private Double price;

    @NotNull
    private Product product;

    @NotNull
    private Orders orders;
}

package com.example.ElectronicStore.Dto;

import com.example.ElectronicStore.Entity.CartItem;
import com.example.ElectronicStore.Entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CartDto {
    private Long id;

    private int totalQuantity;
    private double amount;

    private User user;

    private List<CartItem> cartItem = new ArrayList<>();
}

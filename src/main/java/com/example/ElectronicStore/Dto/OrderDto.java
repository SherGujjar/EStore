package com.example.ElectronicStore.Dto;

import com.example.ElectronicStore.Entity.User;
import lombok.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;

    private String orderStatus;

    private String billingNumber;

    private String billingAddress;

    private Integer totalQuantity;

    private Double totalPrice;

    private Date orderedDate;

    private User user;

    List<OrderItemsDto> orderItemsList = new ArrayList<>();
}

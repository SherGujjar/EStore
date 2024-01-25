package com.example.ElectronicStore.Utils;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestBody {

    private Long cartId;
    private String billingNumber;

    private String billingAddress;

    private String billingName;
}

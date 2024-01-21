package com.example.ElectronicStore.Utils;

import com.example.ElectronicStore.Dto.ProductDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AddCartRequest {

    private int qty;
    private Long productId;
}

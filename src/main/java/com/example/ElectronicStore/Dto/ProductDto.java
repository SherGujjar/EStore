package com.example.ElectronicStore.Dto;

import com.example.ElectronicStore.Entity.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductDto {

    private Long id;

    @NotNull
    private String title;

    private String description;

    private Double price;

    private Category category;

}

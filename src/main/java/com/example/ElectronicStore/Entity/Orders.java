package com.example.ElectronicStore.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String orderStatus;

    private String billingNumber;

    private String billingAddress;

    private Integer totalQuantity;

    private Double totalPrice;

    private Date orderedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "orders",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItems> orderItemsList = new ArrayList<>();



}

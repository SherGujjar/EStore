package com.example.ElectronicStore.Repository;

import com.example.ElectronicStore.Entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItems,Long> {
}

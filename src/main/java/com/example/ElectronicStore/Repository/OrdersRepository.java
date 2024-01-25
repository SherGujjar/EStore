package com.example.ElectronicStore.Repository;

import com.example.ElectronicStore.Entity.Orders;
import com.example.ElectronicStore.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

    @Query("SELECT o FROM Orders o WHERE o.user = :user")
    Page<Orders> findByUser(User user, Pageable pageable);
}

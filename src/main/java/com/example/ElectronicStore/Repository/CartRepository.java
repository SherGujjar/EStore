package com.example.ElectronicStore.Repository;

import com.example.ElectronicStore.Entity.Cart;
import com.example.ElectronicStore.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUser(User user);
}

package com.example.ElectronicStore.Repository;

import com.example.ElectronicStore.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findByCartId(Long cartId);

}

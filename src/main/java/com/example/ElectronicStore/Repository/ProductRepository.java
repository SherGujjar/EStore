package com.example.ElectronicStore.Repository;

import com.example.ElectronicStore.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findById(Long id);

    @Query(value = "Select * from product as p where p.category_id = ?1",nativeQuery = true)
    Page<Product> getAllProductByCategoryId(Long categoryId, Pageable pageable);

}

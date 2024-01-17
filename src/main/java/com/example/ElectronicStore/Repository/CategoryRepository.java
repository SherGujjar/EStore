package com.example.ElectronicStore.Repository;

import com.example.ElectronicStore.Entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByType(String title);

    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.products")
    Page<Category> findAll(Pageable pageable);

}

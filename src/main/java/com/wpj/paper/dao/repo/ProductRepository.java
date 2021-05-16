package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query(value = "UPDATE pruduct SET stock = :stock", nativeQuery = true)
    int updateAll(long stock);
}

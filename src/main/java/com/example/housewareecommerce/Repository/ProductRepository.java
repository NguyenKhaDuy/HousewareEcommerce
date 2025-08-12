package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Repository.Custom.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductRepositoryCustom {
}

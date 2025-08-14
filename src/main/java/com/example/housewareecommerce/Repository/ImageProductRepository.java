package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.ImageEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageEntity, Long> {
    List<ImageEntity> findByProductEntity(ProductEntity productEntity);
}

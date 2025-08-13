package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageEntity, Long> {
}

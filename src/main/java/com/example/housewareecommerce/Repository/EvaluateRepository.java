package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.EvaluateEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluateRepository extends JpaRepository<EvaluateEntity, Long> {
    List<EvaluateEntity> findByProductEntity(ProductEntity productEntity);
    Page<EvaluateEntity> findByProductEntity(ProductEntity productEntity, Pageable pageable);
}

package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.CommentEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findByProductEntity(ProductEntity productEntity , Pageable pageable);
}

package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.CartEntity;
import com.example.housewareecommerce.Entity.CartItemEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    CartItemEntity findByProductEntityAndAndCartEntity(ProductEntity productEntity, CartEntity cartEntity);
}

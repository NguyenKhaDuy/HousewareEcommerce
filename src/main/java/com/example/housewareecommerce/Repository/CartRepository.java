package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.CartEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findByUserEntity(UserEntity userEntity);
}

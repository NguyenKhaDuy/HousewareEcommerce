package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.CartEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findByUserEntity(UserEntity userEntity);

    @Query("SELECT COUNT(ci) FROM CartItemEntity ci JOIN ci.cartEntity c WHERE c.userEntity.id = :userId")
    Long getCartItemCountByUserId(@Param("userId") Long userId);
}

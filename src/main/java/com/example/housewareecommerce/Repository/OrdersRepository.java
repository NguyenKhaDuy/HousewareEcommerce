package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.OrderEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findByUserEntity(UserEntity userEntity, Pageable pageable);
}

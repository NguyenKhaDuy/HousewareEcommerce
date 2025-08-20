package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.OrderEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUserEntityIdOrderByDateOrderDesc(Long userId);
    Page<OrderEntity> findByUserEntity(UserEntity userEntity, Pageable pageable);
}

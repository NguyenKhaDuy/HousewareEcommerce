package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserEntityIdOrderByDateOrderDesc(Long userId);
}

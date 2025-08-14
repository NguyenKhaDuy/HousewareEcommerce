package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscoutRepository extends JpaRepository<DiscountEntity, Long> {
}

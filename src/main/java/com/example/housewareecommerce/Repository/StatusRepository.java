package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
}

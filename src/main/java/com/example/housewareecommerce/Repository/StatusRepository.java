package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.StatusEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {

    Optional<StatusEntity> findById(Long id);
}

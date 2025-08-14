package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}

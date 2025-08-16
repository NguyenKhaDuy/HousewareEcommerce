package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.FavoriteProductEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProductEntity, Long> {
    Page<FavoriteProductEntity> findByUserEntity(UserEntity userEntity, Pageable pageable);
    FavoriteProductEntity findByUserEntityAndProductEntity(UserEntity userEntity, ProductEntity productEntity);
}

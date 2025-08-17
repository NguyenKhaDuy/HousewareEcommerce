package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.FavoriteProductEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@SuppressWarnings("all")
public interface FavoriteProductRepository extends JpaRepository<FavoriteProductEntity, Long> {
    Page<FavoriteProductEntity> findByUserEntity(UserEntity userEntity, Pageable pageable);

    @Query("SELECT f.productEntity.id FROM FavoriteProductEntity f WHERE f.userEntity.id = :userId")
    Set<Long> findProductIdsByUserId(@Param("userId") Long userId);

    FavoriteProductEntity findByUserEntityAndProductEntity(UserEntity userEntity, ProductEntity productEntity);
}

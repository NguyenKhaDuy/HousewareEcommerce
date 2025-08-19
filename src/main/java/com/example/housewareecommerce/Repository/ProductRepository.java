package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Repository.Custom.ProductRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductRepositoryCustom {

    List<ProductEntity> findByCategoryEntity_Id(Long categoryId);

    @Query("SELECT DISTINCT p FROM ProductEntity p " +
            "LEFT JOIN FETCH p.imageEntities " +
            "LEFT JOIN FETCH p.statusEntity " +
            "WHERE p.categoryEntity.id = :categoryId")
    List<ProductEntity> findByCategoryIdWithImages(@Param("categoryId") Long categoryId);

    @Query("SELECT DISTINCT p FROM ProductEntity p " +
            "LEFT JOIN FETCH p.statusEntity " +
            "LEFT JOIN FETCH p.categoryEntity " +
            "WHERE p.id = :productId")
    Optional<ProductEntity> findByIdWithBasicRelations(@Param("productId") Long productId);


    @Query("SELECT DISTINCT p FROM ProductEntity p " +
            "LEFT JOIN FETCH p.imageEntities " +
            "WHERE p.id = :productId")
    Optional<ProductEntity> findByIdWithImages(@Param("productId") Long productId);


    @Query("SELECT DISTINCT p FROM ProductEntity p " +
            "LEFT JOIN FETCH p.evaluateEntities e " +
            "LEFT JOIN FETCH e.userEntity " +
            "WHERE p.id = :productId")
    Optional<ProductEntity> findByIdWithEvaluates(@Param("productId") Long productId);

    @Query("SELECT DISTINCT p FROM ProductEntity p " +
            "LEFT JOIN FETCH p.categoryEntity " +
            "LEFT JOIN FETCH p.statusEntity " +
            "LEFT JOIN FETCH p.imageEntities " +
            "WHERE LOWER(p.nameProduct) LIKE LOWER(CONCAT('%', :nameProduct, '%'))")
    Page<ProductEntity> searchProduct(Pageable pageable, @Param("nameProduct") String nameProduct);
}

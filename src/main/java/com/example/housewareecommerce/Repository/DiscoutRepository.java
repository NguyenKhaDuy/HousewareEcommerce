package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscoutRepository extends JpaRepository<DiscountEntity, Long> {
    DiscountEntity findByDiscountCode(String discountCode);

    @Query("SELECT d.percentDiscount FROM DiscountEntity d WHERE d.discountCode = :discountCode AND d.statusEntity.id = 38")
    Optional<Float> findPercentDiscountByValidCode(@Param("discountCode") String discountCode);

    @Query("SELECT d FROM DiscountEntity d WHERE d.statusEntity.id = :statusId")
    List<DiscountEntity> findByStatusId(@Param("statusId") Long statusId);

    @Query("SELECT d.percentDiscount FROM DiscountEntity d WHERE d.discountCode = :discountCode AND d.statusEntity.id = :statusId")
    Optional<Float> findPercentDiscountByCodeAndStatus(@Param("discountCode") String discountCode, @Param("statusId") Long statusId);

    @Query("SELECT d FROM DiscountEntity d WHERE d.id = :discountId AND d.statusEntity.id = :statusId")
    Optional<DiscountEntity> findByIdAndStatus(@Param("discountId") Long discountId, @Param("statusId") Long statusId);

    @Query("SELECT d.percentDiscount FROM DiscountEntity d WHERE d.statusEntity.id = :statusId")
    List<Float> findAllPercentDiscountByStatus(@Param("statusId") Long statusId);
}

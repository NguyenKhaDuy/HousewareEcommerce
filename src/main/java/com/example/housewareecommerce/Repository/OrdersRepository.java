package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.OrderEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import com.example.housewareecommerce.Model.DTO.MonthlyStatisticsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUserEntityIdOrderByDateOrderDesc(Long userId);
    Page<OrderEntity> findByUserEntity(UserEntity userEntity, Pageable pageable);

    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.dateOrder BETWEEN :fromDate AND :toDate")
    Long countOrdersByDateRange(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM OrderEntity o WHERE o.dateOrder BETWEEN :fromDate AND :toDate")
    Double sumRevenueByDateRange(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("SELECT COALESCE(AVG(o.totalPrice), 0) FROM OrderEntity o WHERE o.dateOrder BETWEEN :fromDate AND :toDate")
    Double averageOrderValueByDateRange(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("SELECT COALESCE(SUM(od.quality), 0) FROM OrderDetailsEntity od JOIN od.orderEntity o WHERE o.dateOrder BETWEEN :fromDate AND :toDate")
    Long sumProductsSoldByDateRange(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    // Thống kê theo tháng
    @Query("SELECT new com.example.housewareecommerce.Model.DTO.MonthlyStatisticsDTO(" +
            "YEAR(o.dateOrder), MONTH(o.dateOrder), COUNT(o), COALESCE(SUM(o.totalPrice), 0.0)) " +
            "FROM OrderEntity o " +
            "WHERE o.dateOrder >= :fromDate " +
            "GROUP BY YEAR(o.dateOrder), MONTH(o.dateOrder) " +
            "ORDER BY YEAR(o.dateOrder), MONTH(o.dateOrder)")
    List<MonthlyStatisticsDTO> getMonthlyStatistics(@Param("fromDate") LocalDate fromDate);

    // Top sản phẩm bán chạy
    @Query("SELECT od.productEntity.nameProduct, SUM(od.quality) as totalSold " +
            "FROM OrderDetailsEntity od JOIN od.orderEntity o " +
            "WHERE o.dateOrder BETWEEN :fromDate AND :toDate " +
            "GROUP BY od.productEntity.id, od.productEntity.nameProduct " +
            "ORDER BY totalSold DESC")
    List<Object[]> getTopSellingProducts(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}

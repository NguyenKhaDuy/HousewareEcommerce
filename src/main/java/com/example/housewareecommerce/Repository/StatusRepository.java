package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {

    @Query("SELECT s.statusCode FROM StatusEntity s")
    List<String> findAllStatusCodes();

    List<StatusEntity> findByIdIn(List<Long> ids);

}

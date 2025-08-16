package com.example.housewareecommerce.Repository;

import com.example.housewareecommerce.Entity.CategoryEntity;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
